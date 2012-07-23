package compilador;
import java_cup.runtime.*;
%%

%class AnalizadorMorfologico
%unicode
%line
%column
%cup


%{   
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn,yytext());
    }
    
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}


letra = [a-zA-Z]
entero = [0-9]+
real = {entero}"."{entero}
realSinEntero = "."{entero}
realSinDecimal = {entero}"."
finDeLinea = \r|\n|\r\n
comentario = \/\/[^\n\r]*{finDeLinea}
idVariable = {letra}[a-zA-Z0-9]{0,29}
idVariableLarga = {letra}[a-zA-Z0-9]+
idVariableEntera = {entero}[a-zA-Z0-9]+

%%

"program"		{return symbol(sym.PROGRAM);}
"if"		{return symbol(sym.IF);}
"else"		{return symbol(sym.ELSE);}
"while"		{return symbol(sym.WHILE);}
"for"       {return symbol(sym.FOR);}

"int"		{return symbol(sym.ENTERO);}
"boolean"	{return symbol(sym.BOOLEAN);}
"float"		{return symbol(sym.FLOAT);}

"true"		{return symbol(sym.CTE_BOOLEANA);}
"false"		{return symbol(sym.CTE_BOOLEANA);}

"scanf"		{return symbol(sym.SCANF);}
"cprintf"	{return symbol(sym.CPRINTF);}
"printf"	{return symbol(sym.PRINTF);}

"array"	{return symbol(sym.ARRAY);}
"function"	{return symbol(sym.FUNCTION);}
"return"	{return symbol(sym.RETURN);}

";"		{return symbol(sym.SEMI);}
","		{return symbol(sym.COMA);}
":"             {return symbol(sym.PUNTOS);}

"+"		{return symbol(sym.MAS);}
"-"		{return symbol(sym.MENOS);}
"/"		{return symbol(sym.ENTRE);}
"*"		{return symbol(sym.POR);}

"&&"		{return symbol(sym.AND);}
"||"		{return symbol(sym.OR);}

"!"		{return symbol(sym.NOT);}

"{"		{return symbol(sym.LEFT_LLAVE);}
"}"		{return symbol(sym.RIGHT_LLAVE);}
"("		{return symbol(sym.LEFT_PARENTESIS);}
")"		{return symbol(sym.RIGHT_PARENTESIS);}
"["		{return symbol(sym.LEFT_CORCHETE);}
"]"		{return symbol(sym.RIGHT_CORCHETE);}

"="		{return symbol(sym.ASIGNACION);}
"=="		{return symbol(sym.EQUALS);}
"!="		{return symbol(sym.NOT_EQUALS);}
">="		{return symbol(sym.GREATER_EQUALS);}
"<="		{return symbol(sym.LESS_EQUALS);}
"<"		{return symbol(sym.LESS);}
">"		{return symbol(sym.GREATER);}


{comentario}		{}
" "			{}
{finDeLinea}		{}
"\t"			{}

{entero}		{return symbol(sym.INT, new Integer(yytext()));}

{real}			{return symbol(sym.REAL, new Float(yytext()));}

{realSinEntero}		{return symbol(sym.REAL_SIN_INT, new Float(yytext()));}

{realSinDecimal}	{return symbol(sym.REAL_SIN_DEC, new Float(yytext()));}

{idVariable}		{return symbol(sym.ID_VAR);}

.			{System.out.print(" Error (fila "+(yyline+1)+", columna "+(yycolumn+1)+"): simbolo "+yytext()+" no valido.");
				System.exit(0);}
