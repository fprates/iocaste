@echo off
cd hsqldb\bin
set SQLTOOL=java -Dsqlfile.charset=UTF-8 -jar ..\lib\sqltool.jar
set DB_SCRIPTS=..\..\scripts\db-install-??-*.sql
for %%g in (%DB_SCRIPTS%) do %SQLTOOL% localhost-sa %%g
