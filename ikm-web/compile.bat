@echo off

set PATH_COMPILE=D:\Personal\Project\IKM-Solution\IKM-SOLUTION\ikm-web
echo Compile %1 using path %PATH_COMPILE%
if not "%2"=="" echo Additional Param: client=%2

if "%1" == "web" goto compile_web
if "%1" == "engine" goto compile_engine
goto end

:compile_web
cd %PATH_COMPILE%\jakarta-software-data
call mvn clean install
cd %PATH_COMPILE%\jakarta-software-web
call mvn clean package
goto end


:compile_engine
cd %PATH_COMPILE%\jets-mmbs-lib
call mvn clean install
cd %PATH_COMPILE%\jets-mmbs-smi
call mvn clean install
cd %PATH_COMPILE%\jets-mmbs-bti
call mvn clean install
cd %PATH_COMPILE%\jets-mmbs-trx
if "%2"=="" (call mvn clean package) else (call mvn clean package -P %2)
goto end

:end
cd %PATH_COMPILE%
rem set PATH_COMPILE=
