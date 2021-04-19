plugins {
  java
  idea
  eclipse
  kotlin("jvm") version "1.4.32"
  id("net.minecrell.licenser") version "0.4.1"
  id("me.champeau.gradle.jmh") version "0.4.7"
}

group = "org.lanternpowered"
version = "1.0-SNAPSHOT"

defaultTasks("licenseFormat", "build")

repositories {
  mavenCentral()
  maven("https://jitpack.io")
  maven("https://kotlin.bintray.com/kotlinx")
  maven("https://oss.sonatype.org/content/groups/public")
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.4.3")
  implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.21")
  implementation(group = "org.checkerframework", name = "checker-qual", version = "3.6.0")
  implementation(group = "it.unimi.dsi", name = "fastutil", version = "8.4.0")
  implementation(group = "it.unimi.dsi", name = "dsiutils", version = "2.6.6")
  implementation(group = "com.github.ben-manes.caffeine", name = "caffeine", version = "2.8.5")
  implementation(group = "org.lanternpowered", name = "lmbda", version = "2.0.0-SNAPSHOT")
}

jmh {
  duplicateClassesStrategy = DuplicatesStrategy.WARN
}

tasks {
  val javadocJar = create<Jar>("javadocJar") {
    archiveBaseName.set(project.name)
    archiveClassifier.set("javadoc")
    from(javadoc)
  }

  val sourceJar = create<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
    exclude("**/*.class") // For module-info.class
  }

  assemble {
    dependsOn(sourceJar)
    dependsOn(javadocJar)
  }

  artifacts {
    archives(jar.get())
    archives(sourceJar)
    archives(javadocJar)
  }

  listOf(jar.get(), sourceJar, javadocJar).forEach {
    it.from(project.file("LICENSE.txt"))
  }

  test {
    useJUnitPlatform()
  }

  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().forEach {
    it.kotlinOptions.apply {
      jvmTarget = "1.8"
      languageVersion = "1.3"

      val args = mutableListOf<String>()
      args += "-Xjvm-default=enable"
      args += "-Xallow-result-return-type"

      fun useExperimentalAnnotation(name: String) {
        args += "-Xuse-experimental=$name"
      }

      fun enableLanguageFeature(name: String) {
        args += "-XXLanguage:+$name"
      }

      enableLanguageFeature("InlineClasses")
      enableLanguageFeature("NewInference")
      enableLanguageFeature("NonParenthesizedAnnotationsOnFunctionalTypes")

      useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
      useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
      useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
      useExperimentalAnnotation("kotlin.experimental.ExperimentalTypeInference")
      useExperimentalAnnotation("kotlin.time.ExperimentalTime")
      useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")

      freeCompilerArgs = args
    }
  }
}

license {
  header = rootProject.file("HEADER.txt")
  newLine = false
  ignoreFailures = false
  sourceSets = project.sourceSets

  include("**/*.java")
  include("**/*.kt")

  ext {
    set("name", rootProject.name)
    set("url", "https://www.lanternpowered.org")
    set("organization", "LanternPowered")
  }
}
