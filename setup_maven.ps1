$ErrorActionPreference = "Stop"

$wrapperDir = ".mvn\wrapper"
$wrapperJar = "$wrapperDir\maven-wrapper.jar"
$wrapperUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

if (-not (Test-Path $wrapperDir)) {
    New-Item -ItemType Directory -Force -Path $wrapperDir | Out-Null
}

Write-Host "Downloading Maven Wrapper JAR..."
Invoke-WebRequest -Uri $wrapperUrl -OutFile $wrapperJar

Write-Host "Maven Wrapper JAR downloaded successfully."
Write-Host "You can now run 'mvnw.cmd clean package' or '.\mvnw.cmd clean package'"
