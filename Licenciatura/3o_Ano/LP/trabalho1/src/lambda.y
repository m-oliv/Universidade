%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
char temp[100]; // String temporária para guardar informação
char temp2[2]; // String temporária para guardar variáveis
void yyerror(char *); /* ver abaixo */
void callbyname(char *); // Função que reduz os termos fornecidos pelo utilizador (utiliza a estratégia call-by-name).
extern int yylex(); /* o analisador lexical do flex */

%}

%token VARIAVEL
%token FIM_LINHA

%start linha	// Simbolo inicial.


%%

linha: termo {
		callbyname(temp); // reduz o termo		
		} FIM_LINHA 
     ;

termo: VARIAVEL {
		temp2[0] = $1; // Coloca a variável numa String temporária
		temp2[1] = '\0';
		strcat(temp,temp2); // Concatena com a String temp.
		}
	| abstraccao
	| termo_par
	| aplicacao
	;

aplicacao: termo ' '{
		strcat(temp," "); // Concatena o espaço com a String temp.
		} termo
	;

abstraccao:
	'!'VARIAVEL '.' 
		{
		strcat(temp,"!"); // Concatena a String temp com !
		temp2[0] = $2; // Coloca a variável numa String temporária
		temp2[1] = '\0';
		strcat(temp,temp2);
		strcat(temp,".");// Concatena com a String temp.
		} 
		termo
	;

termo_par: '('{
		strcat(temp,"(");// Concatena com a String temp.
		} termo ')' {
		strcat(temp,")");// Concatena com a String temp.
		}
	;

%%

char *temporaria,*reduzida; // Temporárias.

int calc_str_size(char *c){ // Auxiliar que calcula o tamanho da string.
	
	int count = 0;
	int i = 0;

	while(c[i] != '\0'){ // Incrementa o contador até que seja encontrado o caractere nulo (fim da string).
		i++;
		count++;
	}

	return count; // Retorna o tamanho da String.
}
	
int conta_lambda(char *c){ // Função que conta o número de lambdas na string.

	int count = 0;
	int i = 0;

	while(c[i] != '\0'){
		if(c[i] == '!'){ // Se é encontrado um !, é incrementado o contador.
			count++;
		}
	i++;
	}

	return count; // Retorna o número de lambdas na string.

}

int conta_espacos(char *c){ // Conta o número de espaços existentes numa String.
	int count = 0, i= 0;
	
	while(c[i] != '\0')
	{
		if(c[i] == ' ')	// Se for encontrado um espaço, incrementa o contador.
		{
			count++;
		}
	i++;
	}
	return count; // Retorna o número de espaços na string.
}

int conta_prt(char *c){	// Conta o número de parêntesis existentes numa String.
	int count = 0, i= 0;
	
	while(c[i] != '\0')
	{
		if((c[i] == '(') || c[i] == ')')	// Se for encontrado um parêntesis, incrementa o contador.
		{
			count++;
		}
	i++;
	}
	return count;// Retorna o número de parêntesis na string.
}


int conta_var(char *c, char v){	// Conta o número de ocorrências de uma dada variável na String.
	int count = 0;
	int i = 0;

	while(c[i] != '\0'){
		if(c[i] == v){	// Se for encontrada uma variável, incrementa o contador.
			count++;
		}
	i++;
	}
	
	return count;// Retorna o número de ocorrências da variável.

}


char* remove_par(char *c){	// Auxiliar que permite remover os parêntesis de uma String.

	int i = 0, j = 0;
	int new_size = calc_str_size(c) - conta_prt(c); // Calcula o tamanho da string sem parêntesis.
	char new[new_size];

	while(c[i] !='\0'){

		if(c[i] != '(' && c[i] != ')'){	// Copia a string sem os parêntesis para uma temporária.
			new[j] = c[i];
			j++;
		}
		i++;

	}
	new[new_size] = '\0';
	temporaria = new;
	return temporaria; // Retorna a temporária.
}

void init_str(char *c){	// Inicializa uma string.

	int i;
	int size = calc_str_size(c);
	for(i=0;i<size;i++)
	{
		c[i] = ' '; // Preenche a string com espaços.
	}
	c[size] = '\0';
}

char* virar_str(char *s){ // Auxiliar que divide uma string em duas
	char *token,*token2;
	char back[100]; // temporario
	init_str(back); // Inicializa a string temporária.
	token = strtok(s, " "); // Obtém a primeira parte da string.
	token2 = strtok(NULL, " "); // Obtém a segunda parte da string.
	strcpy(back,token2);	// Copia a segunda parte da string para a temporária.
	strcat(back," ");	// Concatena a temporária com o espaço.
	strcat(back,token);	// Concatena a temporária com a primeira parte da string argumento.
	temporaria = back;	
	return temporaria;	// Retorna a temporária.
}

