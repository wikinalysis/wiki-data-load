package org.wiki.load;

import java.io.{
  BufferedInputStream,
  BufferedReader,
  FileInputStream,
  InputStreamReader
}

import com.spotify.scio._
import com.spotify.scio.jdbc._
import com.spotify.scio.values.SCollection
import org.apache.beam.sdk.io.xml.XmlIO
import org.apache.beam.sdk.options.PipelineOptions
import org.apache.beam.sdk.transforms.PTransform
import org.apache.beam.sdk.values.{PCollection, _}
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.wiki.load.models._
import org.wiki.load.readers._
import org.wiki.load.transforms._
import org.wiki.load.utils.LanguageReader

object WikiReaderApp {
  def run(opts: WikiReaderConfig, scOpts: PipelineOptions): Unit = {
    val sc = ScioContext(scOpts)

    val language = opts.language
    val languageOnly = sc.parallelize(Seq(language))
    val languageSideIn = languageOnly.asSingletonSideInput

    val connectionOpts = getConnectionOptions(opts)

    val filteredPages: SCollection[WikiPage] = sc
      .customInput("fromXML", XmlWriter.getXmlReader(opts))
      .filter((v: WikiPage) => v.ns == 0)

    val languagePages: SCollection[Page] = filteredPages
      .withSideInputs(languageSideIn)
      .flatMap { (line, ctx) =>
        val language: String = ctx(languageSideIn)
        Seq(line.copy(language = language))
      }
      .toSCollection
      .map(WikiTransform.transform)

    val pagesOnly = languagePages
      .map((v: Page) => v.copy(revision = Array[Revision]()))

    val revisionsOnly = languagePages
      .flatMap((v: Page) => v.revision)
      .map(RevisionTransform.transform)

    languageOnly.saveAsJdbc(
      SqlWriter.writeLanguage(connectionOpts)
    )
    pagesOnly.saveAsJdbc(SqlWriter.writePages(connectionOpts))
    revisionsOnly.saveAsJdbc(SqlWriter.writeRevisions(connectionOpts))

    sc.pipeline.run().waitUntilFinish()
  }

  def getConnectionOptions(opts: WikiReaderConfig): JdbcConnectionOptions =
    JdbcConnectionOptions(
      username = opts.dbUsername,
      password = Some(opts.dbPassword),
      driverClass = classOf[com.mysql.jdbc.Driver],
      connectionUrl = opts.jdbcUrl
    )
}
