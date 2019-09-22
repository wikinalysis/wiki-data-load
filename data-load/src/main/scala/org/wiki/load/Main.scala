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
import org.wiki.load.transforms.WikiTransform
import org.wiki.load.utils.LanguageReader

object WordCount {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (opts, args) = ScioContext.parseArguments[PipelineOptions](cmdlineArgs)

    val RECORD_ELEMENT = "page";
    val ROOT_ELEMENT = "mediawiki";
    val ARTICLE_NAMESPACE = 0
    val INPUT_FILE =
      args.getOrElse("inputFile", "tnwiki-20190801-pages-meta-current.xml.bz2")
    val OUTPUT = args.getOrElse("output", "tmp/output")

    val sc = ScioContext(opts)

    val language: String = LanguageReader.getLanguageFromXmlFile(INPUT_FILE)
    val languageSideIn = sc.parallelize(Seq(language)).asSingletonSideInput

    val xmlRead = XmlIO
      .read()
      .from(INPUT_FILE)
      .withRootElement(ROOT_ELEMENT)
      .withRecordElement(RECORD_ELEMENT)
      .withRecordClass(classOf[WikiPage])

    val xmlWrite: PTransform[PCollection[Page], PDone] = XmlIO
      .write()
      .withRootElement(ROOT_ELEMENT)
      .withRecordClass(classOf[Page])
      .to(OUTPUT)

    val filteredPages: SCollection[WikiPage] = sc
      .customInput("fromXML", xmlRead)
      .filter((v: WikiPage) => v.ns == ARTICLE_NAMESPACE)

    val languagePages: SCollection[WikiPage] = filteredPages
      .withSideInputs(languageSideIn)
      .flatMap { (line, ctx) =>
        val language: String = ctx(languageSideIn)
        Seq(line.copy(language = language))
      }
      .toSCollection

    languagePages
      .map(WikiTransform.transform)
      .saveAsCustomOutput("toXML", xmlWrite)

    sc.pipeline.run().waitUntilFinish()
  }
}
