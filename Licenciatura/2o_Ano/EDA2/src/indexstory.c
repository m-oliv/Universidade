#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "indexstory.h"
#include "btree.c"
#include "automato.c"

int calcsize(FILE *f, int ignoreNewLines){

        int strsize = 0;
        char c=fgetc(f);
        struct BTree *b;
        nova(&b);

	if(ignoreNewLines==1)
		while( c != EOF && c != '\n'){
		        strsize++;
		        c=fgetc(f);
		}
	else
		while( c != EOF){
		        strsize++;
		        c=fgetc(f);
		}
        return strsize;
}



void getString(FILE *f, char *s, int size){

        char c=fgetc(f);
	int i=0;
        while( i<size){
		s[i]=c;
		i++;
                c=fgetc(f);
        }
}


int file_ops(char *st, struct Historia *h) {

        FILE *fp= fopen( st,"r");
        if(  fp  == NULL) {
                puts("Caminho errado ou ficheiro não existente.");
                exit( 0 );
        }

	int i=0;
	char *text[4];
	for(i=0; i<4; i++){

		int size;
		if(i==3)
			size=calcsize(fp, 0);
		else
			size=calcsize(fp, 1);

		char * string=malloc(size*sizeof(char));
		if(i==0) { //título
			fseek(fp, 0-(size+1-8), 1);
			getString(fp, string, size-8);
			string[size-8]='\0';

		}
		if(i==1) { //personagens
			fseek(fp, 0-(size+1-13), 1);
			getString(fp, string, size-13);
			string[size-13]='\0';
		}
		if(i==2) { //conclusões
			fseek(fp, 0-(size+1-12), 1);
			getString(fp, string, size-12);
			string[size-12]='\0';
		}
		if(i==3) { //texto
			fseek(fp, 0-(size+1-7), 1);
			getString(fp, string, size-7);
			string[size-7]='\0';
		}

		text[i]=string;
		if(i!=3)
			fgetc(fp); //salta newline
	}


	h->titulo=text[0];
	h->personagens=text[1];
	h->conclusoes=text[2];
	h->texto=text[3];
        fclose(fp);
}


struct Historia *novaHistoria(struct Historia *h){

	printf("Insira um nome de uma hitória a adicionar à base de dados:\n");
     	char st[128];
	malloc(sizeof(st));
	scanf("%s",st);
	file_ops(st, h);

	h->fileName = malloc(sizeof(st));
	strcpy(h->fileName,st);


	return h;
}


void searchPersonagem(struct Historia *basededados, char *nome){
	int i=0;
	int size_nome=strlen(nome);
	printf("Titulo    |    Nome do Ficheiro    |\n");
	while(basededados[i].titulo!='\0'){
		int size_pers=strlen(basededados[i].personagens);
		int *search=malloc(size_pers*sizeof(int));
		search=aut(basededados[i].personagens, nome);
		int j;
		for(j=0; j<size_pers; j++){
			if(search[j]==size_nome){
			char *titulo=basededados[i].titulo;
			char *ficheiro=basededados[i].fileName;
			printf("%s | %s \n",titulo,ficheiro);
			}

		}
		i+=1;

	}
}

void searchConclu(struct Historia *basededados, char *palavra){
	int i=0;
	int size_palavra=strlen(palavra);
	printf("Conclusoes: \n");
	while(basededados[i].titulo!='\0'){
		int size_conc=strlen(basededados[i].conclusoes);
		int *search=malloc(size_conc*sizeof(int));
		search=aut(basededados[i].conclusoes, palavra);
		int j;
		for(j=0; j<size_conc; j++){
			if(search[j]==size_palavra){
			printf("- %s \n", basededados[i].conclusoes);
			}
		}
		i+=1;

	}
}


void searchTexto(struct Historia *basededados, char *palavra){
	int i=0;
	int size_palavra=strlen(palavra);
	printf("Titulo    |    Nome do Ficheiro    |\n");
	while(basededados[i].titulo!='\0'){
		int size_texto=strlen(basededados[i].texto);
		int *search=malloc(size_texto*sizeof(int));
		search=aut(basededados[i].texto, palavra);
		int j;
		for(j=0; j<size_texto; j++){
			if(search[j]==size_palavra){
			char *titulo=basededados[i].titulo;
			char *ficheiro=basededados[i].fileName;
			printf("%s | %s \n",titulo,ficheiro);
			}

		}
		i+=1;

	}
}

