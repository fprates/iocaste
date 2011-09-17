@echo off
cd hsqldb\bin
set SQLTOOL="java -Dsqlfile.charset=UTF-8 -jar ..\lib\sqltool.jar"
set DB_PATH=..\..\apache-tomcat\webapps
set DB_INSTALL=WEB-INF\classes\META-INF\db-install.sql
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-server\%DB_INSTALL%
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-documents\%DB_INSTALL%
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-shell\%DB_INSTALL%
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-login\%DB_INSTALL%
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-tasksel\%DB_INSTALL%
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-core-utils\%DB_INSTALL%
%SQLTOOL% localhost-sa %DB_PATH%\iocaste-office\%DB_INSTALL%