char* redux(char *c){
	// Auxiliar que faz a redução.
	int i = 0, lb = 0, cp, esp, total_esp = conta_espacos(c);
	char var;
	int conta_aux;
	conta_aux = conta_lambda(c);
	
	// Se tiver só um parentesis
	if(conta_prt(c) == 1)
	{
		char *temp_aux2 = remove_par(c);
		return(temp_aux2);
	}
	
	// Se não tiver termos lamda
	if(conta_aux == 0)
	{
		int cpar = conta_prt(c);
		if(cpar != 0){
			char *cn = remove_par(c);
			return cn;
		}
		reduzida = c;
		return reduzida;
	}
	
	// Se não houver espaços
	conta_aux = conta_espacos(c);
	if(conta_aux == 0 && conta_lambda(c) == 1&&conta_prt(c)==0)
	{	
		reduzida = c;
		return reduzida;
	}
	
	// Se o ultimo termo foi igual as variaveis lambda e não houver parentesis
	if(conta_espacos(c) != 0 && conta_prt(c) == 0)
	{
		int aux = 0;
		i = 0;
		while(aux != total_esp) //Encontrar a localização do ultimo espaço (esp)
		{
			if(c[i] == ' ')
			{
				aux++;
			}
			i++;
		}
		esp = i;
		int k = 0;
		char aux_var[conta_lambda(c)+1];
		i = 0;
		while(c[i]!='\0')
		{
			if(c[i] == '!')
			{
				aux_var[k] = c[i+1];
				k++;
			}
			i++;
		}
		aux_var[k] = '\0';
		i = esp;
		if(c[i] != '!')
		{
		while(c[i] != '\0')
		{
			for(k=0; k <= conta_lambda(c)-1; k++)
			{
				if(c[i] == aux_var[k])
				{
					reduzida = c;
					return reduzida;
				}
			}
			i++;
		}
		
		}
	}
	
	// Para o caso de existir apenas um lambda
	int lambdas = conta_lambda(c);
	if(conta_aux == 0 && lambdas == 1 && conta_prt(c)!=0){ 

		int i = 0, j = 0;
		int new_size = calc_str_size(c) - conta_prt(c);
		char new[new_size];

		while(c[i] !='\0'){

			if(c[i] != '(' && c[i] != ')'){
				new[j] = c[i];
				j++;
			}
			i++;

		}
		new[new_size] = '\0';
		reduzida = new;
		return reduzida;

	}
	
	// Se começar com parentesis
	if(conta_prt(c)!= 0 && c[0] == '(' && conta_espacos(c) == 0)
	{
		i=0;
		char aux2f[calc_str_size(c)];
		char temp[calc_str_size(c)];
		while(c[i] != ')')
		{
			temp[i] = c[i];
			i++;
		}
		temp[i] = '\0';
		char *aux33 = remove_par(temp);
		int aux34 = calc_str_size(temp);
		int aux35 = 0;
		for(aux35 = 0; aux35 <= aux34; aux35++)
		{
			aux2f[aux35] = aux33[aux35];
		}
		aux35= aux35-2;
		i++;
		while(c[i] != '\0')
		{
			aux2f[aux35] = c[i];
			i++;
			aux35++;
		}
		aux2f[aux35] ='\0';
		reduzida = aux2f;
		return reduzida;
	}
		
	// Aplicar Redução

	while(c[lb] != '!') // Encontrar o 1º Lambda
	{
		lb++;
	}
	// Termo identidade
	if(conta_prt(c) != 0 && lb != 1) // Se houver parentesis e o 1º lambda não estiver no inicio eg = z (!x.x)
	{
		i= lb;
		if(c[i-1] == '(' && c[i+1] == c[i+3] && c[i+4] == ')')
		{
			i=0;
			char aux_temp5[calc_str_size(c)];
			while(c[i] != '!')
			{
				aux_temp5[i] = c[i];
				i++;
			}
			aux_temp5[i-2] ='\0';
			char *aux_temp6 = remove_par(aux_temp5);
			return aux_temp6;
		}
	}	

	var = c[lb+1]; // Variavel do lambda
	int aux = 0;
	i = 0;
	while(aux != total_esp) //Encontrar a localização do ultimo espaço (esp)
	{
		if(c[i] == ' ')
		{
			aux++;
		}
		i++;
	}
	esp = i;
	
	int  temp = calc_str_size(c)-esp; // Calcular o tamanho da string
	char copia[temp+1]; // Criar uma string para armazenar a string desde do esp até '\o'
	cp = 0;
	while(c[i]!='\0') // Fazer copia do final da string
	{
		copia[cp] = c[i];
		i++;
		cp++;
	}
	copia[cp]='\0';
	
	//Nova String para armazenar o novo resultado
	int size_nova = (conta_var(c,var)-1) * calc_str_size(copia) + calc_str_size(c)-calc_str_size(copia)-4;
	char nova[size_nova]; // Nova String 
	i = lb+3;  // Começar deps do .
	aux = 0;
	int aux2 = calc_str_size(copia);
	
	//Copiar o final da string original para a nova sempre que encontrar a variavel lambda
	while(i!=esp)
	{
			if(c[i] == var)
			{
				int k = 0;
				while(k != aux2)
				{
					nova[aux] = copia[k];
					aux++;
					k++;
				}
				aux--;
			}
			else
			{
				nova[aux] = c[i];
			}
			i++;
			aux++;
	}

	nova[aux-1] = '\0';
	temp = calc_str_size(nova);
	redux(nova);
}

void callbyname(char *c){ // Função que efectua a redução.
	printf("<- %s\n",c);
	char * reduzida = redux(c);
	printf("-> %s\n",reduzida);
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
