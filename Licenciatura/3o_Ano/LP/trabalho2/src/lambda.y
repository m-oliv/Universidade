%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char tempg[100]; // String temporária para guardar informação
char tempg2[2]; // String temporária para guardar variáveis
char tempint[100];
void print_str(char *);
void callbyname(char *);
char *tira_1esp(char *, char *);
int calc_str_size(char *);	
int conta_espacos(char *);
int conta_prt(char *);
int conta_lambda(char *);
int ultm_esp(char *);
int fim_termo(char *);
char *variaveis(char *, char *);
int strcountc(char *, char);
int check_var(char *, char *);
int fim_abst(char *);
int abstr(char *);
char *abstracao(char *, char *);
char *ult_termo(char *, char *);
int c_redux(char *);
char *aplica(char *, char *, char *);
int check_opr(char *);
int is_operation(char *);
int find_op(char *);
int faz_conta(char *);
char *let(char *, char *);
char* aritm(char *, char *);
int strcountd(char *);
int avalia_se(char *);
char* se(char *, char *);
char *redux(char *, char *);
int main(int, char **);
void yyerror(char *);
extern int yylex(); /* o analisador lexical do flex */

%}

%token IF
%token THEN
%token ELSE
%token TRUE
%token FALSE
%token LET
%token IN
%token INTEIRO
%token VARIAVEL
%token FIM_LINHA

%left ' '

%start linha	// Simbolo inicial.


%%

linha: termo {
		callbyname(tempg); // reduz o termo	
		//print_str(temp);	
		} FIM_LINHA 
     ;

termo: VARIAVEL	{
		tempg2[0] = $1; // Coloca a variável numa String temporária
		tempg2[1] = '\0';
		strcat(tempg,tempg2); // Concatena com a String temp.
		}
	| int
	| '=' {strcat(tempg,"=");} // Concatena o operador = com a String temporária
	| bool
	| const_let
	| const_se
	| abstraccao
	| aritm
	| aplicacao
	| termo_par
	;

aplicacao: termo ' '{
		strcat(tempg," "); // Concatena o espaço com a String temp.
		} termo
	;

abstraccao:
	'!'VARIAVEL '.' 
		{
		strcat(tempg,"!"); // Concatena a String temp com !
		tempg2[0] = $2; // Coloca a variável numa String temporária
		tempg2[1] = '\0';
		strcat(tempg,tempg2);
		strcat(tempg,".");// Concatena com a String temp.
		} 
		termo
	;

const_let: LET ' ' VARIAVEL ' ' '=' ' '{ 
		strcat(tempg,"SEJA");
		strcat(tempg," ");
		tempg2[0] = $3; // Coloca a variável numa String temporária
		tempg2[1] = '\0';
		strcat(tempg,tempg2); // Concatena com a String temp.
		strcat(tempg," ");
		strcat(tempg,"=");
		strcat(tempg," ");
		} termo IN ' ' {
			strcat(tempg," "); 
			strcat(tempg,"EM");
			strcat(tempg," ");
		} termo
		;

int: INTEIRO	{ 
		int x = $1;
		sprintf(tempint,"%d",x);
		strcat(tempg,tempint);
		}
	;

aritm:    '+' {strcat(tempg,"+");}
	| '-' {strcat(tempg,"-");}
	| '*' {strcat(tempg,"*");}
	| '/' {strcat(tempg,"/");}
	;

bool: 	TRUE {strcat(tempg,"verdade");}
	| FALSE {strcat(tempg,"falso");}
	;

const_se: IF ' '{
		strcat(tempg,"SE");
		strcat(tempg," ");
		} termo ' ' THEN ' ' {	
		strcat(tempg," ");
		strcat(tempg,"ENTAO");
		strcat(tempg," ");
		} termo ELSE ' ' {
		strcat(tempg," ");
		strcat(tempg,"SENAO");
		strcat(tempg," ");
		} termo
	;

termo_par: 	'(' {strcat(tempg,"(");} termo ')' {strcat(tempg,")");} ;

%%


char final[200] = {'\0'};
char final2[200] = {'\0'};
char final3[200] = {'\0'};
char final4[200] = {'\0'};
char temp2[100];
char temp3[100];

