package org.wiki.load.transforms
import org.wiki.load.models._

object WikiTransform {
  def transform(input: WikiPage): Page = {
    val latestRevisionId =
      if (input.revision.nonEmpty)
        input.revision.maxBy(rev => rev.timestamp).id
      else 0;
    val firstRevisionId =
      if (input.revision.nonEmpty)
        input.revision.minBy(rev => rev.timestamp).id
      else 0

    Page(
      wikiId = input.id,
      title = input.title,
      namespace = input.ns,
      language = input.language,
      revisionCount = input.revision.length,
      latest = latestRevisionId,
      first = firstRevisionId,
      revision = input.revision.map(
        rev => transformRevision(rev, input, firstRevisionId, latestRevisionId)
      )
    )
  }

  def transformRevision(
      input: WikiRevision,
      page: WikiPage,
      id1: Int,
      id2: Int
  ): Revision = {
    Revision(
      pageId = page.id,
      wikiId = input.id,
      sha1 = input.sha1,
      text = input.text,
      storeText = input.id == id1 || input.id == id2,
      language = page.language,
      timestamp = input.timestamp,
      contributor = transformContributor(input.contributor)
    )
  }

  def transformContributor(input: WikiContributor): Contributor = {
    Contributor(
      wikiId = input.id,
      ip = input.ip,
      username = input.username,
      anonymous = false
    )
  }
}
