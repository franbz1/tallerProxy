# Dev run with Maven Wrapper + optional .env (same folder as pom.xml).
# Usage (from tallerProxy): .\scripts\run-spring-boot-with-env.ps1
# Does not require a global Maven installation (uses mvnw.cmd).

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
Set-Location $root

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
        }
    }
}

& .\mvnw.cmd spring-boot:run
