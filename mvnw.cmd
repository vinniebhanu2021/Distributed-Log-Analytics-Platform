@echo off
set "MAVEN_PROJECTBASEDIR=%~dp0"
:: Remove trailing backslash if present
if "%MAVEN_PROJECTBASEDIR:~-1%"=="\" set "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

if exist "%JAVA_HOME%\bin\java.exe" (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVA_EXE=java"
)

if not exist ".mvn\wrapper\maven-wrapper.jar" (
    echo Maven Wrapper JAR not found. Attempting to download...
    powershell -ExecutionPolicy Bypass -File setup_maven.ps1
)

:: Execute Maven Wrapper Main Class explicitly since the downloaded jar might lack Main-Class attribute
"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp .mvn\wrapper\maven-wrapper.jar org.apache.maven.wrapper.MavenWrapperMain %*
