@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist MODIFYTASKCMDACTUAL.TXT del MODIFYTASKCMDACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\Angela\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Angela < modifytaskcmdinput.txt > MODIFYTASKCMDACTUAL.TXT

REM compare the output to the expected output
FC MODIFYTASKCMDACTUAL.TXT MODIFYTASKCMDEXPECTED.TXT
