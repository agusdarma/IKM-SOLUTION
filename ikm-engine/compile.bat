@echo off 

set PATH_COMPILE=D:\Personal\Project\IKM-Solution\IKM-SOLUTION\ikm-engine
echo Compile %1 using path %PATH_COMPILE%
if not "%2"=="" echo Additional Param: client=%2

if "%1" == "all" goto compile_all

if "%1" == "lib" goto compile_lib
if "%1" == "trx" goto compile_trx

goto end

:compile_all
cd %PATH_COMPILE%\ikm-engine-lib
call mvn clean install
cd %PATH_COMPILE%\ikm-engine-trx
call mvn clean package
goto end

:compile_lib
cd %PATH_COMPILE%\ikm-engine-lib
call mvn clean install
goto end

:compile_trx
cd %PATH_COMPILE%\ikm-engine-trx
if "%2"=="" (call mvn clean package) else (call mvn clean package -Dclient=%2)
goto end

:end
cd %PATH_COMPILE%