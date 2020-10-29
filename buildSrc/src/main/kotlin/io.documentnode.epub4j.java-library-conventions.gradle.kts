plugins {
  // Apply the common convention plugin for shared build configuration
  // between library and application projects.
  id("io.documentnode.epub4j.java-common-conventions")

  // Apply the java-library plugin for API and implementation separation.
  `java-library`

  `maven-publish`
  `signing`
}
