package org.wiki.load.readers

import org.wiki.load.models._
import org.wiki.load.WikiReaderConfig

import org.apache.beam.sdk.io.xml.XmlIO
import org.apache.beam.sdk.options.PipelineOptions
import org.apache.beam.sdk.transforms.PTransform
import org.apache.beam.sdk.values.{PCollection, _}

object XmlWriter {
  def getXmlReader(opts: WikiReaderConfig) = {
    XmlIO
      .read()
      .from(opts.inputFile)
      .withRootElement(opts.rootElement)
      .withRecordElement("page")
      .withRecordClass(classOf[WikiPage])
  }
}
