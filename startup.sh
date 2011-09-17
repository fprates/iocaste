#!/bin/bash
export JPDA_ADDRESS=8000
export JPDA_TRANSPORT=dt_socket
FROM=$(pwd)
echo 'starting database manager...'
cd ../hsqldb/bin/
java -Dsqlfile.charset=UTF-8 -cp ../lib/hsqldb.jar org.hsqldb.server.Server &
echo 'done'
echo 'starting servlet container...'
cd ../../apache-tomcat/bin/
./catalina.sh jpda run
echo 'done'
cd $FROM

