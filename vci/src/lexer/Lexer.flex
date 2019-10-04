package lexer;
import static lexer.Token.*;

%%
%class Lexer
%type Token
%line
%unicode
%ignorecase

L=[a-zA-Z]+
D=[0-9]+
LU=[a-zA-Z]
DU=[0-9]
espacio=[ \t\r\n]+
CA="\""[^\"\n]*"\"" | "\"""\""
LorD = {LU}|{DU}



%{
    public int getLine() {
    return yyline;
    }
%}

%{
    public String lexeme;
%}
%%

PROG {lexeme=yytext(); return Prog;}
VAR {lexeme=yytext(); return Var;}
PROC {lexeme=yytext(); return Proc;}
INICIO {lexeme=yytext(); return Inicio;}
FIN {lexeme=yytext(); return Final;}
ENTERO {lexeme=yytext(); return Entero;}
REAL {lexeme=yytext(); return Real;}
STRING {lexeme=yytext(); return String;}
LIMPIAR {lexeme=yytext(); return Limpiar;}
VEXY {lexeme=yytext(); return Vexy;}
LEER {lexeme=yytext(); return Leer;}
ESCRIBIR {lexeme=yytext(); return Escribir;}
REPITE {lexeme=yytext(); return Repite;}
HASTA {lexeme=yytext(); return Hasta;}
MIENTRAS {lexeme=yytext(); return Mientras;}
SI {lexeme=yytext(); return Si;}
SINO {lexeme=yytext(); return SiNo;}
EJECUTA {lexeme=yytext(); return Ejecuta;}
AND {lexeme=yytext(); return And;}
OR {lexeme=yytext(); return Or;}
HOLA {lexeme=yytext(); return Hola;}
{espacio} {/*Ignore*/}
"//".* {/*Ignore*/}
"\\".* {lexeme=yytext(); return Comentario;}
" . " {/*Ignore*/}
". " {/*Ignore*/}
" ." {/*Ignore*/}
"+" {lexeme=yytext(); return Suma;}
"-" {lexeme=yytext(); return Resta;}
"*" {lexeme=yytext(); return Multiplicacion;}
"/" {lexeme=yytext(); return Division;}
"=" {lexeme=yytext(); return Igual;}
">=" {lexeme=yytext(); return MayorIgual;}
">" {lexeme=yytext(); return Mayor;}
"<=" {lexeme=yytext(); return MenorIgual;}
"<" {lexeme=yytext(); return Menor;}
"<>" {lexeme=yytext(); return Diferente;}
";" {lexeme=yytext(); return PuntoYComa;}
"]" {lexeme=yytext(); return CierraCorchete;}
"[" {lexeme=yytext(); return AbreCorchete;}
"," {lexeme=yytext(); return Coma;}
"." {/* Ignore */}
"\"" {/* Ignore */}
":" {lexeme=yytext(); return DosPuntos;}
")" {lexeme=yytext(); return CierraParentesis;}
"(" {lexeme=yytext(); return AbreParentesis;}
":=" {lexeme=yytext(); return DosPuntosIgual;}
"&&" {lexeme=yytext(); return LogicoAND;}
"||" {lexeme=yytext(); return LogicoOR;}
"!" {lexeme=yytext(); return Negacion;}

{LU}{LorD}{0, 5} {lexeme=yytext(); return Identificador;}
{D}+ {lexeme=yytext(); return Enteros;}
({D}+"."{D}+) {lexeme=yytext(); return Reales;}
{CA}+ {lexeme=yytext(); return Cadena;}
{D}{L}+({L}|{D}|".")* {lexeme=yytext(); return ERROR;}
{L}+"."+({L}|{D}|".")* {lexeme=yytext(); return ERROR;}
{D}+"."+({L}|{D}|".")* {lexeme=yytext(); return ERROR;}
{L}{D}"."+({L}|{D}|".")* {lexeme=yytext(); return ERROR;}
".""."+({L}|{D}|".")* {lexeme=yytext(); return ERROR;}
"."+({L}|{D}|".")+ {lexeme=yytext(); return ERROR;}
"\""[^\"\n]* {lexeme=yytext(); return ERROR;}
[^\"\n" "]+"\"" {lexeme=yytext(); return ERROR;}
"+""+"+ {lexeme=yytext(); return ERROR;}
"-""-"+ {lexeme=yytext(); return ERROR;}
"/""/"+ {lexeme=yytext(); return ERROR;}
"*""*"+ {lexeme=yytext(); return ERROR;}
"|" {lexeme=yytext(); return ERROR;}
"&" {lexeme=yytext(); return ERROR;}
"|" {lexeme=yytext(); return ERROR;}
{LU}({LU}|{DU})({LU}|{DU})({LU}|{DU})({LU}|{DU})({LU}|{DU})[^\"\n" "]* {lexeme=yytext(); return ERROR;}
"\""([^\"\n]|"\\""\"")*"\"" {lexeme=yytext(); return Cadena;}
 . {lexeme=yytext(); return ERROR;}