plugins {
  id("io.documentnode.epub4j.java-library-conventions")
}

dependencies {
  implementation("net.sf.kxml:kxml2:2.3.0")
  implementation("xmlpull:xmlpull:1.1.3.4d_b4_min")
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "$group"

      from(components["java"])
    }
  }
}
