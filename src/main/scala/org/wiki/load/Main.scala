package org.wiki.load

import com.spotify.scio._
import org.apache.beam.sdk.options.PipelineOptions

object WikiReader {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (opts, args) = ScioContext.parseArguments[PipelineOptions](cmdlineArgs)

    val ROOT_ELEMENT = "mediawiki";

    val INPUT_FILE = args.required("inputFile")
    val LANGUAGE = args.required("language")
    val DB_PASS = args.required("dbPassword")
    val CONNECTION_NAME = args.required("connectionName")

    val DB_USER = args.getOrElse("dbUser", "root")
    val DB_NAME = args.getOrElse("dbName", "wikidata")

    val config: WikiReaderConfig =
      new WikiReaderConfig(
        rootElement = ROOT_ELEMENT,
        inputFile = INPUT_FILE,
        dbUsername = DB_USER,
        dbPassword = DB_PASS,
        language = LANGUAGE,
        jdbcUrl = getJdbcUrl(DB_NAME, CONNECTION_NAME)
      )

    WikiReaderApp.run(config, opts)
  }

  def getJdbcUrl(dbName: String, connectionName: String): String = {
    s"jdbc:mysql://google/${dbName}?" +
      s"cloudSqlInstance=${connectionName}&" +
      s"socketFactory=com.google.cloud.sql.mysql.SocketFactory"
  }
}
