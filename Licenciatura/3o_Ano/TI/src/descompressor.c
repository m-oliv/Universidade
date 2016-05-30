#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "descompressor.h"
#define SIZEF 9

void descodifica(char *formato,int fsize,char *buffer, int bsize,char *dictemp,int sdt,FILE *fw){ //Função que irá efectuar a descompressão da imagem

int i,s,x,y,numBloc,sbt,j,k,blocos; //variaveis auxiliares

	fw = fopen("descomprimida.pbm","wb");
	
	for(i=0;i<fsize;i++){ //escreve o formato e as dimensões no ficheiro descomprimido
		if(fw!=NULL){
			fprintf(fw,"%c",formato[i]);
		}
	}
	fprintf(fw,"%c",formato[i]);	

	if(formato[fsize-1]!= '\n'){ //para o caso de existirem comentarios
		fprintf(fw,"%c",'\n');
		fprintf(fw,"%c",'\n');
	}

	x = sdt % 2; 
	y = sdt % 3;

   	if(x == 0 && y ==0 ){ 

            numBloc = 3;//se o numero de caracteres k compõem a imagem for multiplo de 2 e 3, os blocos sao de tamanho 3

   	 }
	else{
		if(x==0){
			numBloc = 4;//se o numero de caracteres que compoe a imagem for par, os blocos sao de tamanho 4
		}
		else numBloc = 3; //senao, sao blocos de tamanho 3
	}


blocos = sdt / numBloc; //calcula o numero de blocos no dicionario

//constroi o dicionario com nlinhas = numero de blocos e ncolunas = tamanho do bloco.
//cada linha do dicionario armazena um bloco
char dicionario[blocos][numBloc];

	for(k=0;k<sdt;k++){ //cria o dicionário
		for(i=0;i<blocos;i++){
			for(j=0;j<numBloc;j++){
				dicionario[i][j] = dictemp[k];
				k++;
			}
		}
	}

//variáveis auxiliares
int ck; 
char c;

	for(i=0;i<bsize;i++){ 
		c = buffer[i];
		ck = atoi(&c);//converte os indices de char para int
		
		if(i < blocos || i == blocos){
			for(j=0;j<numBloc;j++){
			fprintf(fw,"%c",dicionario[ck][j]); //escreve o bloco no ficheiro da imagem descomprimida
			}
	
		}
	}

	
	printf("Descompressão Concluída! \n");

}

void fileOperations(char * filename){ //Função que executa operações relacionadas com a correcta inicialização de ficheiros e comparação de formatos pbm

int i,j,s,k,sbt,calc,si,sformcom,counter,sdt,si2;
char formato[SIZEF];
FILE *f,*fw,*dic;

	f = fopen(filename,"rb"); //abre o ficheiro para leitura.

	if(f == NULL){
		printf("Impossível abrir o ficheiro! \n");
	}
	else{
		printf("Ficheiro aberto com sucesso! \n");
	}

	fread(formato, sizeof(char), SIZEF, f); //lê o formato e as dimensoes da imagem para um buffer temporario(formato)

	fseek(f,0,SEEK_END);//coloca o ponteiro no fim do ficheiro
	s = ftell(f); //numero de bytes do ficheiro
	fseek(f,0,SEEK_SET); //coloca o ponteiro no inicio do ficheiro
	fseek(f,SIZEF,SEEK_SET); //anda 9 posiçoes pra frente, saltando o formato
	k = ftell(f); //numero de bytes ocupado pelo formato da imagem (P4 + dimensoes)

	sbt = s - k; //calcula o numero de bytes restantes, sem o formato aka tamanho do buffer temporario

	char buffertemp[sbt]; //buffer temporário

	fread(buffertemp,sizeof(char),sbt,f); //copia a informaçao para o buffer temporario

	//calcula o numero de bytes ocupado pelo comentario
	calc=0;

	for(i=0;i<sbt;i++){

		if(buffertemp[i]=='#'){
			while(buffertemp[i]!='\n'){
				calc++;
				i++;
				 if(i==sbt){
		                	break;
				}
			}
		}
	}

	if(calc!=0){ //se existirem comentários copia o comentario para um array a parte

	char comment[calc];

	for(i=0;i<calc;i++){
		comment[i] = buffertemp[i];

		}
	}

	si = sbt - calc; // calcula o tamanho da informaçao que ainda se encontra no buffer e que ainda nao foi processada

	char info[si]; 
	//coloca a informação referida acima num buffer temporário
	k=0;	
	for(i = calc; i < sbt; i++){
		info[k] = buffertemp[i];
		k++;
	}

	//calcula o número de newlines, carriage returns e espaços (se não existirem nenhums, counter = 0)
	counter = 0;

	for(i = 0; i < si; i++){
	    if(info[i]==' ' || info[i]=='\n' || info[i]=='\r'){
	        counter++;
	    }
	}


	si2 = si - counter; //calcula de um buffer temporario, cujo conteudo será a informação que ainda não foi processada, mas que já não irá conter espaços, newlines e carriage returns.

char info2[si2];
//coloca a informação referida acima num buffer temporário
	j = 0;
	for(i=counter; i<si; i++){
		if(info[i]!=' ' && info[i]!='\n' && info[i]!='\r'){
			info2[j] = info[i];
			j++;
		}
	}

	//calcula o tamanho de um buffer que irá conter o formato da imagem, as dimensoes e os comentarios da mesma.
	sformcom = SIZEF + calc;
	
char formcom[sformcom];

	fseek(f,0,SEEK_SET); //coloca o ponteiro no inicio do ficheiro
	fread(formcom,sizeof(char),sformcom,f); //copia o formato, as dimensoes e os comentarios da imagem para um buffer

	dic = fopen("dicionario.d","rb");


	fseek(dic,0,SEEK_END);//coloca o ponteiro no fim do ficheiro
	s = ftell(dic); //numero de bytes do dicionário
	fseek(dic,0,SEEK_SET); //coloca o ponteiro no inicio do ficheiro

char dictemp2[s];

	fread(dictemp2,sizeof(char),s,dic); //copia para um array temporário a informação que está no ficheiro do dicionário

//conta \n \r e ' ' que possam existir no dicionário

	counter = 0;

	for(i = 0; i < s; i++){
		if(dictemp2[i]==' ' || dictemp2[i]=='\n' || dictemp2[i]=='\r'){
			counter++;
    		}
	}

	sdt = s - counter; //calcula o tamanho do buffer que irá conter o dicionário, já sem \r, \n e ' '

char dictemp[sdt];
	//copia o dicionario devidamente processado para um buffer
	j = 0;
	for(i=0; i<s; i++){
		if(dictemp2[i]!=' ' && dictemp2[i]!='\n' && dictemp2[i]!='\r'){
			dictemp[j] = dictemp2[i];
			j++;
		}
	}
		
	//inicia a descompressão
	descodifica(formcom,sformcom,info2,si2,dictemp,sdt,fw);


}	

void main(){
	char namefile[500];
	char extensao[] = ".data";
	printf("Insira o nome do ficheiro a descomprimir: \n");
	gets(namefile);
	
	if(strstr(namefile,extensao)==NULL){
		printf("Não é um ficheiro válido! \n");
		main();
	}
	else{

	fileOperations(namefile);
	}

}
