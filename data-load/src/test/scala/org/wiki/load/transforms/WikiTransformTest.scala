import org.scalatest.FlatSpec
import org.wiki.load.transforms.WikiTransform
import org.wiki.load.models._

// WikiRevision(id: Int, timestamp: String, model: String, format: String, sha1: String, text: String, contributor: WikiContributor)

class WikiTransformTest extends FlatSpec {
  def fixture =
    new {
      val wikiPage = new WikiPage(
        id = 1,
        ns = 0,
        language = "en",
        title = "Example Title",
        revision = Array(
          new WikiRevision(
            id = 1,
            timestamp = "2006-06-24T11:46:25Z",
            model = "mediawiki",
            format = "mediawiki",
            sha1 = "12345abcdefg",
            text = "",
            contributor = new WikiContributor(id = 0, username = "", ip = "")
          ),
          new WikiRevision(
            id = 2,
            timestamp = "2007-06-24T11:46:25Z",
            model = "mediawiki",
            format = "mediawiki",
            sha1 = "12345abcdefg",
            text = "",
            contributor = new WikiContributor(id = 0, username = "", ip = "")
          )
        )
      )
    }

  "WikiTransform" should "transform WikiPage to Page" in {
    val testPage = new WikiPage()
    assert(WikiTransform.transform(testPage).isInstanceOf[Page])
  }

  it should "put latest revision ID in `latest` field" in {
    val newPage = WikiTransform.transform(fixture.wikiPage)
    assert(newPage.latest == 2)
  }

  it should "add latest ID when only one revision present" in {
    val testPage =
      fixture.wikiPage.copy(
        revision = Array(
          new WikiRevision(
            id = 2,
            timestamp = "2007-06-24T11:46:25Z",
            model = "mediawiki",
            format = "mediawiki",
            sha1 = "12345abcdefg",
            text = "",
            contributor = new WikiContributor(id = 0, username = "", ip = "")
          )
        )
      )
    assert(WikiTransform.transform(testPage).latest == 2)
  }

  it should "add the language to revisions" in {
    val testPage = fixture.wikiPage.copy(language = "de")
    assert(WikiTransform.transform(testPage).revision(0).language == "de")
  }

  it should "default latest ID to 0" in {
    val testPage =
      fixture.wikiPage.copy(
        revision = Array()
      )
    assert(WikiTransform.transform(testPage).latest == 0)
  }

  it should "put the page ID on the revision objects" in {
    val newPage = WikiTransform.transform(fixture.wikiPage)
    assert(newPage.revision(0).pageId == newPage.wikiId)
  }
}
