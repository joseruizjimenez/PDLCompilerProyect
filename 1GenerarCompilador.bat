echo off
echo PRACTICA PDL 2012
echo ---------------------
echo Generando Flex
echo ---------------------

set CLASSPATH=.;.\lib\JFlex.jar
java JFlex.Main .\CupFlexTest\AnalizadorMorfologico.flex
move .\CupFlexTest\AnalizadorMorfologico.java .\compilador
echo Flex creado
echo *********************
pause

set CLASSPATH=.;.\lib\java-cup-11a.jar;.\lib\java-cup-11a-runtime.jar
echo ---------------------
echo Generando Cup
echo ---------------------
java java_cup.Main < .\CupFlexTest\AnalizadorSintactico.cup
move parser.java .\compilador\parser.java
move sym.java .\compilador\sym.java
echo Cup creado
echo *********************
pause

echo ---------------------
echo Generando Codigo Compilador
echo ---------------------
javac compilador\EscritorFichero.java compilador\SimboloSemantico.java compilador\AnalizadorMorfologico.java compilador\sym.java compilador\parser.java compilador\Main.java -d .\
echo Codigo compilado
echo *********************
pause