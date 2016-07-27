licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
bintrayReleaseOnPublish := false
bintrayRepository := "maven"
bintrayOrganization := Some("vpon")

pomExtra := {
  <scm>
    <connection>scm:git:https://github.com/vpon/konfig.git</connection>
    <developerConnection>scm:git:https://github.com/vpon/konfig.git</developerConnection>
    <url>https://github.com/vpon/konfig/</url>
  </scm>
  <developers>
    <developer>
      <name>Yan Su</name>
      <email>yan.su@vpon.com</email>
    </developer>
  </developers>
}
