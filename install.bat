@echo off
copy conf\sqltool.rc %UserProfile%
mkdir transport
call startup.bat
call regendb.bat
