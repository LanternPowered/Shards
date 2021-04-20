plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()
  js {
    browser()
    nodejs()
  }
  mingwX64()
  mingwX64("native") // TODO: Temporarily for creating a "common" native sourceset

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

    val jsAndJvmMain by creating {}
    val jsAndJvmTest by creating {}

    val jsAndNativeMain by creating {}
    val jsAndNativeTest by creating {}

    // val nativeMain by creating {}
    // val nativeTest by creating {}
  }
}
