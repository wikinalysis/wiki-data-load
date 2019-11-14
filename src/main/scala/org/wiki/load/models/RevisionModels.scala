package org.wiki.load.models

import javax.xml.bind.annotation.{
  XmlRootElement,
  XmlAccessorType,
  XmlAccessType,
  XmlElement
}
import javax.xml.bind.annotation.adapters._

@XmlRootElement(name = "revision")
@XmlAccessorType(XmlAccessType.FIELD)
case class FullRevision(
    wikiId: Int,
    pageId: Int,
    language: String,
    timestamp: String,
    sha1: String,
    storeText: Boolean,
    isFirst: Boolean,
    isLatest: Boolean,
    revisionNumber: Int,
    text: Text,
    textLength: Int,
    contributor: Contributor
) {
  def this() =
    this(
      wikiId = 0,
      pageId = 0,
      textLength = 0,
      language = "",
      storeText = false,
      isFirst = true,
      isLatest = true,
      revisionNumber = 0,
      timestamp = "",
      sha1 = "",
      text = new Text,
      contributor = new Contributor
    )
}

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.FIELD)
case class Text(
    revisionId: Int,
    pageId: Int,
    language: String,
    rawText: String
) {
  def this() = this(revisionId = 0, pageId = 0, language = "", rawText = "")
}
