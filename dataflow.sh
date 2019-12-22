MAIN_CLASS=org.wiki.load.WikiReader
PROJECT_ID=project
DB_PASSWORD=password
CONNECTION_NAME=connection
TEMP_LOCATION=temp_location
INPUT_FILE=input_file
LANG=language

sbt pack

CLASSPATH="target/pack/lib/*" java $MAIN_CLASS \
--exec.mainClass=$MAIN_CLASS --project=$PROJECT_ID \
--dbPassword=$DB_PASSWORD --connectionName=$CONNECTION_NAME --language=$LANG \
--gcpTempLocation=$TEMP_LOCATION --inputFile=$INPUT_FILE --runner=DataflowRunner