import sbt._
import Keys._

val scioVersion = "0.7.4"
val beamVersion = "2.11.0"
val scalaMacrosVersion = "2.1.1"

val scalaMainVersion = "2.12"

val javaxVersion = "2.2.11"
val slf4jVersion = "1.7.25"
val scalaTestVersion = "3.0.8"

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  organization := "org.wiki.load",
  // Semantic versioning http://semver.org/
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.10",
  scalacOptions ++= Seq(
    "-target:jvm-1.8",
    "-deprecation",
    "-feature",
    "-unchecked"
  ),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val paradiseDependency =
  "org.scalamacros" % "paradise" % scalaMacrosVersion cross CrossVersion.full
lazy val macroSettings = Seq(
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  addCompilerPlugin(paradiseDependency)
)

lazy val root: Project = project
  .in(file("."))
  .settings(commonSettings)
  .settings(macroSettings)
  .settings(
    name := "Wikipedia XML Processor",
    description := "Processing Wikipedia because I can",
    publish / skip := true,
    run / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    libraryDependencies ++= Seq(
      // SCIO and BEAM
      "com.spotify" %% "scio-core" % scioVersion,
      "com.spotify" %% "scio-test" % scioVersion % Test,
      "com.spotify" %% "scio-jdbc" % scioVersion,
      "org.apache.beam" % "beam-runners-direct-java" % beamVersion,
      "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % beamVersion,
      "org.slf4j" % "slf4j-simple" % "1.7.25",
      // XML
      "org.apache.beam" % "beam-sdks-java-io-xml" % beamVersion,
      "javax.xml.bind" % "jaxb-api" % javaxVersion,
      "com.sun.xml.bind" % "jaxb-core" % javaxVersion,
      "com.sun.xml.bind" % "jaxb-impl" % javaxVersion,
      "javax.activation" % "activation" % "1.1.1",
      // Misc
      "mysql" % "mysql-connector-java" % "8.0.17",
      "com.google.cloud.sql" % "mysql-socket-factory-connector-j-8" % "1.0.15",
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
      "org.scalatest" % s"scalatest_${scalaMainVersion}" % scalaTestVersion % "test",
      "org.apache.commons" % "commons-compress" % "1.18"
    )
  )
  .enablePlugins(PackPlugin)

lazy val repl: Project = project
  .in(file(".repl"))
  .settings(commonSettings)
  .settings(macroSettings)
  .settings(
    name := "repl",
    description := "Scio REPL for scio job",
    libraryDependencies ++= Seq(
      "com.spotify" %% "scio-repl" % scioVersion
    ),
    Compile / mainClass := Some("com.spotify.scio.repl.ScioShell"),
    publish / skip := true
  )
  .dependsOn(root)
