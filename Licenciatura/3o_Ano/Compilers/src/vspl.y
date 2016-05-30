%{

/*Includes necessarios */
#include <stdio.h>
#include <stdlib.h>
void yyerror(char *); /* ver abaixo */
extern int yylex(); /* o analisador lexical do flex */
extern FILE *yyin;
%}

/* union para tipos */
/* union -> especifica uma collection de tipos de dados para valores semanticos*/
%union{
	int inteiro, boolean;
	char *identif;
	double real;
}

/*Tokens*/
%token <identif> ID 
%token OP AND OR NOT
%token <inteiro> INT_LIT 
%token <real> REAL_LIT 
%token <boolean> BOOL_LIT 
%token INT REAL BOOL VOID
%token RETURN BREAK SKIP RETRY MAP
%token COND WHILE ELSE
%token LESSEQ MOREEQ DIF APT AFECT

%left '+' '-' '*' '/' AFECT APT AND NOT '.' '%' OR ','

%nonassoc '(' '[' ']' ')'
%nonassoc '<' '>' LESSEQ MOREEQ DIF '='

%start program /* Simbolo inicial */

%%

program: decls { printf("[A]-[prog(A)].\n"); };

decls: /*Vazio*/
	{ printf("X-[[]|X].\n"); }
	| decls decl 
	;

decl: ids ':' type ';' { printf("[[A],[B]|X]-[decl(B,A)|X].\n");}
	| id ':' type AFECT sexp ';' { printf("[[A],[B],[C]|X]-[decl(C,B,A)|X].\n");}
	| id AFECT sexp ';' { printf("[[A],[B]|X]-[decl(B,A)|X].\n");}
	| id ':' type '=' sexp ';' { printf("[[A],[B],[C]|X]-[assign(C,B,A)|X].\n");}
	| id '=' sexp ';' { printf("[[A],[B]|X]-[assign(B,A)|X].\n");}
	;

formals: /*Vazio*/
	{ printf("X-[[]|X].\n"); }
	| formal_decls
	;

formal_decls: formal_decl
	| formal_decl ';' formal_decls
	;

formal_decl: ids
	| ids ':' type { printf("[[A],[B]|X]-[decl(B,A)|X].\n"); }
	;

ids: id 		
	| id ',' ids { printf("[A|X]-[[nome('%s'),A]|X].\n","ovos" ); }
	;

id: ID { printf("X-[[nome('%s')]|X].\n", (char *) $1); };

type:
	single_type { printf("[A|X]-[[A]|X].\n"); }
	| '(' types ')'  { printf("[A|X]-[[A]|X].\n"); }
	;

types: /*Vazio*/
	| type ',' type_list 
	;

type_list:
	type
	| type ',' type_list { printf("[A,B|X]-[([B,A])|X].\n"); }
	;

single_type:
	 ID { printf("X-[[nome('%s')]|X].\n", (char *) $1); } 	
	| INT { printf("X-['int'|X].\n"); }
	| REAL { printf("X-['real'|X].\n"); }
	| BOOL { printf("X-['bool'|X].\n"); }
	| VOID { printf("X-['void'|X].\n"); }
	| type APT type { printf("[A,B|X]-[[B,A]|X].\n"); }
	| '[' exp ']' type { printf("[A,B|X]-[[B,A]|X].\n"); }
	| '{' formals '}' { printf("[A|X]-[[A]|X].\n"); }
	;

exp: 
	sexp { printf("[A|X]-[[A]|X].\n"); }
	| sexp ',' exp { printf("[A,B|X]-[[B,A]|X].\n"); }
	;
