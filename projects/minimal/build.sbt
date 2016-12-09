lazy val hello = Project(
  "minimal", file(".")
).enablePlugins(PlayScala).settings(
  scalaVersion := "2.11.6"
)
