package com.github.hummel.mdb.core.service.impl

import com.github.hummel.mdb.core.bean.BotData
import com.github.hummel.mdb.core.factory.ServiceFactory
import com.github.hummel.mdb.core.service.AccessService
import com.github.hummel.mdb.core.service.DataService
import com.github.hummel.mdb.core.service.OwnerService
import com.github.hummel.mdb.core.utils.I18n
import com.github.hummel.mdb.core.utils.access
import com.github.hummel.mdb.core.utils.success
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.interaction.InteractionCreateEvent

class OwnerServiceImpl : OwnerService {
	private val dataService: DataService = ServiceFactory.dataService
	private val accessService: AccessService = ServiceFactory.accessService

	override fun import(event: InteractionCreateEvent) {
		val sc = event.slashCommandInteraction.get()

		if (sc.fullCommandName == "import") {
			sc.respondLater().thenAccept {
				val server = sc.server.get()
				val serverData = dataService.loadServerData(server)

				val embed = if (!accessService.fromOwnerAtLeast(sc)) {
					EmbedBuilder().access(sc.user, serverData, I18n.of("no_access", serverData))
				} else {
					val byteArray = sc.arguments[0].attachmentValue.get().asByteArray().join()
					dataService.importBotData(byteArray)
					EmbedBuilder().success(sc.user, serverData, I18n.of("import", serverData))
				}
				sc.createFollowupMessageBuilder().addEmbed(embed).send().get()
			}.get()
		}
	}

	override fun export(event: InteractionCreateEvent) {
		val sc = event.slashCommandInteraction.get()

		if (sc.fullCommandName == "export") {
			sc.respondLater().thenAccept {
				val server = sc.server.get()
				val serverData = dataService.loadServerData(server)

				if (!accessService.fromOwnerAtLeast(sc)) {
					val embed = EmbedBuilder().access(sc.user, serverData, I18n.of("no_access", serverData))
					sc.createFollowupMessageBuilder().addEmbed(embed).send().get()
				} else {
					dataService.exportBotData(sc)
				}
			}.get()
		}
	}

	override fun exit(event: InteractionCreateEvent) {
		val sc = event.slashCommandInteraction.get()
		if (sc.fullCommandName == "exit") {
			var exit = false

			sc.respondLater().thenAccept {
				val server = sc.server.get()
				val serverData = dataService.loadServerData(server)

				val embed = if (!accessService.fromOwnerAtLeast(sc)) {
					EmbedBuilder().access(sc.user, serverData, I18n.of("no_access", serverData))
				} else {
					exit = true
					EmbedBuilder().success(sc.user, serverData, I18n.of("exit", serverData))
				}
				sc.createFollowupMessageBuilder().addEmbed(embed).send().get()
			}.get()

			if (exit) {
				BotData.exitFunction.invoke()
			}
		}
	}
}