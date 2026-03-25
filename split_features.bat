@echo off
setlocal enabledelayedexpansion

echo Splitting feature files across 6 nodes...

set "FEATURE_DIR=src/test/java/scenarios/features"
set "ALL_TEST_FEATURES=all_test_features.txt"

set "NODE1=features_node1.txt"
set "NODE2=features_node2.txt"
set "NODE3=features_node3.txt"
set "NODE4=features_node4.txt"
set "NODE5=features_node5.txt"
set "NODE6=features_node6.txt"

set "BASE_DIR=%CD%"

del /f /q %ALL_TEST_FEATURES% %NODE1% %NODE2% %NODE3% %NODE4% %NODE5% %NODE6% >nul 2>&1

REM Collect all feature files with @test tag
for /r "%FEATURE_DIR%" %%f in (*.feature) do (

    findstr /r /c:"^@cart" "%%f" >nul

    if !errorlevel! == 0 (

        set "featurePath=%%f"

        REM make relative path
        set "featurePath=!featurePath:%BASE_DIR%\=!"
        REM convert \ to /
        set "featurePath=!featurePath:\=/!"
        echo !featurePath!>> %ALL_TEST_FEATURES%
    )
)

REM load features into array
set /a index=0
for /f "usebackq delims=" %%f in ("%ALL_TEST_FEATURES%") do (
    set /a index+=1
    set "feature[!index!]=%%f"
)
set /a total=%index%
if %total%==0 (
    echo ❌ No @test123 feature files found
    exit /b 1
)
echo Total matching features: %total%
REM round robin distribution across 6 nodes
for /L %%i in (1,1,%total%) do (
    set /a mod=%%i %% 6
    if !mod!==1 (
        echo !feature[%%i]!>> %NODE1%
    ) else if !mod!==2 (
        echo !feature[%%i]!>> %NODE2%
    ) else if !mod!==3 (
        echo !feature[%%i]!>> %NODE3%
    ) else if !mod!==4 (
        echo !feature[%%i]!>> %NODE4%
    ) else if !mod!==5 (
        echo !feature[%%i]!>> %NODE5%
    ) else (
        echo !feature[%%i]!>> %NODE6%
    )
)

echo.
echo Distribution summary:

for %%n in (1 2 3 4 5 6) do (

    if exist features_node%%n.txt (
        set /a count=0
        for /f %%x in ('type features_node%%n.txt ^| find /c /v ""') do set count=%%x
        echo Node %%n -> !count! features
    )
)
echo.
echo ✅ Feature files successfully split across 6 nodes
exit /b 0