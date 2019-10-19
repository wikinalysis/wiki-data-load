package org.wiki.load.readers

import org.wiki.load.models._
import com.spotify.scio.jdbc._

object SqlWriter {
  def writePages(
      connOpts: JdbcConnectionOptions
  ): JdbcWriteOptions[Page] = {
    JdbcWriteOptions(
      connectionOptions = connOpts,
      statement =
        "INSERT INTO pages (wiki_id, revision_count, title, language, latest) values(?, ?, ?, ?, ?)",
      preparedStatementSetter = (page: Page, s) => {
        s.setLong(1, page.wikiId);
        s.setLong(2, page.revisionCount);
        s.setString(3, page.title);
        s.setString(4, page.language);
        s.setLong(5, page.latest);
        s.setLong(6, page.first);
      }
    );
  }

  def writeRevisions(
      connOpts: JdbcConnectionOptions
  ): JdbcWriteOptions[FullRevision] = {
    JdbcWriteOptions(
      connectionOptions = connOpts,
      statement =
        "INSERT INTO revisions (wiki_id, page_id, language, sha1, timestamp, text_length) values(?, ?, ?, ?)",
      preparedStatementSetter = (revision: FullRevision, s) => {
        s.setLong(1, revision.wikiId);
        s.setLong(2, revision.pageId);
        s.setString(3, revision.language);
        s.setString(4, revision.sha1);
        s.setString(5, revision.timestamp);
        s.setLong(6, revision.textLength);
      }
    )
  }

  def writeText(
      connOpts: JdbcConnectionOptions
  ): JdbcWriteOptions[Text] = {
    JdbcWriteOptions(
      connectionOptions = connOpts,
      statement =
        "INSERT INTO texts (page_id, revision_id, language, raw_text) values(?, ?, ?)",
      preparedStatementSetter = (text: Text, s) => {
        s.setLong(1, text.pageId);
        s.setLong(2, text.revisionId);
        s.setString(3, text.language);
        s.setString(4, text.rawText);
      }
    )
  }

  def writeContributor(
      connOpts: JdbcConnectionOptions
  ): JdbcWriteOptions[Text] = {
    JdbcWriteOptions(
      connectionOptions = connOpts,
      statement =
        "INSERT INTO texts (contributor_id, revision_id, language, raw_text) values(?, ?, ?)",
      preparedStatementSetter = (contributor: Text, s) => {
        s.setLong(1, contributor.id);
        s.setLong(2, text.revisionId);
        s.setString(3, text.language);
        s.setString(4, text.rawText);
      }
    )
  }
}
