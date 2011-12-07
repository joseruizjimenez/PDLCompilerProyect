package practicapdl;

%%

%{

%}

%line
%column
%char
%full


DECIMAL_NATURAL=[0-9]+
DECIMAL_ENTERO=[\+\-]{DECIMAL_NATURAL}
DECIMAL_REAL=({DECIMAL_ENTERO}|{DECIMAL_NATURAL})?\.{DECIMAL_NATURAL}?
OCTAL=[0-7]+[oO]
HEXADECIMAL=[0-9abcdefABCDEF]+[hH]
BINARIO=[0-1]+[bB]
NEWLINE=\r|\n|\r\n

%%

{OCTAL} { System.out.print("OCTAL: "); System.out.print(yytext() + " >> "); 
        System.out.println(Utility.octalToBinary(yytext(),yyline+1,yycolumn+1)); }

{HEXADECIMAL} { System.out.print("HEXADECIMAL: "); System.out.print(yytext() + " >> "); 
        System.out.println(Utility.hexaToBinary(yytext(),yyline+1,yycolumn+1)); }

{BINARIO} { System.out.print("BINARIO: "); System.out.print(yytext() + " >> "); 
        System.out.println(Utility.binaryToBinary(yytext(),yyline+1,yycolumn+1)); }

{DECIMAL_REAL} { System.out.print("DECIMAL REAL: "); System.out.print(yytext() + " >> "); 
        System.out.println(Utility.decimalToBinary(yytext(),yyline+1,yycolumn+1)); }

{DECIMAL_ENTERO} { System.out.print("DECIMAL ENTERO: "); System.out.print(yytext() + " >> "); 
        System.out.println(Utility.decimalToBinary(yytext(),yyline+1,yycolumn+1)); }

{DECIMAL_NATURAL} { System.out.print("DECIMAL NATURAL: "); System.out.print(yytext() + " >> "); 
        System.out.println(Utility.decimalToBinary(yytext(),yyline+1,yycolumn+1)); }

.+ { System.out.print("NUM NO VALIDO: " + yytext() + " >> ");
	Utility.error(Utility.E_ILLEGAL_ENDING_CHAR,yyline+1,yycolumn+1,yytext());
        System.out.println(); }

{NEWLINE} { }