package org.wiki.load

case class WikiReaderConfig(
    rootElement: String,
    dbUsername: String,
    dbPassword: String,
    jdbcUrl: String,
    language: String,
    inputFile: String
)
