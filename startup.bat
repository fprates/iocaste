@echo off
echo "starting database manager..."
cd ..\hsqldb\bin\
call rundb-des.bat
echo "done"
echo "starting servlet container..."
cd ..\..\apache-tomcat\bin\
call tomcat-start.bat
echo "done"
