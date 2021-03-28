import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  id("io.documentnode.epub4j.java-application-conventions")

  // https://github.com/johnrengelman/shadow
  // https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

// Required by the 'shadowJar' task
project.setProperty("mainClassName", "io.documentnode.epub4j.Fileset2Epub")

dependencies {
  implementation(project(":epub4j-core"))

  implementation("net.sourceforge.htmlcleaner:htmlcleaner:2.2") {
    exclude("org.jdom", "jdom")
    exclude("org.apache.ant", "ant")
  }
  implementation("commons-vfs:commons-vfs:1.0")
  implementation("commons-lang:commons-lang:2.4")
  implementation("commons-io:commons-io:2.0.1")
}

application {
  // Define the main class for the application.
  mainClass.set("io.documentnode.epub4j.Fileset2Epub")
}

tasks.withType<ShadowJar> {
  manifest.attributes.apply {
    put("Implementation-Title", "Fileset2Epub Command Line Tool")
    //put("Implementation-Version" version)
    put("Main-Class", "io.documentnode.epub4j.Fileset2Epub")
  }
}
