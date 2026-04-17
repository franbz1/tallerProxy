# Run the packaged JAR after loading a ".env" file from the tallerProxy directory (if present).
# Usage (from tallerProxy): .\scripts\run-jar-with-env.ps1
# Requires: JDK 11+ on PATH. Build the JAR first: .\mvnw.cmd package -DskipTests

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
Set-Location $root

$jar = Join-Path $root "target\tallerProxy-0.0.1-SNAPSHOT.jar"
if (-not (Test-Path $jar)) {
    Write-Host "JAR not found: $jar" -ForegroundColor Red
    Write-Host "Build with (no global Maven required): .\mvnw.cmd package -DskipTests" -ForegroundColor Yellow
    exit 1
}

$envFile = Join-Path $root ".env"
if (Test-Path $envFile) {
    Get-Content $envFile | ForEach-Object {
        $line = $_.Trim()
        if ($line -match '^\s*#' -or $line -eq "") { return }
        $idx = $line.IndexOf("=")
        if ($idx -gt 0) {
            $name = $line.Substring(0, $idx).Trim()
            $value = $line.Substring($idx + 1).Trim().Trim('"')
            [Environment]::SetEnvironmentVariable($name, $value, "Process")
            Write-Host "Set $name from .env"
        }
    }
}
else {
    Write-Host "No .env file (optional). Copy .env.example to .env to configure APP_CORS_ALLOWED_ORIGINS."
}

Write-Host "Starting: $jar"
& java -jar $jar