void callbyname(char *c){
	char * reduzida = malloc(sizeof(char)*200);
	printf("<- %s\n",c);
	redux(c,reduzida);
	printf("-> %s\n",reduzida);
}

char *tira_1esp(char *c, char *d) // Auxiliar que caso a função comece com 1 espaço, retira-o
{
    int i = 0,j = 0;
    char aux[200] = {'\0'};
    if(c[0] == ' ')
    {
        i++;
    }
    while(c[i] != '\0')
    {
        aux[j] = c[i];
        j++;
        i++;
    }
    j++;
    aux[j] = '\0';
    strcat(d,aux);
    return d;
}

int calc_str_size(char *c) // Auxiliar que calcula o tamanho da string.
{ 
	int count = 0;
	int i = 0;

	while(c[i] != '\0'){
		i++;
		count++;
	}
	return count;
}
	
int conta_espacos(char *c)   // Auxiliar para contar espaços
{ 
	int count = 0, i= 0;
	
	while(c[i] != '\0')
	{
		if(c[i] == ' ')
		{
			count++;
		}
	i++;
	}
	return count;
}

int conta_prt(char *c)   //Auxiliar para contar o numero de parentesis
{ 
	int count = 0, i= 0;
	
	while(c[i] != '\0')
	{
		if((c[i] == '(') || c[i] == ')')
		{
			count++;
		}
	i++;
	}
	return count;
}

int conta_lambda(char *c)  // Auxiliar que conta o número de lambdas na string.
{
	int count = 0;
	int i = 0;

	while(c[i] != '\0'){
		if(c[i] == '!'){
			count++;
		}
	i++;
	}
	return count;
}

int ultm_esp(char *c) // Auxiliar para descobrir onde começa o termo a aplicar 
{
    int i = 0;
    int j = 0;
    int prt_aux = 0;
    int aux = conta_lambda(c);
    int m = 0;
    
    j = fim_abst(c);
    return j;
}

int fim_termo(char *c)
{
    int i = 0;
    int j = 0;
    int prt_aux = 0;
    int aux = conta_lambda(c);
    int m = 0;
    
    j = ultm_esp(c);
    j++;
    if(c[j] == '(')
    {
        prt_aux++;
        j++;
        while(prt_aux != 0) // até chegar ao fim dos ()
        {
            if(c[j] == ')')
            {
                prt_aux--;
            }
            if(c[j] == '(')
            {
                prt_aux++;
            }
            j++;
        }
        return j;
    }
    else 
    {
        while(c[j] != ' ' && c[j] != '\0')
        {
            j++;
        }
        return j;
    }  
}

char *variaveis(char *c, char *dest) // Auxiliar que vai armazenar as variaveis
{
    int i = 0, j = 0;
    char aux[200] = {'\0'};
    while(c[i] != '\0')
    {
        if(c[i] == '!')
        {
            i++;
            aux[j] = c[i];
            j++;
        }
        i++;
        
    }
    j++;
    aux[j] = '\0';
    strcat(dest,aux);
    return dest;
}

int strcountc(char *s, char ch) // Auxiliar que indica quantas vezes o caracter ch ocorre na string s
{
    int i, conta;
    for( i = conta = 0; s[i]!='\0'; i++)
    {
        if(s[i] == ch) conta++;
    }
    return conta;
}

int check_var(char *c, char *v) // Auxiliar que verifica se a string c tem os caracteres da string v          = 1 significa c não tem char da string s, = 0 significa que tem char da string s
{
    int i = 0,j,k = 0;
    j = calc_str_size(v);
    while(j != k)
    {
        if(strcountc(c,v[k]) >= 1)
        {
            return 0;
        }
        k++;
    }
    return 1;
}

