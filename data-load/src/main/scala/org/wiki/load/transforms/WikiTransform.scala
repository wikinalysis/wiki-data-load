package org.wiki.load.transforms
import org.wiki.load.models._

object WikiTransform {
  def transform(input: WikiPage): Page = {
    Page(
      wikiId = input.id,
      title = input.title,
      namespace = input.ns,
      language = input.language,
      revisionCount = input.revision.length,
      latest =
        if (input.revision.nonEmpty)
          Some(input.revision.maxBy(rev => rev.timestamp).id)
        else None,
      revision = input.revision.map(rev => transformRevision(rev, input))
    )
  }

  def transformRevision(input: WikiRevision, page: WikiPage): Revision = {
    Revision(
      pageId = page.id,
      wikiId = input.id,
      sha1 = input.sha1,
      text = input.text,
      language = page.language,
      timestamp = input.timestamp,
      contributor = transformContributor(input.contributor)
    )
  }

  def transformContributor(input: WikiContributor): WikiContributor = {
    input
  }
}
