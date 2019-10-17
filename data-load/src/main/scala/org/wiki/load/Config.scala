package org.wiki.load

case class WikiReaderConfig(
    projectId: String,
    databaseId: String,
    instanceId: String,
    stagingLocation: String,
    rootElement: String,
    outputLocation: String,
    dbUsername: String,
    dbPassword: String,
    inputFile: String
)
