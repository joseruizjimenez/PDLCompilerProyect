echo off
set CLASSPATH=.;.\lib\JFlex.jar
set CLASSPATH=.;.\lib\java-cup-11a.jar;.\lib\java-cup-11a-runtime.jar
echo ---------------------------
echo Compilando Codigo Programa
echo ---------------------------
java compilador.Main .\CupFlexTest\test.txt

PAUSE

echo -------------------
echo Fin de Ejecucion 
echo -------------------
PAUSE

del textoTraducido.obj ceulib.o a.exe