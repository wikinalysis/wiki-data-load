import org.scalatest.FlatSpec
import org.wiki.load.utils.LanguageReader

class LanguageReaderTest extends FlatSpec {
  "getLanguageFromXMLHeader" should "extract language" in {
    val testString = "<mediawiki version=\"0.10\" xml:lang=\"tn\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "tn")
  }
}
