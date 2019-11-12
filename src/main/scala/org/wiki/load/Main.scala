package org.wiki.load

import java.io.{
  BufferedInputStream,
  BufferedReader,
  FileInputStream,
  InputStreamReader
}

import com.spotify.scio._
import com.spotify.scio.values.SCollection
import org.apache.beam.sdk.io.xml.XmlIO
import org.apache.beam.sdk.options.PipelineOptions
import org.apache.beam.sdk.transforms.PTransform
import org.apache.beam.sdk.values.{PCollection, _}
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.wiki.load.models._
import org.wiki.load.transforms._
import org.wiki.load.utils.LanguageReader

object WikiReader {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (opts, args) = ScioContext.parseArguments[PipelineOptions](cmdlineArgs)

    val RECORD_ELEMENT = "page";
    val ROOT_ELEMENT = "mediawiki";
    val INPUT_FILE =
      args.getOrElse(
        "inputFile",
        "tnwiki-20191101-pages-meta-history.xml.bz2"
      )
    val OUTPUT = args.getOrElse("output", "tmp/page")

    val config: WikiReaderConfig =
      new WikiReaderConfig(
        outputXml = false,
        rootElement = ROOT_ELEMENT,
        outputLocation = "tmp/",
        inputFile = INPUT_FILE,
        stagingLocation = "",
        databaseId = "",
        instanceId = "",
        dbUsername = "root",
        dbPassword = "P@ssw0rd",
        jdbcUrl =
          "jdbc:mysql://localhost:3307/myschema?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        projectId = ""
      )

    WikiReaderApp.run(config, opts)
  }
}
