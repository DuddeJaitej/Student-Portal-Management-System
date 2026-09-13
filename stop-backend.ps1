$pidFile = Join-Path (Split-Path -Parent $MyInvocation.MyCommand.Path) 'logs\backend-pids.json'

if (-not (Test-Path $pidFile)) {
    Write-Host 'No backend PID file found.'
    exit 0
}

$parsed = Get-Content $pidFile -Raw | ConvertFrom-Json
$entries = if ($parsed -is [Array]) { $parsed } elseif ($parsed) { @($parsed) } else { @() }

function Stop-ProcessTree([int]$processId) {
    $children = Get-CimInstance Win32_Process -Filter "ParentProcessId = $processId" -ErrorAction SilentlyContinue
    foreach ($child in $children) {
        Stop-ProcessTree $child.ProcessId
    }

    Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
}

foreach ($entry in $entries) {
    $process = Get-Process -Id $entry.ProcessId -ErrorAction SilentlyContinue
    if ($process) {
        Stop-ProcessTree $entry.ProcessId
        Write-Host ("Stopped {0} (PID {1})" -f $entry.Name, $entry.ProcessId)
    }
}

Remove-Item $pidFile -Force
Write-Host 'Backend services stopped.'
