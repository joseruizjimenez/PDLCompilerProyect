package pdlcompilerproyect;

import java_cup.runtime.*;

%%

%class AnalizadorMorfologico
%standalone
%8bit
%line
%column
%char
%state COMMENT
%full
%cup

%{
    private int comment_count = 0;

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

DIGITO=[0-9]
DECIMAL_NATURAL={DIGITO}+
DECIMAL_ENTERO=[\+\-]{DECIMAL_NATURAL}
DECIMAL_REAL=({DECIMAL_ENTERO}|{DECIMAL_NATURAL})?\.{DECIMAL_NATURAL}?
OCTAL=[0-7]+[oO]
HEXADECIMAL=[0-9abcdefABCDEF]+[hH]
BINARIO=[0-1]+[bB]
LETRA=[a-zA-Z]
ALFANUMERICO={LETRA}|{DIGITO}
IDENTIFICADOR=[a-z]({LETRA}|{ALFANUMERICO}|[_]){0,79}
NEWLINE=\r|\n|\r\n
WHITESPACE={NEWLINE}|[ \t\f\b\012]
COMMENT_LINE=(\/){2,2}([^\r\n])*
COMMENT_TEXT=([^*/\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*

%%

<YYINITIAL> {

"/*"            {Utility.info("[Omitido comentario multiple en linea: "+(yyline+1)+"]");
                    yybegin(COMMENT); comment_count++;}
{COMMENT_LINE}  {Utility.info("[Omitido comentario simple en linea: "+(yyline+1)+"]");}

"program"	{return symbol(sym.PROGRAMA);}
"const"         {return symbol(sym.CONSTANTE);}
"if"		{return symbol(sym.IF);}
"else"		{return symbol(sym.ELSE);}
"while"		{return symbol(sym.WHILE);}
"for"		{return symbol(sym.FOR);}
"of"            {return symbol(sym.OF);}

"int"		{return symbol(sym.INT);}
"boolean"	{return symbol(sym.BOOLEAN);}
"float"		{return symbol(sym.FLOAT);}

"true"		{return symbol(sym.CTE_BOOLEANA);}
"false"		{return symbol(sym.CTE_BOOLEANA);}

"scanf"		{return symbol(sym.SCANF);}
"cprintf"	{return symbol(sym.CPRINTF);}
"printf"	{return symbol(sym.PRINTF);}

"malloc"        {return symbol(sym.MALLOC);}
"free"		{return symbol(sym.FREE);}
"array"         {return symbol(sym.ARRAY);}
"function"	{return symbol(sym.FUNCTION);}
"return"	{return symbol(sym.RETURN);}
"size"		{return symbol(sym.SIZE);}
"contains"	{return symbol(sym.CONTAINS);}

";"		{return symbol(sym.SEMI);}
","		{return symbol(sym.COMA);}
":"             {return symbol(sym.PUNTOS);}

"+"		{return symbol(sym.MAS);}
"-"		{return symbol(sym.MENOS);}
"/"		{return symbol(sym.ENTRE);}
"*"		{return symbol(sym.ASTERISCO);}

"&&"		{return symbol(sym.AND);}
"||"		{return symbol(sym.OR);}

"{"		{return symbol(sym.LEFT_LLAVE);}
"}"		{return symbol(sym.RIGHT_LLAVE);}
"("		{return symbol(sym.LEFT_PAREN);}
")"		{return symbol(sym.RIGHT_PAREN);}
"["		{return symbol(sym.LEFT_CORCHETE);}
"]"		{return symbol(sym.RIGHT_CORCHETE);}

"=="		{return symbol(sym.EQUALS);}
"=&"		{return symbol(sym.ASIGNACION);}
"="		{return symbol(sym.ASIGNACION);}
"!="		{return symbol(sym.NOT_EQUALS);}
"!"		{return symbol(sym.NOT);}
">="		{return symbol(sym.GREATER_EQUALS);}
"<="		{return symbol(sym.LESS_EQUALS);}
"<"		{return symbol(sym.LESS);}
">"		{return symbol(sym.GREATER);}

{OCTAL}         {return symbol(sym.NUM_OCT, yytext());}
{HEXADECIMAL}   {return symbol(sym.NUM_HEX, yytext());}
{BINARIO}       {return symbol(sym.NUM_BIN, yytext());}
{DECIMAL_REAL}  {return symbol(sym.NUM_REAL, new Float(yytext()));}
{DECIMAL_ENTERO} {return symbol(sym.NUM_ENTERO, new Integer(yytext()));}
{DECIMAL_NATURAL} {return symbol(sym.NUM_ENTERO, new Integer(yytext()));}

{IDENTIFICADOR} {return symbol(sym.ID_VAR, yytext());}

{WHITESPACE}    {}

.              {Utility.error(Utility.E_SYMBOL_NOT_FOUND,yyline+1,yycolumn+1,yytext());
                    System.exit(0);}
}


<COMMENT> {
"/*"            {comment_count++;}
"*/"            {if (--comment_count == 0) yybegin(YYINITIAL);}
{COMMENT_TEXT}  {}
}