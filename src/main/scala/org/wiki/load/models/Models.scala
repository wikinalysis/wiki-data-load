package org.wiki.load.models
import javax.xml.bind.annotation.{
  XmlRootElement,
  XmlAccessorType,
  XmlAccessType,
  XmlElement
}
import javax.xml.bind.annotation.adapters._

@XmlRootElement(name = "page")
@XmlAccessorType(XmlAccessType.FIELD)
case class Page(
    wikiId: Int,
    namespace: Int,
    title: String,
    language: String,
    latest: Int,
    first: Int,
    revisionCount: Int,
    @XmlElement(name = "revision") revision: Array[Revision]
) {
  def this() =
    this(
      wikiId = 0,
      namespace = 0,
      title = "",
      language = "",
      latest = 0,
      first = 0,
      revisionCount = 0,
      revision = new Array[Revision](0)
    )
}

@XmlRootElement(name = "revision")
@XmlAccessorType(XmlAccessType.FIELD)
case class Revision(
    wikiId: Int,
    pageId: Int,
    language: String,
    timestamp: String,
    storeText: Boolean,
    sha1: String,
    text: String,
    contributor: Contributor
) {
  def this() =
    this(
      wikiId = 0,
      pageId = 0,
      storeText = false,
      language = "",
      timestamp = "",
      sha1 = "",
      text = "",
      contributor = new Contributor
    )
}

@XmlRootElement(name = "contributor")
@XmlAccessorType(XmlAccessType.FIELD)
case class Contributor(
    wikiId: Int,
    ip: String,
    username: String,
    anonymous: Boolean
) {
  def this() =
    this(wikiId = 0, ip = "", username = "", anonymous = false)
}
