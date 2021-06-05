rootProject.name = "Shards"

listOf("core").forEach {
  include(it)
  project(":$it").name = "shards-$it"
}

pluginManagement {
  repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "kotlinx.atomicfu") {
        val version = requested.version ?: "0.16.0"
        useModule("org.jetbrains.kotlinx:atomicfu-gradle-plugin:$version")
      }
    }
  }
}
