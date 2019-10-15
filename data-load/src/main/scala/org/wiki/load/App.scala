package org.wiki.load;

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

object WikiReaderApp {
  def run(opts: WikiReaderConfig, scOpts: PipelineOptions): Unit = {
    val sc = ScioContext(scOpts)

    val language: String = LanguageReader.getLanguageFromXmlFile(opts.inputFile)
    val languageSideIn = sc.parallelize(Seq(language)).asSingletonSideInput

    val xmlRead = XmlIO
      .read()
      .from(opts.inputFile)
      .withRootElement(opts.rootElement)
      .withRecordElement("page")
      .withRecordClass(classOf[WikiPage])

    val xmlWritePages: PTransform[PCollection[Page], PDone] = XmlIO
      .write()
      .withRootElement(opts.rootElement)
      .withRecordClass(classOf[Page])
      .to(opts.outputLocation + "pages")

    val xmlWriteRevisions: PTransform[PCollection[FullRevision], PDone] = XmlIO
      .write()
      .withRootElement(opts.rootElement)
      .withRecordClass(classOf[FullRevision])
      .to(opts.outputLocation + "revisions")

    val filteredPages: SCollection[WikiPage] = sc
      .customInput("fromXML", xmlRead)
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
      .saveAsCustomOutput("toXML", xmlWritePages)

    val revisionsOnly = languagePages
      .flatMap((v: Page) => v.revision)
      .map(RevisionTransform.transform)
      .saveAsCustomOutput("toXml", xmlWriteRevisions)

    sc.pipeline.run().waitUntilFinish()
  }
}
