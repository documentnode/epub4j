plugins {
  id("io.documentnode.epub4j.java-library-conventions")

  // https://github.com/lessthanoptimal/gversion-plugin
  id("com.peterabeles.gversion") version "1.9"

  // https://github.com/researchgate/gradle-release
  // https://plugins.gradle.org/plugin/net.researchgate.release
  id("net.researchgate.release") version "3.0.2"
}

dependencies {
  implementation("net.sf.kxml:kxml2:2.3.0")
  implementation("xmlpull:xmlpull:1.1.3.4d_b4_min")
  implementation("net.lingala.zip4j:zip4j:2.11.5")
}

gversion {
  // path is relative to the sub-project by default
  // Gradle variables can also be used
  // E.g. "${project.rootDir}/module/src/main/java"
  srcDir = "src/main/java/"
  classPackage = "io.documentnode.epub4j.util"
  // optional. If not specified GVersion is used
  className = "GVersion"
  // optional. This is the default
  dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
  // optional. UTC is default
  timeZone = "UTC"
  // optional. print out extra debug information
  debug = false
  // optional. Can be Java or Kotlin, case insensitive
  language = "java"
  // optional. Force types to be explicitly printed
  explicitType = false
}

tasks.compileJava {
  dependsOn(tasks.createVersionFile)
}

tasks.withType<Javadoc> {
  (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:none", true)
  (options as StandardJavadocDocletOptions).addStringOption("sourcepath", "")
  (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
}

java {
  withJavadocJar()
  withSourcesJar()
}

signing {
  sign(publishing.publications)
}

val ossrhUsername: String? by project
val ossrhPassword: String? by project

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      groupId = "$group"
      from(components["java"])

      pom {
        name.set("epub4j")
        description.set("A java library for reading/writing/manipulating EPUB files, with improvements based on epublib.")
        url.set("https://documentnode.io/epub4j")
        //properties.set(mapOf(
        //    "myProp" to "value",
        //    "prop.with.dots" to "anotherValue"
        //))
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        developers {
          developer {
            id.set("jakew")
            name.set("Jake Wang")
            email.set("jake@documentnode.io")
            organization.set("Document Node")
            organizationUrl.set("https://documentnode.io")
          }
        }
        scm {
          connection.set("scm:git:git@github.com:documentnode/epub4j.git")
          developerConnection.set("scm:git:git@github.com:documentnode/epub4j.git")
          url.set("https://github.com/documentnode/epub4j")
        }
      }
    }
  }
  repositories {
    maven {
      val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
      val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
      url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
      credentials {
        username = ossrhUsername
        password = ossrhPassword
      }
    }
  }
}
