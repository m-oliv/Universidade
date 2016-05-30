%{
#include "lambda.tab.h" /* ficheiro produzido pelo bison */
%}

%%


[\t] ;

[0-9]+		{yylval = atoi(yytext); return INTEIRO;}
[a-z]		{yylval = yytext[0]; return VARIAVEL;}

"se"		{return IF;}
"entao"		{return THEN;}
"senao"		{return ELSE;}
"verdade"	{return TRUE;}
"falso"		{return FALSE;}
"em"		{return IN;}
"seja"		{return LET;}
"="		{return '=';}
"!" 		{return '!';}
"(" 		{return '(';}
")" 		{return ')';}
" "		{return ' ';}
"."		{return '.';}
"+"		{return '+';}
"-"		{return '-';}
"*"		{return '*';}
"/"		{return '/';}

"\n" 		return FIM_LINHA;

%%



/* chamada quando é encontrado fim-de-ficheiro */
int yywrap() {
  return 1;
}