int fim_abst(char *c) // Auxiliar que indica a posição onde acaba a abstração
{
    int i = 0;
    int k= 0;
    int prt = 0;
    int j = abstr(c);
    int l = conta_lambda(c);
    int c_prt = conta_prt(c);
    
    while(i != l)
    {
        if(c[k] == '!')
        {
            i++;
        }
        k++;
    }
    k--;
    j = k-1;
    
    if(c[j] == '(')
    {
        j++;
        prt++;
        while(prt != 0)
        {
            if(c[j] == ')')
            {
                prt--;
            }
            if(c[j] == '(')
            {
                prt++;
            }
            j++;
        }
        if(c[j] == '\0')
        {
            if(c[k-1] == '(')
            {
                k = k-2;
                return k; 
            }
            if(c[k-1] != '(')
            {
                k--;
                return k;
            }
        }
        return j;
    }
    else 
    {
        if(c_prt == 0)
        {
            while(c[j] != ' ')
            {
                j++;
            }
            return j;
        }
        else
        {
            while(c[j] != ')')
            {
                j++;
            }
            j++;
            if(c[j] == '\0')
            {
                if(c[k-1] == '(')
                {
                    k = k-2;
                    return k; 
                }
                if(c[k-1] != '(')
                {
                    k--;
                    return k;
                }
            }
            return j;
        }
    }
}

int abstr(char *c) // Auxiliar que indica onde começa a lambda-abstração
{
    int i = 0;
    int k = conta_prt(c);
    while(c[i] != '!')
    {
        i++;
    }
    if(k == 0 )
    {
        return i;
    }
    if(c[i-1] == '(' && c[i+3] != '(' && c[i+4] != '!')
    {
        i--;
        return i;
    }
    if(c[i+3] == '(' && c[i+4] == '!')
    {
        while(c[i+3] == '(' && c[i+4] == '!')
        {
            i = i+3;
        }
    }
    return i;
}

char *abstracao(char *c, char *dest) // Auxiliar que devolve a abstração
{
    int i = 0;
    int j = 0;
    int k = 0;
    k= calc_str_size(c);
    char final[k];
    
    for(i =  abstr(c); i < fim_abst(c); i++)
    {
        final[j] =  c[i];
        j++;
    }
    final[j] ='\0';
    strcat(dest,final);
    return dest;
}

char *ult_termo(char *c, char *dest) // Auxiliar que devolve o ultimo termo da expressão
{
    int i = 0;
    int j = 0;
    int k = calc_str_size(c);
    char h[k];
    int prt_aux = 0;
    int aux = conta_lambda(c);
    int m = 0;
    
    for(i = ultm_esp(c)+1; i < fim_termo(c); i++)
    {
        h[j] = c[i];
        j++;
    }
    h[j] = '\0';
    strcat(dest,h);
    return dest;
    
}

int c_redux(char *c) // Auxiliar que indica se é possivel reduzir a expressão 0 = Não, 1 = Sim
{
    int i = conta_espacos(c);
    int k = conta_prt(c);
    int l = conta_lambda(c);
    int esp = ultm_esp(c);
    char aux1[200] ={'\0'};
    char aux2[200] ={'\0'};
    ult_termo(c,aux1);
    variaveis(c,aux2);
    int var = check_var(aux1,aux2);
    
    
    if(strcountc(c,'!') == 0)
    {
        return 0;
    }
    if(var = 0)
    {
        return 0;
    }
    if(l == 1 && i == 0)
    {
        return 0;
    }
    if(c[0] == '!' && i != 0)
    {
        if(c[1] == c[esp+1] && i == 1 && k == 0)
        {
            return 0;
        }
        if(c[0] == c[3] && l >= 2 && i == 1)
        {
            return 0;
        }
        else return 1;
    }
    if(c[0] == '(' && c[1] == '!')
    {
        return 1;
    }
    return 1;
}

char *aplica(char *abst, char *subs, char *dest) // Auxiliar que retorna o resultado de fazer a aplicação do ultimo termo na abstração
{
    int i = 0, j= 0, k = 0;
    char termo;
    char final[200];
    int prt = 0;
    
    
    while(abst[i] != '!')
    {
        i++;
    }
    i++;
    termo = abst[i];
    i=i+2;
    
    while(abst[i] != '\0')
    {
        if(abst[i] == '(')
        {
            prt++;
        }
        if(abst[i] == ')')
        {
            prt--;
        }
        if(prt == -1)
        {
            i++;
            prt = 0;
        }
        if(abst[i] == termo)
        {
            while(subs[k] != '\0')
            {
                final[j] = subs[k];
                j++;
                k++;
            }
            j--;
        }
        else
        {
            
                final[j] = abst[i];
        }
        i++;
        j++;
    }
    final[j] = '\0';
    strcat(dest, final);
    return dest;
}

