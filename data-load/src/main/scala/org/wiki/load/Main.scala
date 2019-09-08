package org.wiki.load

import java.lang

import com.spotify.scio._
import com.spotify.scio.values.SCollection
import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.io.xml.XmlIO;
import org.apache.beam.sdk.options.Validation.Required
import org.apache.beam.sdk.options.{Default, Description, PipelineOptions, PipelineOptionsFactory}
import org.apache.beam.sdk.transforms.DoFn.ProcessElement
import org.apache.beam.sdk.transforms.{MapElements, Filter, SimpleFunction, PTransform}
import org.apache.beam.sdk.values._
import org.apache.beam.sdk.values.{KV, PCollection}
import javax.xml.bind.annotation.{XmlRootElement, XmlAccessorType, XmlAccessType, XmlElement}

object WordCount {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (opts, args) = ScioContext.parseArguments[PipelineOptions](cmdlineArgs)

    val RECORD_ELEMENT = "page";
    val ROOT_ELEMENT = "mediawiki";
    val ARTICLE_NAMESPACE = 0
    val INPUT_FILE = args.getOrElse("inputFile", "tnwiki-20190720-pages-articles-multistream.xml.bz2")
    val OUTPUT = args.getOrElse("output", "tmp/output")

    val sc = ScioContext(opts)

    var pipeline: Pipeline = sc.pipeline

    var xmlRead = XmlIO.read().from(INPUT_FILE).withRootElement(ROOT_ELEMENT).withRecordElement(RECORD_ELEMENT).withRecordClass(classOf[WikiPage])

    var xmlWrite: PTransform[PCollection[Page], PDone] = XmlIO.write().withRootElement(ROOT_ELEMENT).withRecordClass(classOf[Page]).to(OUTPUT)

    // var transformPages: PTransform[PCollection[WikiPage], PCollection[Page]] = MapElements.via(new ProcessPage)

    sc
      .customInput("fromXML", xmlRead)
      .filter((v: WikiPage) => v.ns == ARTICLE_NAMESPACE)
      .map(WikiTransforms.transformPage)
      .saveAsCustomOutput("toXML", xmlWrite)

    // pipeline.apply("ReadFiles", xmlRead)
    //   // .apply("Filter Namespace", new NamespaceFilter)
    //   .apply("Transform Elements", MapElements.via(new ProcessPage))
    //   .apply("WriteWords", xmlWrite)

    sc.pipeline.run().waitUntilFinish()
  }
}

// ======================================= Transforms =============================================

object WikiTransforms {
  def transformPage(input: WikiPage): Page = {
    Page(id=input.id, namespace=input.ns, title=input.title, revisionCount=input.revision.length, revision=input.revision.map(rev => transformRevision(rev)))
  }

  def transformRevision(input: WikiRevision): Revision = {
    Revision(id=input.id, timestamp=input.timestamp, sha1=input.sha1, textLength=input.text.length, text=input.text, contributor=transformContributor(input.contributor))
  }

  def transformContributor(input: WikiContributor): WikiContributor = {
    input
  }

}

// ==== Wiki Classes ====
@XmlRootElement(name = "page")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiPage(id: Int, ns: Int, title: String, @XmlElement(name="revision") revision: Array[WikiRevision]) {
  def this() = this(0, 0, "", new Array[WikiRevision](0))
}

@XmlRootElement(name = "revision")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiRevision(id: Int, timestamp: String, model: String, format: String, sha1: String, text: String, contributor: WikiContributor) {
  def this() = this(id=0, timestamp="", model="", format="", sha1="", text="", contributor=new WikiContributor)
}

@XmlRootElement(name="contributor")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiContributor(id: Int, ip: String, username: String) {
  def this() = this(id=0, ip="", username="")
}

// ==== Classes ====
@XmlRootElement(name="page")
@XmlAccessorType(XmlAccessType.FIELD)
case class Page(id: Int, namespace: Int, title: String, revisionCount: Int, @XmlElement(name="revision") revision: Array[Revision]) {
  def this() = this(id=0, namespace=0, title="", revisionCount=0, revision=new Array[Revision](0))
}

@XmlRootElement(name="revision")
@XmlAccessorType(XmlAccessType.FIELD)
case class Revision(id: Int, timestamp: String, sha1: String, textLength: Int, text: String, contributor: WikiContributor) {
  def this() = this(id=0, timestamp="", sha1="", textLength=0, text="", contributor=new WikiContributor)
}