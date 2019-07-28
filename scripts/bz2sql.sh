#!/bin/sh
bunzip2 -c $1 | java -jar mwdumper.jar --format=sql:1.25 > output/$1.sql