int check_opr(char *c){ // auxiliar para verificar se existem operadores.
	int i = 0;
	int op = 0;
	int check = 0;
	
	while(c[i] !='\0'){
		if(c[i] == '+' || c[i] == '-' || c[i] == '*' || c[i] == '/'){
			op++;
		}
		i++;
	}

	if(op!= 0){
		check = 1;
	}
	else{
		check = 0;
	}

	return check;
}

int is_operation(char *c){

	if(c[0]=='+'||c[0]=='-'||c[0] == '*'|| c[0]=='/'){
		return 1;
	}

	if(c[0] == '(' && (c[1]=='+'||c[1]=='-'||c[1] == '*'|| c[1]=='/')){
		return 1;
	}

	return 0;

}

int find_op(char *c){ // procura a posição da operação mais interior
	int i = 0;
	int pos = 0;
	int nop = check_opr(c);
	
	if(nop == 1){
		while(c[i]!='\0'){
			if(c[i]=='+'||c[i]=='-'||c[i] == '*'|| c[i]=='/'){
				pos = i;
			}
			i++;
		}
	}
	
	if(nop!=1){
		
		while(c[i]!='\0'){
				if((c[i]=='+'||c[i]=='-'||c[i] == '*'|| c[i]=='/') && c[i-1] == '('){
					nop--;
						
					if(nop==0){
							pos = i;
					}
						
				}
				i++;
		}
	}
	
	return pos;
}

int faz_conta(char *c){ // faz a conta
	int resultado = 0;
	int i = 0,k;
	char arg1[10] ={'\0'};
	char arg2[10] ={'\0'};
	int num1;
	int num2;
	
		if(c[0] == '+'){ // soma
			
				while(c[i] != ' '){
				i++;
				}
				
				i++;
				k = 0;
				
				while(c[i] != ' '){
					
					arg1[k] = c[i];
					k++;
					i++;
				}
				arg1[k] = '\0';
			
				num1 = atoi(arg1);
				
			
				k = 0;
			
				while(c[i] !='\0'){
				
					if(c[i] == ' '){
						i++;
					}
					
					arg2[k] = c[i];
					k++;
					i++;
				}
				
				arg2[k] = '\0';
			
				num2 = atoi(arg2);
			
				resultado = num1 +num2;
		}
		
		if(c[0] == '-'){ // subtraccao
					
						while(c[i] != ' '){
						i++;
						}
						
						i++;
						k = 0;
						
						while(c[i] != ' '){
							
							arg1[k] = c[i];
							k++;
							i++;
						}
						arg1[k] = '\0';
					
						num1 = atoi(arg1);
						
					
						k = 0;
					
						while(c[i] !='\0'){
						
							if(c[i] == ' '){
								i++;
							}
							
							arg2[k] = c[i];
							k++;
							i++;
						}
						
						arg2[k] = '\0';
					
						num2 = atoi(arg2);
					
						resultado = num1 - num2;
				}
	
		if(c[0] == '*'){ // multiplicacao
					
						while(c[i] != ' '){
						i++;
						}
						
						i++;
						k = 0;
						
						while(c[i] != ' '){
							
							arg1[k] = c[i];
							k++;
							i++;
						}
						arg1[k] = '\0';
					
						num1 = atoi(arg1);
						
					
						k = 0;
					
						while(c[i] !='\0'){
						
							if(c[i] == ' '){
								i++;
							}
							
							arg2[k] = c[i];
							k++;
							i++;
						}
						
						arg2[k] = '\0';
					
						num2 = atoi(arg2);
					
						resultado = num1 * num2;
				}
		
		if(c[0] == '/'){ // divisao
					
						while(c[i] != ' '){
						i++;
						}
						
						i++;
						k = 0;
						
						while(c[i] != ' '){
							
							arg1[k] = c[i];
							k++;
							i++;
						}
						arg1[k] = '\0';
					
						num1 = atoi(arg1);
						
					
						k = 0;
					
						while(c[i] !='\0'){
						
							if(c[i] == ' '){
								i++;
							}
							
							arg2[k] = c[i];
							k++;
							i++;
						}
						
						arg2[k] = '\0';
					
						num2 = atoi(arg2);
					
						if(num2 !=0){
						
							resultado = num1 / num2;
						}
				}
		
	return resultado;
}

