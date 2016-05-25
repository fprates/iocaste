@echo on
set SERVICE_NAME=iocaste-sap-connector-%1
prunsrv.exe delete %SERVICE_NAME%
