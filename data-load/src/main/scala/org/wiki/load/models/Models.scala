package org.wiki.load.models
import javax.xml.bind.annotation.{XmlRootElement, XmlAccessorType, XmlAccessType, XmlElement}

// ==== Wiki Classes ====
@XmlRootElement(name = "page")
@XmlAccessorType(XmlAccessType.FIELD)
case class WikiPage(id: Int, ns: Int, title: String, language: String, @XmlElement(name="revision") revision: Array[WikiRevision]) {
  def this() = this(0, 0, "", "", new Array[WikiRevision](0))
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
case class Page(wikiId: Int, namespace: Int, title: String, language: String, revisionCount: Int, @XmlElement(name="revision") revision: Array[Revision]) {
  def this() = this(wikiId=0, namespace=0, title="", language="", revisionCount=0, revision=new Array[Revision](0))
}

@XmlRootElement(name="revision")
@XmlAccessorType(XmlAccessType.FIELD)
case class Revision(wikiId: Int, pageId: Int, language: String, timestamp: String, sha1: String, textLength: Int, text: String, contributor: WikiContributor) {
  def this() = this(wikiId=0, pageId=0, language="", timestamp="", sha1="", textLength=0, text="", contributor=new WikiContributor)
}
