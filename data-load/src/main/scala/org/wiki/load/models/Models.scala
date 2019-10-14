package org.wiki.load.models
import javax.xml.bind.annotation.{
  XmlRootElement,
  XmlAccessorType,
  XmlAccessType,
  XmlElement
}
import javax.xml.bind.annotation.adapters._

class IntOptionAdapter extends OptionAdapter[Int](0)
class OptionAdapter[A](nones: A*) extends XmlAdapter[A, Option[A]] {
  def marshal(v: Option[A]): A = v.getOrElse(nones(0))
  def unmarshal(v: A) = if (nones contains v) None else Some(v)
}

// ==== Wiki Classes ====
@XmlRootElement(name = "page")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiPage(
    id: Int,
    ns: Int,
    title: String,
    language: String,
    @XmlElement(name = "revision") revision: Array[WikiRevision]
) {
  def this() = this(0, 0, "", "", new Array[WikiRevision](0))
}

@XmlRootElement(name = "revision")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiRevision(
    id: Int,
    timestamp: String,
    model: String,
    format: String,
    sha1: String,
    text: String,
    contributor: WikiContributor
) {
  def this() =
    this(
      id = 0,
      timestamp = "",
      model = "",
      format = "",
      sha1 = "",
      text = "",
      contributor = new WikiContributor
    )
}

@XmlRootElement(name = "contributor")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiContributor(id: Int, ip: String, username: String) {
  def this() = this(id = 0, ip = "", username = "")
}

// ==== Classes ====
@XmlRootElement(name = "page")
@XmlAccessorType(XmlAccessType.FIELD)
case class Page(
    wikiId: Int,
    namespace: Int,
    title: String,
    language: String,
    latest: Int,
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
    sha1: String,
    text: String,
    contributor: Contributor
) {
  def this() =
    this(
      wikiId = 0,
      pageId = 0,
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
