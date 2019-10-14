organization := "org.apache.beam"

name := "beam-scala-examples"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.9"

lazy val scalaMainVersion = "2.12"
lazy val beamVersion = "2.11.0"
lazy val slf4jVersion = "1.7.25"
lazy val scalaTestVersion = "3.0.8"
lazy val javaxVersion = "2.2.11"
lazy val scioVersion = "0.7.4"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-compress" % "1.18",
  // Sweble WikiParser
  "org.sweble.wikitext" % "swc-parser-lazy" % "3.1.9",
  "org.sweble.wikitext" % "swc-engine" % "3.1.9",
  "de.fau.cs.osr.utils" % "utils-parent" % "3.0.8" pomOnly (),
  // SCIO
  "io.scalaland" %% "chimney" % "0.3.2",
  "com.spotify" %% "scio-core" % scioVersion,
  "com.spotify" %% "scio-test" % scioVersion % "test",
  // BEAM and XML
  "org.apache.beam" % "beam-runners-direct-java" % beamVersion,
  "org.apache.beam" % "beam-sdks-java-io-xml" % beamVersion,
  "javax.xml.bind" % "jaxb-api" % javaxVersion,
  "com.sun.xml.bind" % "jaxb-core" % javaxVersion,
  "com.sun.xml.bind" % "jaxb-impl" % javaxVersion,
  "javax.activation" % "activation" % "1.1.1",
  // Misc
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
  "org.scalatest" % s"scalatest_${scalaMainVersion}" % scalaTestVersion % "test"
)

assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.first
  case PathList("META-INF", "native", xs @ _*) =>
    MergeStrategy.first // for io.netty
  case PathList("META-INF", "services", xs @ _*) =>
    MergeStrategy.filterDistinctLines // for IOChannelFactory
  case PathList("META-INF", xs @ _*) =>
    MergeStrategy.discard // otherwise google's repacks blow up
  case _ => MergeStrategy.first
}
