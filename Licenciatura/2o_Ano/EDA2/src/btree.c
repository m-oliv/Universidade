#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "btree.h"
#include "indexstory.h"

void novoNo(struct Node **n){

    *n=malloc(sizeof(**n));
	(*n)->value=calloc(30, sizeof(char));
	(*n)->frequencia=0;

}


void nova(struct BTree **t){

	*t = malloc(sizeof(**t));
	(*t)->folha=1;
	(*t)->n=0;

	struct Node *empty;
	novoNo(&empty);
	int i=0;

	for(i=0; i<maxfilhos; i++)
		(*t)->chaves[i]=empty;
}


int compareString(char *left, char *right){



	int i=0;
	while(i<strlen(right)+1&&i<strlen(left)+1){

		if(left[i]=='\0'){
			if(right[i]=='\0')
				return 0;
			else
				return -1;}


		if(right[i]=='\0')
			return 1;

		if(left[i]==right[i]){i++;
			continue;}

		if(left[i]<right[i])
			return -1;
		if(left[i]>right[i]);
			return 1;
		i++;
	}

}

void split(struct BTree *parent, int pos, struct BTree *fullChild){

	struct BTree *adopting; //biggest values
	nova(&adopting);
	adopting->n=ordem-1;

	struct Node *empty;
	novoNo(&empty);

	struct BTree *emptyBT;
	nova(&emptyBT);

	int i;



	for(i=0; i<(ordem); i++){
		adopting->chaves[i]=fullChild->chaves[i+ordem];


		if(fullChild->folha==0){
			adopting->filho[i]=fullChild->filho[i+ordem];
			fullChild->filho[i+ordem]=emptyBT;
			adopting->folha=0;
			}

	}


	adopting->filho[ordem]=fullChild->filho[ordem];


	for((i=maxchaves-1); i>(pos); i--){
		parent->chaves[i]=parent->chaves[i-1];
		parent->filho[i]=parent->filho[i-1];
		}
	if(pos<maxchaves-1)
		parent->filho[maxchaves]=parent->filho[maxchaves-1];

	struct Node *goesUp;
	novoNo(&goesUp);
	goesUp=fullChild->chaves[ordem-1];

	fullChild->chaves[ordem-1]=empty;
	fullChild->n=ordem-1;

	parent->chaves[pos]=goesUp;
	parent->filho[pos]=fullChild;
	parent->filho[pos+1]=adopting;
	parent->n++;
	parent->folha=0;

}





void insertNotFullLeaf(struct BTree *b, char * string){

	int i=0;
	int j=0;
	struct Node *toIns;
	novoNo(&toIns);
	toIns->value=string;
	toIns->frequencia++;
	int insert;

	for(i=0; i<b->n; i++){
		int check=compareString(string, b->chaves[i]->value);


		if(check==0){//chave a inserir já se encontra na BTree
			b->chaves[i]->frequencia++;
			return;
			}
		if(check==-1){
			for(j=maxchaves; j>i; j--){
				b->chaves[j]=b->chaves[j-1];
				}

			break;
		}
		//como é folha não temos de alterar children, são todos null.
	}
	//chegados aqui estamos em n e todas as chaves já na BTree são menores.


	b->chaves[i]=toIns;
	b->n++;


}

void insertNotFullNonLeaf(struct BTree *b, char * string){

	int i=0;
	int check;

	for(i=0; i<b->n; i++){
		if(b->chaves[i]->value[0]!='\0'){
			check=compareString(string, b->chaves[i]->value);}
		else
			break;

		if(check==0){ //já na BTree
			b->chaves[i]->frequencia++;
			return;}
		if(check==-1){
			break;
		}
	}

	if(b->filho[i]->n==maxchaves){
		split(b, i, b->filho[i]);

		if(b->n!=5)
			insertNotFullNonLeaf(b, string);




		else
		{
			check=compareString(string, b->chaves[i]->value);
			if(check==1)
				if(!b->filho[i+1]->folha)
					insertNotFullNonLeaf(b->filho[i+1],string);
				else
					insertNotFullLeaf(b->filho[i+1],string);
			else
				if(!b->filho[i]->folha)
					insertNotFullNonLeaf(b->filho[i],string);
				else
					insertNotFullLeaf(b->filho[i],string);
		}

		return;}


	if(!b->folha){
		if(i==b->n){
			if(b->filho[i]->folha==1){
				insertNotFullLeaf(b->filho[i], string);
				return;}
			else{
				insertNotFullNonLeaf(b->filho[i], string);
				return;}
			}
		check=compareString(string, b->chaves[i]->value);
			if(check==1)
				if(!b->filho[i+1]->folha)
					insertNotFullNonLeaf(b->filho[i+1],string);
				else
					insertNotFullLeaf(b->filho[i+1],string);
			else
				if(!b->filho[i]->folha)
					insertNotFullNonLeaf(b->filho[i],string);
				else
					insertNotFullLeaf(b->filho[i],string);


		}
	else
		insertNotFullLeaf(b, string);

}




void listar(struct BTree *b, int apenasRoot){

	int i;
	if(apenasRoot==1)
		for(i=0; i<b->n; i++){
			printf("%s ,", b->chaves[i]->value);
			printf("%d ,",b->chaves[i]->frequencia);
		}

	else{

	if(b->folha==1){
		for(i=0; i<b->n; i++){
			printf("%s - ", b->chaves[i]->value);
			printf("%d ,",b->chaves[i]->frequencia);
			}
		}
	else
		for(i=0; i<b->n+1; i++){
			listar(b->filho[i], apenasRoot);
			if(i<b->n)
				printf("%s ,", b->chaves[i]->value);
				printf("%d ,",b->chaves[i]->frequencia);
		}
	}

}

struct BTree *inserir(struct BTree *b, char * string){


	if(b->n==maxchaves){
			struct BTree *newRoot;
			nova(&newRoot);
			split(newRoot, 0, b);
			inserir(newRoot, string);
			return newRoot;
		}
	else
		if(b->folha)
			insertNotFullLeaf(b, string);
		else
			insertNotFullNonLeaf(b, string);

	return b;
}


struct BTree *savewords(struct Historia *h, struct BTree *b, int func){//func=0: título
									//func=1: personagens
									//func=2: conclusões
									//func=3: texto


	int pos=0;

	int size=0;
	char *text;
	if(func==0)
		text=h->titulo;
	if(func==1)
		text=h->personagens;
	if(func==2)
		text=h->conclusoes;
	if(func==3)
		text=h->texto;

	char c;



	while(pos<strlen(text)){
		c=text[pos];
		if((c<0)){
			size+=2;
			pos+=2;
			c=text[pos];
			}
		else
		if(!((c<='º'&&c>='Ç')||(c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')))
			pos++;

		while((c<0)||(c<='º'&&c>='Ç')||(c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')){

			size+=1;
			if((c<0)){
				size+=2;
				pos+=2;
				c=text[pos];
			}
			if(c==' ')
				break;

			pos++;
			c=text[pos];
		}

		if(size!=0){
			char *word;
			word=malloc(size*sizeof(char));
			int j;
			for(j=0;j<size;j++){

				word[j]=text[pos-(size-j)];

				}
			word[j]='\0';
			b=inserir(b,word);
			}


		size=0;
	}
	return b;
}