char *let(char *c, char *dest)
{
    int k;
    char f[200] = {'\0'};
    char t[200];
    char v;
    k = 0;
    int i = 0;
    
    while(c[i] != ' ')  //Chegar até seja
    {
        i++;
    }
    while(c[i] != '=') //Chegar até ao argumento
    {
        if(c[i] != ' ')
        {
            v = c[i];  //Copiar o argumento 
        }
        i++;
    }
    i= i+2;
    k = 0;
    while(c[i] != 'E' && c[i+1] != 'M') //Até encontrar a palavra "em"
    {
          t[k] = c[i];        // Copiar termo
          k++;
        i++;
    }
    t[k-1] = '\0';
    k = 0;
    while(t[k] != '\0')
    {
        k++;
    }
    k = 0;
    i = i+3;
    while(c[i] != '\0')
    {
        if(c[i] == v)
        {
            strcat(f,t);
        }
        else
        {
            char aux[2];
            aux[0] = c[i];
            aux[1] ='\0';
            strcat(f,aux);
        }        
           
    i++;
    }
    strcat(dest,f);
    return dest;
}

char* aritm(char *c, char *dest){ // trata a string e faz a conta
	int i = 0;
	int tem_op = check_opr(c);
	int pos_op = find_op(c);
	char temp[200] = {'\0'};
	char temp2[200] = {'\0'};
	char res[200] = {'\0'};
	char resultado[200] = {'\0'};
	
	if(is_operation(c) == 0){ // se nao tem operaçoes
			
			strcat(dest,c);
	}	

	if((c[0]=='+'||c[0]=='-'||c[0] == '*'|| c[0]=='/') && tem_op == 1 && conta_espacos(c) == 1){ // se so tem um operador e um numero
			
			strcat(dest,c);
	}
		
	if(c[0]== '(' &&(c[1]=='+'||c[1]=='-'||c[1] == '*'|| c[1]=='/') && tem_op == 1 && conta_espacos(c) == 1){ // se so tem um operador e um numero
					
			strcat(dest,c);
	}
	
	if(pos_op == 0 && conta_espacos(c)!=1 && conta_espacos(c)!=0){ // PARA K E K EU QUERIA ISTO???? -> op simples, maybe?
			
			int result = faz_conta(c);
			int j = sprintf(res,"%d",result);
			strcat(dest,res);
	}

	if(pos_op !=0 && conta_espacos(c)!=1){
			
			
			if(c[pos_op-1] != '(' ){ // para quando nao ha parentesis
				
				while(i<pos_op){ // copiar tudo até à posição da operação
					resultado[i] = c[i];
					i++;
				}
				
				int l = pos_op;
				int u = 0;
				while(c[l]!='\0'){ // copia operação para uma temporária
					temp[u] = c[l];
					l++;
					u++;
				}
				temp[l+1]='\0';
			
				// faz a conta
				int ret = faz_conta(temp); // resultado da conta
				
				int j = sprintf(res,"%d",ret); // Converte para string
				
				strcat(resultado,res); // concatena string antiga com o resultado.
				
				aritm(resultado,dest); // corre de novo a aritmetica para a expressao nova.
			}
			
			if(c[pos_op-1] == '('){ // para quando ha parêntesis
				
				while(i<pos_op-1){ // copiar tudo até à posição da operação
						resultado[i] = c[i];
						i++;
				}
				
				int l = pos_op;
				int u = 0;
				
				while(c[l]!=')'){ // copia operação para uma temporária
					temp[u] = c[l];
					l++;
					u++;
				}
				temp[l+1]='\0';
				
				int n = 0;
				int m = l;
				while(c[m] != '\0'){
					temp2[n] = c[m];
					m++;
					n++;
				}
				temp2[m+1]='\0';
				int ret = faz_conta(temp); // resultado da conta
								
				int j = sprintf(res,"%d",ret); // Converte para string
								
				strcat(resultado,res); // concatena string antiga com o resultado.
				strcat(resultado,temp2);
				aritm(resultado,dest); // corre de novo a aritmetica para a expressao nova.
				
			}
			
	}
		
		return dest; // retorna a string com o resultado
	
}

