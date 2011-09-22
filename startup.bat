@echo off
set JPDA_ADDRESS=8000
set JPDA_TRANSPORT=dt_socket
echo "starting database manager..."
cd hsqldb\bin\
start "hsqldb" java -Dsqlfile.charset=UTF-8 -cp ..\lib\hsqldb.jar org.hsqldb.server.Server
echo "done"
echo "starting servlet container..."
cd ..\..\apache-tomcat\bin\
start "tomcat" catalina.bat jpda start
echo "done"
cd ..\..\
