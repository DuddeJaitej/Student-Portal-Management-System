$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$logRoot = Join-Path $root 'logs'
$pidFile = Join-Path $logRoot 'backend-pids.json'

if (-not $env:SPRING_DATASOURCE_PASSWORD) {
    $securePassword = Read-Host 'Enter MySQL password' -AsSecureString
    $passwordPtr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
    try {
        $env:SPRING_DATASOURCE_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($passwordPtr)
    } finally {
        [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($passwordPtr)
    }
}

New-Item -ItemType Directory -Force -Path $logRoot | Out-Null

foreach ($serviceName in @('StudentRegister', 'AdminRegister', 'AdminProfile', 'AdminStudents', 'AdminStudentCourse', 'AdminStudentsTimetable', 'AdminFaculty', 'AdminFacultyCourse', 'AdminFacultyTimetable', 'FacultyRegister', 'FacultyHome', 'MyFee', 'PortalService', 'AdminDashboard')) {
    $outLog = Join-Path $logRoot "$serviceName.out.log"
    $errLog = Join-Path $logRoot "$serviceName.err.log"
    if (-not (Test-Path $outLog)) {
        New-Item -ItemType File -Path $outLog | Out-Null
    }
    if (-not (Test-Path $errLog)) {
        New-Item -ItemType File -Path $errLog | Out-Null
    }
}

$services = @(
    @{ Name = 'StudentRegister'; Path = 'backend\Admin\StudentRegister'; Port = 8083; Database = 'Student_Service' },
    @{ Name = 'AdminRegister'; Path = 'backend\Admin\Admin_Register'; Port = 9093; Database = 'Admin_Service' },
    @{ Name = 'AdminProfile'; Path = 'backend\Admin\Admin_Profile'; Port = 9101; Database = 'Admin_Service' },
    @{ Name = 'AdminStudents'; Path = 'backend\Admin\Admin_Students'; Port = 9098; Database = 'AdminStudents_Details' },
    @{ Name = 'AdminStudentCourse'; Path = 'backend\Admin\Admin_Student_Course'; Port = 9094; Database = 'AdminStudent_Course' },
    @{ Name = 'AdminStudentsTimetable'; Path = 'backend\Admin\Admin_Students_Timetable'; Port = 9100; Database = 'AdminStudents_Timetable' },
    @{ Name = 'AdminFaculty'; Path = 'backend\Admin\Admin_Faculty'; Port = 9102; Database = 'AdminFaculty_Details' },
    @{ Name = 'AdminFacultyCourse'; Path = 'backend\Admin\Admin_Faculty_Course'; Port = 9095; Database = 'AdminFaculty_Course' },
    @{ Name = 'AdminFacultyTimetable'; Path = 'backend\Admin\Admin_Faculty_Timetable'; Port = 9099; Database = 'AdminFaculty_Timetable' },
    @{ Name = 'FacultyRegister'; Path = 'backend\Admin\FacultyRegister'; Port = 9070; Database = 'AdminFaculty_Details' },
    @{ Name = 'FacultyHome'; Path = 'backend\Admin\FacultyHome'; Port = 9071; Database = 'AdminFaculty_Course' },
    @{ Name = 'MyFee'; Path = 'backend\Admin\MyFee'; Port = 9096; Database = 'Admin_Service' },
    @{ Name = 'PortalService'; Path = 'backend\Admin\Portal_Service'; Port = 9110; Database = 'Portal_Service' },
    @{ Name = 'AdminDashboard'; Path = 'backend\Admin\AdminDashboard'; Port = 9083; Database = $null }
)

$runningJson = Get-Content $pidFile -Raw -ErrorAction SilentlyContinue | ConvertFrom-Json -ErrorAction SilentlyContinue
$running = if ($runningJson -is [Array]) { $runningJson } elseif ($runningJson) { @($runningJson) } else { @() }

function Get-ListeningProcessId([int]$port) {
    $connection = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($connection) {
        return $connection.OwningProcess
    }

    return $null
}

$started = @()
foreach ($service in $services) {
    $listeningProcessId = Get-ListeningProcessId $service.Port
    if ($listeningProcessId) {
        $started += [pscustomobject]@{ Name = $service.Name; ProcessId = $listeningProcessId; Port = $service.Port }
        Write-Host ("{0} is already running (PID {1}, port {2})" -f $service.Name, $listeningProcessId, $service.Port)
        continue
    }

    $serviceRoot = Join-Path $root $service.Path
    $stdout = Join-Path $logRoot "$($service.Name).out.log"
    $stderr = Join-Path $logRoot "$($service.Name).err.log"
    $startInfo = New-Object System.Diagnostics.ProcessStartInfo
    $startInfo.FileName = 'cmd.exe'
    $startInfo.Arguments = "/d /c mvn.cmd spring-boot:run -Dspring-boot.run.jvmArguments=-Dserver.port=$($service.Port) > `"$stdout`" 2> `"$stderr`""
    $startInfo.WorkingDirectory = $serviceRoot
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $true
    $startInfo.EnvironmentVariables['SPRING_DATASOURCE_USERNAME'] = 'root'
    $startInfo.EnvironmentVariables['SPRING_DATASOURCE_PASSWORD'] = $env:SPRING_DATASOURCE_PASSWORD
    if ($service.Database) {
        $startInfo.EnvironmentVariables['SPRING_DATASOURCE_URL'] = "jdbc:mysql://localhost:3306/$($service.Database)"
    }
    if ($service.Name -eq 'AdminDashboard') {
        $startInfo.EnvironmentVariables['STUDENTS_SERVICE_URL'] = 'http://localhost:9098'
        $startInfo.EnvironmentVariables['FACULTY_SERVICE_URL'] = 'http://localhost:9102'
    }

    $process = New-Object System.Diagnostics.Process
    $process.StartInfo = $startInfo
    $process.Start() | Out-Null
    $processId = $process.Id
    $started += [pscustomobject]@{ Name = $service.Name; ProcessId = $processId; Port = $service.Port }
    Write-Host ("Started {0} (PID {1}, port {2})" -f $service.Name, $processId, $service.Port)
}

$started | ConvertTo-Json | Set-Content $pidFile
Write-Host ""
Write-Host "All backend services started in one PowerShell session."
Write-Host "Logs: $logRoot"
Write-Host "Stop: .\stop-backend.ps1"
