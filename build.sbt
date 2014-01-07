name := """play-life"""

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  // Select Play modules
  //jdbc,      // The JDBC connection pool and the play.api.db API
  //anorm,     // Scala RDBMS Library
  //javaJdbc,  // Java database API
  //javaEbean, // Java Ebean plugin
  //javaJpa,   // Java JPA plugin
  //filters,   // A set of built-in filters
  javaCore,  // The core Java API
  // WebJars pull in client-side web libraries
  "org.webjars" %% "webjars-play" % "2.2.0",
  "org.webjars" % "bootstrap" % "2.3.1",
  // Add your own project dependencies in the form:
  // "group" % "artifact" % "version"
  "org.scalatest" % "scalatest_2.10" % "1.9.2" % "test",
  "org.scala-lang" % "jline" % "2.10.0-M2",
  "com.google.guava" % "guava" % "15.0"
)


play.Project.playScalaSettings

closureCompilerOptions := Seq("rjs")

atmosSettings
