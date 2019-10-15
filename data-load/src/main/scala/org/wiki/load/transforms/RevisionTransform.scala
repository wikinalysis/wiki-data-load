package org.wiki.load.transforms
import org.wiki.load.models._

object RevisionTransform {
  def transform(input: Revision): FullRevision = {
    FullRevision(
      sha1 = input.sha1,
      wikiId = input.wikiId,
      pageId = input.pageId,
      language = input.language,
      timestamp = input.timestamp,
      contributor = input.contributor,
      textLength = input.text.length,
      text = new Text(
        revisionId = input.wikiId,
        pageId = input.pageId,
        language = input.language,
        rawText = input.text
      )
    )
  }
}
