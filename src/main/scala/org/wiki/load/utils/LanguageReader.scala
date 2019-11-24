package org.wiki.load.utils

import java.io.{
  BufferedInputStream,
  BufferedReader,
  FileInputStream,
  InputStreamReader
}
import org.apache.commons.compress.compressors.CompressorStreamFactory
import java.util.regex.Pattern
import java.util.regex.Matcher

object LanguageReader {
  def getLanguageFromXmlFile(fileIn: String): String = {
    getLanguageFromXMLHeader(
      getBufferedReaderForCompressedFile(fileIn).readLine()
    )
  }

  def getBufferedReaderForCompressedFile(fileIn: String): BufferedReader = {
    val fin: FileInputStream = new FileInputStream(fileIn)
    val bis: BufferedInputStream = new BufferedInputStream(fin)
    val input = new CompressorStreamFactory().createCompressorInputStream(bis)
    new BufferedReader(new InputStreamReader(input))
  }

  def getLanguageFromXMLHeader(head: String): String = {
    val pattern: Pattern = Pattern.compile("xml:lang=\"(.*?)\"")
    val matcher: Matcher = pattern.matcher(head)
    matcher.find()
    matcher.group(1)
  }
}
