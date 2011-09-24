#!/bin/bash
FROM=$(pwd)
SQLTOOL="java -Dsqlfile.charset=UTF-8 -jar ../lib/sqltool.jar"
DB_SCRIPTS=../../scripts/db-install-??-*.sql

cd hsqldb/bin
for i in $DB_SCRIPTS;
do
   $SQLTOOL localhost-sa $i
done
cd $FROM
