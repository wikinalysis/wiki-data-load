organization := "org.apache.beam"

name := "beam-scala-examples"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.4"

lazy val scalaMainVersion = "2.12"
lazy val beamVersion = "2.1.0"
lazy val slf4jVersion = "1.7.25"
lazy val scalaTestVersion = "3.0.4"
lazy val javaxVersion = "2.2.11"

libraryDependencies ++= Seq(
  "io.scalaland" %% "chimney" % "0.3.2",
  "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % beamVersion,
  "org.apache.beam" % "beam-runners-direct-java" % beamVersion,
  "org.apache.beam" % "beam-sdks-java-io-xml" % beamVersion,
  "javax.xml.bind" % "jaxb-api" % javaxVersion,
  "com.sun.xml.bind" % "jaxb-core" % javaxVersion,
  "com.sun.xml.bind" % "jaxb-impl" % javaxVersion,
  "javax.activation" % "activation" % "1.1.1",
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
  "org.scalatest" % s"scalatest_${scalaMainVersion}" % scalaTestVersion % "test"
)

assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.first
  case PathList("META-INF", "native", xs@_*) => MergeStrategy.first // for io.netty
  case PathList("META-INF", "services", xs@_*) => MergeStrategy.filterDistinctLines // for IOChannelFactory
  case PathList("META-INF", xs@_*) => MergeStrategy.discard // otherwise google's repacks blow up
  case _ => MergeStrategy.first
}

//   <dependencies>
//     <!-- API -->
//     <dependency>  <groupId>javax.activation</groupId>
    // <artifactId>activation</artifactId>
    // <version>1.1.1</version>
//     <groupId>javax.xml.bind</groupId>
//     <artifactId>jaxb-api</artifactId>
//     <version>2.2.11</version>
// </dependency>
// <dependency>
//     <groupId>com.sun.xml.bind</groupId>
//     <artifactId>jaxb-core</artifactId>
//     <version>2.2.11</version>
// </dependency>
// <dependency>
//     <groupId>com.sun.xml.bind</groupId>
//     <artifactId>jaxb-impl</artifactId>
//     <version>2.2.11</version>
// </dependency>