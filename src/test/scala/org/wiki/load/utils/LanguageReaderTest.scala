import org.scalatest.FlatSpec
import org.wiki.load.utils.LanguageReader

class LanguageReaderTest extends FlatSpec {
  "getLanguageFromXMLHeader" should "extract 1-letter language" in {
    val testString = "<mediawiki version=\"0.10\" xml:lang=\"a\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "a")
  }

  "getLanguageFromXMLHeader" should "extract 2-letter language" in {
    val testString = "<mediawiki version=\"0.10\" xml:lang=\"ab\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "ab")
  }

  "getLanguageFromXMLHeader" should "extract 3-letter language" in {
    val testString = "<mediawiki version=\"0.10\" xml:lang=\"abc\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "abc")
  }

  "getLanguageFromXMLHeader" should "extract 4-letter language" in {
    val testString = "<mediawiki version=\"0.10\" xml:lang=\"abcd\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "abcd")
  }

  "getLanguageFromXMLHeader" should "extract 5-letter language" in {
    val testString = "<mediawiki version=\"0.10\" xml:lang=\"abcde\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "abcde")
  }

  "getLanguageFromXMLHeader" should "extract in a greedy fashion" in {
    val testString =
      "<mediawiki version=\"0.10\" xml:lang=\"abcde\" other:property=\"xyz\">"
    assert(LanguageReader.getLanguageFromXMLHeader(testString) == "abcde")
  }
}
