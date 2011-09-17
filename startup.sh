#!/bin/bash
FROM=$(pwd)
echo 'starting database manager...'
cd ../hsqldb/bin/
./rundb-des.sh &
echo 'done'
echo 'starting servlet container...'
cd ../../apache-tomcat/bin/
./tomcat-start.sh
echo 'done'
cd $FROM

