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

    val INPUT_FILE = args.required("inputFile")
    val OUTPUT = args.required("output")
    val DB_PASS = args.required("dbPassword")
    val CONNECTION_NAME = args.required("connectionName")

    val DB_USER = args.getOrElse("dbUser", "root")
    val DB_NAME = args.getOrElse("dbName", "wikidata")

    val config: WikiReaderConfig =
      new WikiReaderConfig(
        outputXml = false,
        rootElement = ROOT_ELEMENT,
        outputLocation = "tmp/",
        inputFile = INPUT_FILE,
        stagingLocation = "",
        databaseId = DB_NAME,
        dbUsername = DB_USER,
        dbPassword = DB_PASS,
        jdbcUrl = getJdbcUrl(DB_NAME, CONNECTION_NAME),
        projectId = ""
      )

    WikiReaderApp.run(config, opts)
  }

  def getJdbcUrl(dbName: String, connectionName: String): String = {
    s"jdbc:mysql://google/${dbName}?" +
      s"cloudSqlInstance=${connectionName}&" +
      s"socketFactory=com.google.cloud.sql.mysql.SocketFactory"
  }
}
