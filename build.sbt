name := """play-life"""

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  javaCore,  // The core Java API
  "org.webjars" %% "webjars-play" % "2.2.0",
  "org.webjars" % "bootstrap" % "2.3.1",
  "org.scalatest" % "scalatest_2.10" % "1.9.2" % "test",
  "org.scala-lang" % "jline" % "2.10.0-M2",
  "com.google.guava" % "guava" % "15.0"
)

play.Project.playScalaSettings

closureCompilerOptions := Seq("rjs")
