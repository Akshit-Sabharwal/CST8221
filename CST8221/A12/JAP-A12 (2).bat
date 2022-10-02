:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: ASSIGNMENTS - CST8221 - Fall 2022
:: ---------------------------------------------------------------------
:: Begin of Script (Assignments - F22)
:: ---------------------------------------------------------------------


CLS

:: LOCAL VARIABLES ....................................................

SET SRCDIR=src
SET BINDIR=bin
SET BINOUT=numPuzzle-javac.out
SET BINERR=numPuzzle-javac.err
SET JARNAME=CST8221.jar
SET JAROUT=NumPuzzle-jar.out
SET JARERR=NumPuzzle-jar.err
SET DOCDIR=doc
SET DOCPACK=numPuzzle
SET DOCOUT=numPuzzle-javadoc.out
SET DOCERR=numPuzzle-javadoc.err
SET MAINCLASSSRC=src/numPuzzle/Main.java
SET MAINCLASSBIN=numPuzzle.Main


@echo off

ECHO " _________________________________ "
ECHO "|     __    _  ___    ___  _      |"
ECHO "|    |  |  / \ \  \  /  / / \     |"
ECHO "|    |  | /   \ \  \/  / /   \    |"
ECHO "|    |  |/  _  \ \    / /  _  \   |"
ECHO "|  __|  |  / \  \ \  / /  / \  \  |"
ECHO "|  \____/_/   \__\ \/ /__/   \__\ |"
ECHO "|                                 |"
ECHO "| .. ALGONQUIN COLLEGE - 2022F .. |"
ECHO "|_________________________________|"
ECHO "                                   "
ECHO "[ASSIGNMENT SCRIPT ---------------]"

ECHO "1. Compiling ......................"
javac -cp ".;%SRCDIR%/*" %MAINCLASSSRC% -d %BINDIR% > %BINOUT% 2> %BINERR%

:: ECHO "Running  ........................."
:: start java -cp ".;%BINDIR%/*" %MAINCLASSBIN%

ECHO "2. Creating Javadoc ..............."
javadoc -cp ".;%BINDIR%/*" -d %DOCDIR% -sourcepath %SRCDIR% -subpackages %DOCPACK% > %DOCOUT% 2> %DOCERR%

cd src
ECHO "3. Running Jar ...................."
start  %JARNAME%
cd ..

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on

:: ---------------------------------------------------------------------
:: End of Script (Assignments - F22)
:: ---------------------------------------------------------------------

