package org.wiki.load

case class WikiReaderConfig(
    outputXml: Boolean,
    projectId: String,
    databaseId: String,
    stagingLocation: String,
    rootElement: String,
    outputLocation: String,
    dbUsername: String,
    dbPassword: String,
    jdbcUrl: String,
    inputFile: String
)
