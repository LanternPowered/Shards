plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()
  js {
    browser()
    nodejs()
  }
  // mingwX64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("reflect"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
      }
    }
    val commonTest by getting {}

    val jvmMain by getting {
      dependencies {
        implementation("it.unimi.dsi:fastutil:8.5.4")
        implementation("org.lanternpowered:lmbda:2.0.0-SNAPSHOT")
      }
    }

    val jsAndNativeMain by creating {}
    val jsAndNativeTest by creating {}
  }
}
