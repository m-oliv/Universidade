%{
#include "lambda.tab.h" /* ficheiro produzido pelo bison */
%}

%%


[\t] ;


[a-zA-Z]	{yylval = yytext[0]; return VARIAVEL;}

"!" 		{return '!';}
"(" 		{return '(';}
")" 		{return ')';}
"."		{return '.';}
" "		{return ' ';}

"\n" 		return FIM_LINHA;

%%



/* chamada quando é encontrado fim-de-ficheiro */
int yywrap() {
  return 1;
}
