@echo on
set SERVICE_NAME=iocaste-sap-connector-%2
set PR_INSTALL=%1\prunsrv.exe
set CONFIG_FILE=%1\%3

REM Log do serviço
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=%1\logs
set PR_STDOUTPUT=%1\logs\stdout.txt
set PR_STDERROR=%1\logs\stderr.txt
set PR_LOGLEVEL=Error

REM Localização do java
if %4=="AMD64" ( goto server ) else ( goto client )
:server
set PR_JVM=%JAVA_HOME%\bin\server\jvm.dll
goto stage1
:client
set PR_JVM=%JAVA_HOME%\bin\client\jvm.dll
goto stage1

:stage1
set PR_CLASSPATH=%1\sap-connector.jar

REM Configuração de partida
set PR_STARTUP=auto
set PR_STARTMODE=jvm
set PR_STARTCLASS=org.quantic.iocasteconnector.Main
set PR_STARTMETHOD=start

REM Configuração de parada
set PR_STOPMODE=jvm
set PR_STOPCLASS=org.quantic.iocasteconnector.Main
set PR_STOPMETHOD=stop

prunsrv.exe install %SERVICE_NAME% --Description="Iocaste SAP Connector" --Install="%PR_INSTALL%" --Jvm="%PR_JVM%" --Classpath="%PR_CLASSPATH%" --Startup="%PR_STARTUP%" --StartMode="%PR_STARTMODE%" --StartClass="%PR_STARTCLASS%" --StartMethod="%PR_STARTMETHOD%" --StartParams="--config-file;%CONFIG_FILE%" --StopMode="%PR_STOPMODE%" --StopClass="%PR_STOPCLASS%" --StopMethod="%PR_STOPMETHOD%" --StdError="auto" --StdOutput="auto"
prunsrv.exe start %SERVICE_NAME%
