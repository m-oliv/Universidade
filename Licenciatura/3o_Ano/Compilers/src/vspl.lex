
%{	
	#include <stdio.h>
	#include <stdlib.h>
    	#include "vspl.tab.h"
%}

ID			[a-zA-Z_][a-zA-Z0-9_]*
REAL 			[0-9]*"."[0-9]+
INT	       	 	[0-9]+
WTS			[ \t\n]+ 
COM			"#".*\n 

%%

"cond"			return COND;
"and"			return AND;
"or"			return OR;
"not"			return NOT;
"return"		return RETURN;
"while"			return WHILE;
"else"			return ELSE;
"int"			return INT;
"real"			return REAL;
"bool"			return BOOL;
"void"			return VOID;
"map"			return MAP;
"break"			return BREAK;
"skip"			return SKIP;
"retry"			return RETRY;
"*"/{WTS}"->"		return ELSE;
"*"/{WTS}"["		return WHILE;
"true"			{yylval.boolean = 1; return BOOL_LIT;}
"false"			{yylval.boolean = 0; return BOOL_LIT;}
"+" 			return '+';
"-"			return '-';
"*"			return '*';
"/"			return '/';
"%"			return '%';
"&"			return AND;
"|"			return OR;
"~"			return NOT;
"^"			return RETURN;
"?"			return COND;
"="			return '=';
":" 			return ':';
";"			return ';';
","			return ',';
"<"			return '<';
"@"			return '@';
"<="			return LESSEQ;
"<>"			return DIF;
">="			return MOREEQ;
"->"			return APT;
":="			return AFECT;
">"			return '>';
"("			return '(';
")"			return ')';
"["			return '[';
"]"			return ']';
"{"			return '{';
"}"			return '}';

{ID} 			{yylval.identif = strdup(yytext); return ID;}

{INT}			{yylval.inteiro = atof(yytext); return INT_LIT;}

{REAL}			{yylval.real = atof(yytext); return REAL_LIT;}

{WTS}			{ }
{COM}			{ }

%%
/* chamada quando é encontrado fim-de-ficheiro */
int yywrap() {
  return 1;
}
