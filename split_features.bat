@echo off
setlocal enabledelayedexpansion

echo Splitting feature files...

REM Define the base feature directory
set "FEATURE_DIR=src/test/java/scenarios/features"
set "ALL_TEST_FEATURES=all_test_features.txt"
set "NODE1=features_node1.txt"
set "NODE2=features_node2.txt"
set "BASE_DIR=%CD%"

REM Cleanup previous output
del /f /q %ALL_TEST_FEATURES% %NODE1% %NODE2% >nul 2>&1

REM Find feature files with @test at Feature level
for /r "%FEATURE_DIR%" %%f in (*.feature) do (
    findstr /r /c:"^@test" "%%f" >nul
    if !errorlevel! == 0 (
        set "featurePath=%%f"
        REM Convert absolute path to relative path without the base directory prefix
        REM Make sure the path is calculated correctly by using %FEATURE_DIR% in place of the base path
        set "featurePath=!featurePath:%BASE_DIR%\=!"
        set "featurePath=!featurePath:\=/!"
        REM Ensure we're getting only the correct relative path
        set "featurePath=!featurePath:%FEATURE_DIR%\=!"
        echo !featurePath!>> %ALL_TEST_FEATURES%
    )
)

REM Count how many test features we found
set /a count=0
for /f %%i in (%ALL_TEST_FEATURES%) do (
    set /a count+=1
)

echo Total @test features found: %count%

if %count%==0 (
    echo ERROR: No feature files with @test found. Exiting.
    exit /b 0
)

REM Compute half and split
set /a half=%count% / 2
set /a index=0

(for /f %%f in (%ALL_TEST_FEATURES%) do (
    if !index! lss %half% (
        echo %%f
    )
    set /a index+=1
)) > %NODE1%

(for /f "skip=%half%" %%f in (%ALL_TEST_FEATURES%) do (
    echo %%f
)) > %NODE2%

echo Split complete: %half% features to node1, %count% - %half% to node2

exit /b 0
