enablePlugins(CommonSettingsPlugin)
enablePlugins(DockerPackagePlugin)

name := "git-big"

resolvers += Resolver.bintrayRepo("meetup", "maven")

daemonUser in Docker := "root"

defaultLinuxInstallLocation in Docker := "/opt"

dockerEntrypoint := Seq("/opt/entrypoint.sh")

libraryDependencies ++= Seq(
  "com.meetup" %% "scala-logger" % "0.1.9",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)