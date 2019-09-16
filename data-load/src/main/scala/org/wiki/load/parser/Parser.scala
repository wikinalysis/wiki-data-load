package org.wiki.load.parser

import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;

object Parser {
  def parse(id: Int, title: String, text: String): EngProcessedPage = {
    val config: WikiConfig = DefaultConfigEnWp.generate();
    val pageTitle: PageTitle = PageTitle.make(config, title)
    val pageId: PageId = new PageId(pageTitle, id)
    new WtEngineImpl(config).postprocess(pageId, text, null)
  }
}