int strcountd(char *c)  // Auxiliar que retorna o numero de digitos que uma String têm
{
    int i, conta;
    for(i =conta =0; c[i] !='\0'; i++)
    {
        if(isdigit(c[i]))
        {
            conta++;
        }
    }
    return conta;
}

int avalia_se(char *c){
	char *x = c;
	char *true = "verdade";
	char *false = "falso";
	char t1bool, t2bool;
	int a,b;
	int i, j, k;
	char t1[100];
	char t2[100];

	if(c[0] == '='){

		x = x+2; // salta = esp

		i=0;
		while(x[i] != ' '){ // copia o argumento 1
			t1[i] = x[i];
			i++;
		}
		t1[i] = '\0';
		if(strcmp(t1,true) == 0){
			t1bool = 'v';
		}

		if(strcmp(t1,false) == 0){
			t1bool = 'f';
		}
		if(strcmp(t1,false) != 0 && strcmp(t1,true) != 0){

			a = atoi(t1);

		}

		x = x+strlen(t1)+1; // salta exp esp

		i=0;

		while(x[i] != '\0'){ // copia argumento 2
			t2[i] = x[i];
			i++;
		}
		t2[i] = '\0';

		if(strcmp(t2,true) == 0){
			t2bool = 'v';
		}

		if(strcmp(t2,false) == 0){
			t2bool = 'f';
		}
		if(strcmp(t2,true) != 0 && strcmp(t2,false) != 0){
			b = atoi(t2);

		}

		if(t1bool == 'v' && t2bool == 'v' || t1bool == 'f' && t2bool == 'f'){
			return 1;
		}

		if( a == b ){
			return 1;
		}

	}
	
	if(strcmp(x,true) == 0){
		return 1;
	}

	return 0;
}

char* se(char *c, char *dest){
	char *d = c;
	char t[100];
	char t2[100];
	int i = 0, j = 0, k = 0;

	if(d[0] != 'S' && d[0] != '='){
		dest = d;
		return dest;
	}

	if(c[0] == 'S'){

		d = d+3; // salta se esp

		while(d[i] != 'E'){
			t[i] = d[i];
			i++;
		}
		t[i-1] = '\0';

		int avalia = avalia_se(t);
		int size_t = strlen(t);

		d = d+size_t+1; // salta exp esp

		if(avalia == 1){ // se reduz para verdade
			d = d+6; // salta entao esp

			while(d[j] != 'S'){
				t2[j] = d[j];
				j++;
			}
			t2[j-1] = '\0';

			strcpy(temp3,t2);
		}

		if(avalia == 0){ // se reduz para falso
			d = d+6; // salta entao esp
			j = 0;
			int pos = 0;

			while(d[j] != 'S'){ // calcula a posiçao do senao
				pos++;
				j++;
			}
			d = d+pos; // salta a exp do entao
			d = d+6; // salta senao exp
			while(d[k] != '\0'){
				t2[k] = d[k];
				k++;
			}
			t2[k] = '\0';

			strcpy(temp3, t2);
		}
		}

		if(d[0] == '='){
			int l = avalia_se(d);
			if(l==1){
				dest = "verdade";
				return dest;
			}
			if(l==0){
				dest = "falso";
				return dest;
			}
		}

	se(temp3,dest);
}

