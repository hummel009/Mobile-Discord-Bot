import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.kotlin.plugin.compose") version "latest.release"
	id("org.jetbrains.compose") version "latest.release"
}

android {
	namespace = "com.github.hummel.mdb"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.github.hummel.mdb"
		minSdk = 34
		targetSdk = 35
		versionName = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))
	}

	packaging {
		resources {
			excludes.add("META-INF/*")
		}
	}
}

dependencies {
	implementation(project(":appCommon"))

	implementation("com.google.android.material:material:latest.release")

	val composeBom = platform("androidx.compose:compose-bom:latest.release")
	implementation(composeBom)
	implementation("androidx.compose.material3:material3")
	implementation("androidx.activity:activity-compose")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}