@echo off
setlocal
set "ROOT=%~dp0"

echo ========================================================
echo    Presidency University - Starting all backend services
echo    Local MySQL mode - no service windows will be opened
echo ========================================================
echo.
echo Make sure the MySQL80 Windows service is running before continuing.
echo.

powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%ROOT%start-backend.ps1"
if errorlevel 1 (
    echo.
    echo Backend startup failed. Check the logs folder for service errors.
    exit /b 1
)

echo.
echo All 14 backend services were launched in the background.
echo Logs: %ROOT%logs
echo Stop all services: %ROOT%stop-backend.ps1