sexp:
	sexp OR sexp /* ops booleanos */ { printf("[A,B|X]-[op(or,A,B)|X].\n");  }
	| sexp AND sexp { printf("[A,B|X]-[op(and,A,B)|X].\n"); }
	| NOT sexp { printf("[A|X]-[op(neg,A)|X].\n");    }

	| sexp '<' sexp { printf("[A,B|X]-[op(menor,B,A)|X].\n");    }
	| sexp LESSEQ sexp { printf("[A,B|X]-[op(menorig,B,A)|X].\n");    }
	| sexp '=' sexp { printf("[A,B|X]-[op(igual,B,A)|X].\n");    }
	| sexp DIF sexp { printf("[A,B|X]-[op(dif,B,A)|X].\n");    }
	| sexp MOREEQ sexp { printf("[A,B|X]-[op(maiorig,B,A)|X].\n");    }
	| sexp '>' sexp { printf("[A,B|X]-[op(maior,B,A)|X].\n");    }

	| sexp '+' sexp { printf("[A,B|X]-[op(mai,B,A)|X].\n");  }
	| sexp '-' sexp { printf("[A,B|X]-[op(men,B,A)|X].\n");  }
	| sexp '*' sexp { printf("[A,B|X]-[op(vez,B,A)|X].\n");  }
	| sexp '/' sexp { printf("[A,B|X]-[op(div,B,A)|X].\n");  }
	| sexp '%' sexp { printf("[A,B|X]-[op(mod,B,A)|X].\n");  }
	| '-' sexp { printf("[A|X]-[op(neg,A)|X].\n");       }

	| sexp '.' ID /*nomes qualificados*/
	| sexp '[' exp ']'/*refs a arrays*/ { printf("[A,B|X]-[array(B,ind(A))|X].\n"); }
	| id '(' exp ')' /*aplicação funcional*/
			{ printf("[A|X]-[func(A)|X].\n"); }
	| '@' '(' exp ')' /*Aplicação recursiva directa*/ 
			{ printf("[A|X]-[func(rec,A)|X].\n"); }
	| id '(' ')' { printf("[void|X]-[func(void)|X].\n"); }
	| '@' '(' ')'{ printf("[void|X]-[func(rec,void)|X].\n"); }

	| ID /*nome simples*/	{ printf("X-[nome('%s')|X].\n",$1);     }
	| INT_LIT	/*constante inteira*/ { printf("X-[[literal(int,%d)]|X].\n",$1);  }
	| REAL_LIT	/*constante em virgula flutuante*/ { printf("X-[[literal(real,%f)]|X].\n",$1);  }
	| BOOL_LIT	/*constante booleana*/ { char* answer; if($1) answer="true"; else answer="false";printf("X-[[literal(bool,%s)]|X].\n",answer); }

	| '[' exp ']'
	| '(' exp ')'

	| MAP '(' formals ')' '[' stats ']' { printf("[A,B|X]-[func(args(B),tipo(_),corp(A))|X].\n"); }
	| MAP '(' formals ')' APT type '[' stats ']' 	{ printf("[A,B,C|X]-[func(args(C),tipo(B),corp(A))|X].\n"); }
	;

primary: prim
	| '(' primary '.' primaries ')'
	;

primaries: primary
	| primary ',' primaries
	;

prim:
	ID { printf("X-[nome('%s')|X].\n",$1); }
	| prim '.' ID
	| prim '[' sexp ']'
	;

stats: /*vazio*/ { printf("X-[[]|X].\n"); }
	| stat stats { printf("[A,B|X]-[[B,A]|X].\n"); }
	;

stat: decl
	| primary AFECT exp ';' { printf("[[A],B|X]-[decl(B,_,A)|X].\n"); }
	| primary '(' exp ')' ';' { printf("[A,B|X]-[func(B,A)|X].\n"); }
	| RETURN exp { printf("[[A]|X]-[ret(A)|X].\n"); }
	| '^' exp { printf("[[A]|X]-[ret(A)|X].\n"); }
	| BREAK ';' { printf("X-[break|X].\n"); }
	| SKIP ';' { printf("X-[skip|X].\n"); }
	| RETRY ';' { printf("X-[retry|X].\n"); }
	| COND '[' clauses ']' { printf("[A|X]-[cond(A)|X].\n"); }
	| WHILE '[' clauses ']' { printf("[A|X]-[while(A)|X].\n"); }
	| '*' '[' clauses ']' { printf("[A|X]-[while(A)|X].\n"); }
	| '[' stats ']' { printf("[A|X]-[bloco(A)|X].\n"); }
	;

clauses: exp APT stats { printf("[A,[B]|X]-[[clause(B,A)]|X].\n"); }
	| exp APT stats '|' clauses { printf("[A,[B],[C]|X]-[[clause(C,B),A)|X]].\n"); }
	| exp APT stats '|' ELSE APT stats { printf("[[A],[B],[C]|X]-[[(clause(C,B),clause(A))]|X].\n"); }
	;

%%
int main(int argc, char **argv){
	yyin = fopen(argv[1],"r"); // le o ficheiro a compilar
	
  	yyparse();
	fclose(yyin);
	exit(0);
}

/* função usada pelo bison para dar mensagens de erro */
void yyerror(char *msg)
{
  fprintf(stderr, "erro: %s\n", msg);
}
