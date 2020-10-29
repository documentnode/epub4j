plugins {
  // Apply the java Plugin to add support for Java.
  java
}

group = "io.documentnode"

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
}

dependencies {
  implementation("io.documentnode:minilog:1.0")

  // Don't use the traditional logging framework to make it easier
  // for creating a native shared library (smaller)
  // implementation("org.slf4j:slf4j-api:1.8.0-beta4")
  // implementation("org.slf4j:slf4j-simple:1.8.0-beta4")

  // Use JUnit for testing
  testImplementation("junit:junit:4.10")
  testImplementation("org.mockito:mockito-core:3.6.0")
}

tasks.test {
  // Use JUnit for unit tests.
  useJUnit()
}