char *redux(char *c, char *dest)
{
    int fim_c = 0;
    while(c[fim_c] != '\0')
    {
        fim_c++;
    }
    
    char redux1[200] = {'\0'};
    char *redux2;
    char redux3[200] = {'\0'};
    char aux[200] = {'\0'};
    
    if(calc_str_size(c) == 1)
    {
        strcat(dest,c);
        return dest;
    }
    if(strcountd(c) == calc_str_size(c))
    {
        strcat(dest,c);
        return dest;
    }
    if(strcmp(c,"verdade") == 0 || strcmp(c,"falso") == 0)
    {
        strcat(dest,c);
        return dest;
    }
    
    if(c[0] == '(')
    {
        if(c[1] == '+' || c[1] == '-' || c[1] == '*' || c[1] == '/')
        {
            if(c[5] == '\0')
            {
                strcat(dest,c);
                return dest;
            }
            aritm(c,redux1);
            redux(redux1,redux3);
            strcat(dest,redux3);
            return dest;
        }

        if(c[1] == 'S' && c[2] == 'E' && c[3] == 'J' && c[4] == 'A')
        {
            let(c,redux1);
            redux(redux1,redux3);
            redux(redux3,dest);
            return dest;
        }

        if(c[1] == 'S' && c[2] == 'E' )
        {
            int i = 1;
            int j = 0;
            while(c[i] != ')')
            {
                redux1[j] = c[i];
                j++;
                i++;
            }
            redux1[j] = '\0';
            strcat(aux,redux1);
            redux2 = se(aux,redux1);
            strcat(redux3,redux2);
            j = 0;
            i = i+1;
            while(c[i] !='\0')
            {
                redux1[j] = c[i];
                i++;
                j++;
            }
            redux1[j] = '\0';
            strcat(redux3,redux1);
            redux(redux3,dest);
            return dest;
        }
        if(c[1] == 'v' && c[2] == 'e' && c[3] == 'r' && c[4] == 'd')
        {
            redux2 = se(c,redux1);
        strcat(redux3,redux2);
        redux(redux3,dest);
        return dest;
        }
        if(c[1] == 'f' && c[2] == 'a' && c[3] == 'l' && c[4] == 's')    
        {
            redux2 = se(c,redux1);
            redux(redux2,dest);
            return dest;
        }
        if(c[1] == '=')
        {
            redux2 = se(c,redux1);
        strcat(redux3,redux2);
        redux(redux3,dest);
        return dest;
        }
    }
    if(c[0] == '+' || c[0] == '-' || c[0] == '*' || c[0] == '/')
    {
        if(c[3] == '\0')
        {
            strcat(dest,c);
            return dest;
        }
        aritm(c,redux1);
        redux(redux1,redux3);
        strcat(dest,redux3);
        return dest;
    }
    
    if(c[0] == 'S' && c[1] == 'E' && c[2] == 'J' && c[3] == 'A')
    {
        let(c,redux1);
        redux(redux1,redux3);
        redux(redux3,dest);
        return dest;
    }
    
    if(c[0] == 'S' && c[1] == 'E' )
    {
        redux2 = se(c,redux1);
        strcat(redux3,redux2);
        redux(redux3,dest);
        return dest;
    }
    if(c[0] == 'v' && c[1] == 'e' && c[2] == 'r' && c[3] == 'd')
    {
        redux2 = se(c,redux1);
        strcat(redux3,redux2);
        redux(redux3,dest);
        return dest;
    }
    if(c[0] == 'f' && c[1] == 'a' && c[2] == 'l' && c[3] == 's')    
    {
        redux2 = se(c,redux1);
        strcat(redux3,redux2);
        redux(redux3,dest);
        return dest;
    }
    if(c[0] == '=')
    {
        redux2 = se(c,redux1);
        strcat(redux3,redux2);
        redux(redux3,dest);
        return dest;
    }
    
    int i = 0;
    int j = 0;
    int k = 0;
    int l = 0;
    int term = 0;
    int tam = calc_str_size(c);
    int p1 = fim_abst(c);
    i = abstr(c);
    j = ultm_esp(c)+1;
    term = j-1;
    k = c_redux(c);
    int fim_t = fim_termo(c);
    
    char g[tam];
    char abst[200] ={'\0'};
    char termo[200] ={'\0'};
    char termo2[200] ={'\0'};
    char auxf[200] ={'\0'};
    int kp = conta_prt(c);
    
    if(k == 0) // Não é possivel reduzir a expressão
    {  
            if(c[i+1] == '!' && c[i+2] == c[i+4] && c[i+5] == ')' && i == j+1 && i != 0) // se o termo a aplicar for a identidade
            {
                l = 0;
                while(l < i)
                {
                    g[l] = c[l];
                    l++;
                }
                if(c[i+6] != '\0') // Se tiver alguma depois da identidade
                {
                    i = i+7;
                    while(c[i] != '\0')
                    {
                        g[l] = c[i];
                        l++;
                        i++;
                    }
                }
                g[l] = '\0';
                strcat(dest,g);
                return dest;
            }
            if(c[i] == '!' && c[i+1] == c[i+3] && i == j+1) // se o termo a aplicar for a identidade
            {
                l = 0;
                while(l < i)
                {
                    g[l] = c[l];
                    l++;
                }
                if(c[i+5] != '\0') // Se tiver alguma depois da identidade
                {
                    i = i+7;
                    while(c[i] != '\0')
                    {
                        g[l] = c[i];
                        l++;
                        i++;
                    }
                }
                g[l] = '\0';
                strcat(dest,g);
                return dest;
            }
            strcat(dest, c);
            return dest;
    }
    else
    {
            abstracao(c, abst);
            ult_termo(c, termo);
            tira_1esp(termo,termo2);
            aplica(abst,termo2,auxf);


            if(i == 0) // Se começo abstração for inicio expressão
            {
                
                if(p1 == term && fim_t == fim_c) // Final abstração = começo do termo a copiar e final termo = fim da expressão
                {
                    redux(auxf,dest);
                    return dest;
                }
                if(p1 == term && fim_t != fim_c)
                {
                        l = 0;
                        while(auxf[l] != '\0') // copia resultado aplicação
                        {
                            g[l] = auxf[l];
                            l++;
                        }
                        while(fim_t < fim_c) // adicionar o restante
                        {
                            g[l] = c[fim_t];
                            fim_t++;
                            l++;
                        }
                        g[l] = '\0';
                        redux(g,dest);
                        return dest;
                }
                if(p1 < term && fim_t == fim_c)
                {
                        l = 0;
                        while(auxf[l] != '\0') // copia resultado aplicação
                        {
                            g[l] = auxf[l];
                            l++;
                        }
                        while(p1 < term) // adicionar o restante
                        {
                            g[l] = c[p1];
                            p1++;
                            l++;
                        }
                        l--;
                        g[l] = '\0';
                        redux(g,dest);
                        return dest;
                }
                if(p1 < term && fim_t != fim_c)
                {
                        l = 0;
                        while(auxf[l] != '\0') // copia resultado aplicação
                        {
                            g[l] = auxf[l];
                            l++;
                        }
                        while(p1 < term) // adicionar o restante
                        {
                            g[l] = c[p1];
                            p1++;
                            l++;
                        }
                        while(fim_t < fim_c)
                        {
                            g[l] = c[fim_t];
                            l++;
                            fim_t++;
                        }
                        g[l] = '\0';
                        redux(g,dest);
                        return dest;
                }
            }
            else // Se abstração começar depois do inicio da expressão 
            {
                int temp = 0;
                while(temp != i)
                {
                    g[temp] = c[temp];
                    temp++;
                }
                g[temp] = '\0';
                if(strcmp(auxf,termo) == 0 && p1 < i) // Final abstração = começo do termo a copiar e final termo = fim da expressão
                {
                    redux(g,dest);
                    return dest;
                }
                strcat(g,auxf);
                int t = 0;
                while(g[t] != '\0')
                {
                    t++;
                }
                if(p1 == term && fim_t == fim_c) // Final abstração = começo do termo a copiar e final termo = fim da expressão
                {
                    redux(g,dest);
                    return dest;
                }
                if(p1 == term && fim_t != fim_c)
                {
                    
                        while(fim_t < fim_c) // adicionar o restante
                        {
                            g[temp] = c[fim_t];
                            temp++;
                            fim_t++;
                        }
                        g[temp] = '\0';
                        redux(g,dest);
                        return dest;
                }
                if(p1 < term && fim_t == fim_c)
                {
                        while(p1 < term) // adicionar o restante
                        {
                            g[temp] = c[p1];
                            p1++;
                            temp++;
                        }
                        l--;
                        g[l] = '\0';
                        redux(g,dest);
                        return dest;
                }
                if(p1 < term && fim_t != fim_c)
                {
                        while(p1 < term) // adicionar o restante
                        {
                            g[temp] = c[p1];
                            p1++;
                            temp++;
                        }
                        temp--;
                        while(fim_t < fim_c)
                        {
                            g[temp] = c[fim_t];
                            temp++;
                            fim_t++;
                        }
                        g[temp] = '\0';
                        redux(g,dest);
                        return dest;
                }
            }
    }
}

int main(int argc, char **argv)
{
  return yyparse();
}

/* função usada pelo bison para dar mensagens de erro */
void yyerror(char *msg)
{
  fprintf(stderr, "erro: %s\n", msg);
}
