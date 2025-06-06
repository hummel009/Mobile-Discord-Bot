package com.github.hummel.mdb.core.utils

import com.github.hummel.mdb.core.bean.ServerData
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.entity.user.User

fun EmbedBuilder.error(user: User, serverData: ServerData, desc: String): EmbedBuilder =
	setAuthor(user).setTitle(I18n.of("msg_error", serverData)).setDescription(desc)

fun EmbedBuilder.access(user: User, serverData: ServerData, desc: String): EmbedBuilder =
	setAuthor(user).setTitle(I18n.of("msg_access", serverData)).setDescription(desc)

fun EmbedBuilder.success(user: User, serverData: ServerData, desc: String): EmbedBuilder =
	setAuthor(user).setTitle(I18n.of("msg_success", serverData)).setDescription(desc)

fun String.build(name: String, preprompt: String): String =
	trimIndent().replace("\t", "").replace("\n", " ").format(name, name, preprompt) + "\n"