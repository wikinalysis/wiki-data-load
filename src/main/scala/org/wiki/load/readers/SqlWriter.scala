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
        "REPLACE INTO pages (wiki_id, revision_count, title, wiki_language, latest_id, first_id, id) values(?, ?, ?, ?, ?, ?, ?)",
      preparedStatementSetter = (page: Page, s) => {
        s.setLong(1, page.wikiId);
        s.setLong(2, page.revisionCount);
        s.setString(3, page.title);
        s.setString(4, page.language);
        s.setString(5, page.latest + "-" + page.language);
        s.setString(6, page.first + "-" + page.language);
        s.setString(7, page.wikiId + "-" + page.language);
      }
    );
  }

  def writeLanguage(
      connOpts: JdbcConnectionOptions
  ): JdbcWriteOptions[String] = {
    JdbcWriteOptions(
      connectionOptions = connOpts,
      statement = "REPLACE INTO languages (id) VALUES (?)",
      preparedStatementSetter = (language: String, s) => {
        s.setString(1, language)
      }
    )
  }

  def writeRevisions(
      connOpts: JdbcConnectionOptions
  ): JdbcWriteOptions[FullRevision] = {
    JdbcWriteOptions(
      connectionOptions = connOpts,
      statement =
        "REPLACE INTO revisions (wiki_id, page_id, wiki_language, sha1, created_at, text_length, has_text, is_first, is_latest, revision_number, id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
      preparedStatementSetter = (revision: FullRevision, s) => {
        s.setLong(1, revision.wikiId);
        s.setString(2, revision.pageId + "-" + revision.language);
        s.setString(3, revision.language);
        s.setString(4, revision.sha1);
        s.setString(5, revision.timestamp);
        s.setLong(6, revision.textLength);
        s.setBoolean(7, revision.storeText);
        s.setBoolean(8, revision.isFirst);
        s.setBoolean(9, revision.isLatest);
        s.setLong(10, revision.revisionNumber);
        s.setString(11, revision.wikiId + "-" + revision.language);
      }
    )
  }
}
