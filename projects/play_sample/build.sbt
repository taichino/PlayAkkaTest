lazy val hello = Project(
  "play_sample", file(".")
).enablePlugins(PlayScala).settings(
  scalaVersion := "2.11.6"
)

libraryDependencies ++= Seq(
  ws
)
