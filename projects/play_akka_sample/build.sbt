lazy val hello = Project(
  "play_akka_sample", file(".")
).enablePlugins(PlayScala).settings(
  scalaVersion := "2.11.6"
)

libraryDependencies ++= Seq(
  ws,
  "com.typesafe.akka" %% "akka-remote" % "2.3.4"
)
