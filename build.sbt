enablePlugins(CommonSettingsPlugin)
enablePlugins(NexusPlugin)

name := "git-big"

resolvers += Resolver.bintrayRepo("meetup", "maven")

libraryDependencies ++= Seq(
  "com.meetup" %% "scala-logger" % "0.1.9",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)