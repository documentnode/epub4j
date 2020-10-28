plugins {
  // Apply the java Plugin to add support for Java.
  java
}

group = "io.documentnode"

repositories {
  // Use JCenter for resolving dependencies.
  jcenter()
}

dependencies {
  implementation("org.slf4j:slf4j-api:1.8.0-beta4")
//  implementation("org.slf4j:slf4j-simple:1.8.0-beta4")
  implementation("org.slf4j:slf4j-jdk14:1.8.0-beta4")

  // Use JUnit for testing
  testImplementation("junit:junit:4.10")
  testImplementation("org.mockito:mockito-core:3.6.0")
}

tasks.test {
  // Use JUnit for unit tests.
  useJUnit()
}
