package org.wiki.load

import java.io.{BufferedInputStream, BufferedReader, FileInputStream, InputStreamReader}

import com.spotify.scio._
import com.spotify.scio.values.SCollection
import org.apache.beam.sdk.io.xml.XmlIO
import org.apache.beam.sdk.options.PipelineOptions
import org.apache.beam.sdk.transforms.PTransform
import org.apache.beam.sdk.values.{PCollection, _}
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.wiki.load.models._

object WordCount {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (opts, args) = ScioContext.parseArguments[PipelineOptions](cmdlineArgs)

    val RECORD_ELEMENT = "page";
    val ROOT_ELEMENT = "mediawiki";
    val ARTICLE_NAMESPACE = 0
    val INPUT_FILE = args.getOrElse("inputFile", "tnwiki-20190720-pages-articles-multistream.xml.bz2")
    val OUTPUT = args.getOrElse("output", "tmp/output")

    val sc = ScioContext(opts)

    val firstLine = getBufferedReaderForCompressedFile(INPUT_FILE).readLine()
    val language: String = Utils.getLanguageFromXMLHeader(firstLine)
    val languageSideIn = sc.parallelize(Seq(language)).asSingletonSideInput

    val xmlRead = XmlIO.read().from(INPUT_FILE).withRootElement(ROOT_ELEMENT).withRecordElement(RECORD_ELEMENT).withRecordClass(classOf[WikiPage])

    val xmlWrite: PTransform[PCollection[Page], PDone] = XmlIO.write().withRootElement(ROOT_ELEMENT).withRecordClass(classOf[Page]).to(OUTPUT)

    val filteredPages: SCollection[WikiPage] = sc
      .customInput("fromXML", xmlRead)
      .filter((v: WikiPage) => v.ns == ARTICLE_NAMESPACE)
    
    val languagePages: SCollection[WikiPage] =  filteredPages
        .withSideInputs(languageSideIn)
        .flatMap {
          (line, ctx) =>
            val language: String = ctx(languageSideIn)
            Seq(line.copy(language = language))
        }
      .toSCollection

   
    languagePages.map(WikiTransforms.transform)
      .saveAsCustomOutput("toXML", xmlWrite)

    sc.pipeline.run().waitUntilFinish()
  }

  def getBufferedReaderForCompressedFile (fileIn: String): BufferedReader = {
    val fin: FileInputStream = new FileInputStream(fileIn)
    val bis: BufferedInputStream = new BufferedInputStream(fin)
    val input = new CompressorStreamFactory().createCompressorInputStream(bis)
    new BufferedReader(new InputStreamReader(input))
  }

}

object Utils {
  def getLanguageFromXMLHeader(head: String): String = {
    val start = head.indexOf("xml:lang")
    head.substring(start+10, start+12)
  }
}

// ======================================= Transforms =============================================

object WikiTransforms {
  def transform(input: WikiPage): Page = {
    Page(wikiId=input.id, language=input.language, namespace=input.ns, title=input.title, revisionCount=input.revision.length, revision=input.revision.map(rev => transformRevision(rev, input)))
  }

  def transformRevision(input: WikiRevision, page: WikiPage): Revision = {
    Revision(wikiId=input.id, language=page.language, pageId=page.id, timestamp=input.timestamp, sha1=input.sha1, textLength=input.text.length, text=input.text, contributor=transformContributor(input.contributor))
  }

  def transformContributor(input: WikiContributor): WikiContributor = {
    input
  }
}