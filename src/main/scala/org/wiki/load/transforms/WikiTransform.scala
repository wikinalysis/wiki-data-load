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
      revision = input.revision
        .sortBy(_.timestamp)
        .zipWithIndex
        .map {
          case (element, index) =>
            transformRevision(element, input, index, input.revision.length)
        }
    )
  }

  def transformRevision(
      input: WikiRevision,
      page: WikiPage,
      index: Int,
      count: Int
  ): Revision = {
    Revision(
      pageId = page.id,
      wikiId = input.id,
      sha1 = input.sha1,
      text = input.text,
      storeText = index == 0 || index == count - 1,
      isFirst = index == 0,
      isLatest = index == count - 1,
      revisionNumber = index,
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
