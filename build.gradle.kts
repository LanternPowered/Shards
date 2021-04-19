plugins {
  kotlin("multiplatform") version "1.4.32" apply false
  id("net.minecrell.licenser") version "0.4.1"
}

allprojects {
  group = "org.lanternpowered"
  version = "0.0.1"

  repositories {
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlinx")
    maven("https://oss.sonatype.org/content/groups/public")
  }
}

subprojects {
  afterEvaluate {
    apply(plugin = "net.minecrell.licenser")

    val multiplatform = extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()

    multiplatform?.apply {
      targets
        .filterIsInstance<org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget>()
        .flatMap { it.compilations }
        .forEach {
          it.apply {
            kotlinOptions {
              jvmTarget = "1.8"
            }
          }
        }

      sourceSets {
        val commonMain by getting {}
        val commonTest by getting {
          dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
          }
        }

        // val jvmMain = findByName("jvmMain")
        val jvmTest = findByName("jvmTest")

        val jsAndNativeCommonMain = findByName("jsAndNativeMain")
        val jsAndNativeCommonTest = findByName("jsAndNativeTest")

        val jsMain = findByName("jsMain")
        val jsTest = findByName("jsTest")

        val nativeMain = findByName("nativeCommonMain")
        val nativeTest = findByName("nativeCommonTest")

        jvmTest?.dependencies {
          implementation(kotlin("test-junit"))
        }
        jsTest?.dependencies {
          implementation(kotlin("test-js"))
        }

        if (jsAndNativeCommonMain != null) {
          jsAndNativeCommonMain.dependsOn(commonMain)
          nativeMain?.dependsOn(jsAndNativeCommonMain)
          jsMain?.dependsOn(jsAndNativeCommonMain)
        }
        if (jsAndNativeCommonTest != null) {
          jsAndNativeCommonTest.dependsOn(commonTest)
          if (jsAndNativeCommonMain != null)
            jsAndNativeCommonTest.dependsOn(jsAndNativeCommonMain)
          nativeTest?.dependsOn(jsAndNativeCommonTest)
          jsTest?.dependsOn(jsAndNativeCommonTest)
        }
        if (jsMain != null)
          jsTest?.dependsOn(jsMain)
        if (nativeMain != null)
          nativeTest?.dependsOn(nativeMain)

        all {
          languageSettings.apply {
            enableLanguageFeature("InlineClasses")
            enableLanguageFeature("NewInference")
            enableLanguageFeature("NonParenthesizedAnnotationsOnFunctionalTypes")

            useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
            useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
            useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            useExperimentalAnnotation("kotlin.experimental.ExperimentalTypeInference")
            useExperimentalAnnotation("kotlin.time.ExperimentalTime")

            // useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
            // useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
          }
        }
      }
    }

    license {
      header = rootProject.file("HEADER.txt")
      newLine = false
      ignoreFailures = false

      val sourceSetContainer = project.the<SourceSetContainer>()

      if (multiplatform != null) {
        val temp = mutableListOf<SourceSet>()
        for ((name, kotlinSourceSet) in multiplatform.sourceSets.asMap) {
          temp += sourceSetContainer.create(project.name + "_" + name) {
            allSource.source(kotlinSourceSet.kotlin)
          }
        }
        gradle.taskGraph.whenReady {
          // Remove them when the license plugin has detected them
          // so the dev environment doesn't get polluted
          sourceSetContainer.removeAll(temp)
        }
      }

      include("**/*.kt")

      ext {
        set("name", rootProject.name)
        set("url", "https://www.lanternpowered.org")
        set("organization", "LanternPowered")
      }
    }
  }
}
