plugins {
  kotlin("multiplatform")
  // id("kotlinx.atomicfu") version "0.16.0"
}

kotlin {
  jvm()
  js {
    browser()
    nodejs()
  }
  // TODO: Native is not compiling
  //  https://youtrack.jetbrains.com/issue/KT-47144
  // mingwX64("native") // TODO: Temporarily for creating a "common" native sourceset

  if (System.getProperty("idea.sync.active") == "true") {
    mingwX64("native")
  }

  // IDE error fix:
  //  Class '...' has several compatible actual declarations in modules
  // mingwX64 is currently only needed at compile time anyway,
  // not when setting up the project
  if (System.getProperty("idea.sync.active") != "true") {
    // mingwX64()
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("reflect"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
        implementation("org.jetbrains.kotlinx:atomicfu:0.16.1")
      }
    }
    val commonTest by getting {}

    val jvmMain by getting {
      dependencies {
        implementation("it.unimi.dsi:fastutil:8.5.4")
        implementation("org.lanternpowered:lmbda:2.0.0")
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
