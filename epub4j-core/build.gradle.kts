plugins {
  id("io.documentnode.epub4j.java-library-conventions")
}

dependencies {
  implementation("net.sf.kxml:kxml2:2.3.0")
  implementation("xmlpull:xmlpull:1.1.3.4d_b4_min")
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
