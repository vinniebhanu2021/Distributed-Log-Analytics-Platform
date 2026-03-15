@echo off
echo Cleaning target directories manually...
if exist common\target rmdir /s /q common\target
if exist ingestion-service\target rmdir /s /q ingestion-service\target
if exist processor-service\target rmdir /s /q processor-service\target
if exist analytics-service\target rmdir /s /q analytics-service\target

echo Running Maven Build (skipping clean goal to avoid locking issues)...
call .\mvnw.cmd package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo Build Failed!
    exit /b %ERRORLEVEL%
)
echo Build Success!
