#include <stdlib.h>
#include <stdio.h>
#include "indexstory.c"
#include <string.h>

void main(){

int escolha=1;


struct Historia *baseDados=malloc(50*sizeof(struct Historia));
struct BTree *(*storyBTrees)=malloc(50*sizeof(struct BTree));
	int historias=0;
	struct Historia h1;
	novaHistoria(&h1);
	baseDados[0]=h1;
	historias+=1;
	struct BTree *mainroot;
	nova(&mainroot);
		

	mainroot=savewords(&h1, mainroot, 0);	
	mainroot=savewords(&h1, mainroot, 1);
	mainroot=savewords(&h1, mainroot, 2);
	mainroot=savewords(&h1, mainroot, 3);
	
	nova(&storyBTrees[0]);

	storyBTrees[0]=savewords(&h1, storyBTrees[0], 0);
	storyBTrees[0]=savewords(&h1, storyBTrees[0], 1);	
	storyBTrees[0]=savewords(&h1, storyBTrees[0], 2);	
	storyBTrees[0]=savewords(&h1, storyBTrees[0], 3);		


	while(escolha!=0){
	printf("%s","Por favor escolha uma das opções abaixo: \n");
	printf("%s","1 - Listar todas as palavras e a sua frequência; \n");
	printf("%s","2 - Listar todas as palavras da história e a sua frequência; \n");
	printf("%s","3 - Listar o título e o nome dos ficheiros que têm a personagem; \n");
	printf("%s","4 - Listar conclusões que contêm a palavra; \n");
	printf("%s","5 - Listar títulos e nomes de ficheiros que contêm a palavra; \n");
	printf("%s","6 - Acrescentar informação ao sistema; \n");
	printf("%s","0 - Sair \n");
	printf("\n\n %s - %d\n", "Número de histórias guardadas na base de dados ", historias);
	scanf("%d",&escolha);

	if(escolha==1){
	listar(mainroot,0);	
	}
	if(escolha==2){
        char nomehist[128];
        printf("%s","Por favor insira o nome de uma história: \n");
	
	scanf("%s",nomehist);
	
	int i;
	int existe=0;
	for(i=0;i<historias;i++){
		if((compareString(nomehist,baseDados[i].fileName))==0){
			existe=1;
			listar(storyBTrees[i],0);
			break;
		}
		}
	
	if(existe==0)
		printf("%s\n\n", "História não encontrada.");
		printf("%s\n", nomehist);
	continue;
	}

	if(escolha==3){
	    char personagem[128];
        printf("%s","Por favor insira o nome de uma personagem: \n");
        scanf("%s",&personagem);
        searchPersonagem(baseDados,personagem); //falta parte da base de dados.
        continue;
	}
	if(escolha==4){
        char palavra[128];
        printf("%s","Por favor insira uma palavra: \n");
        scanf("%s",&palavra);
        searchConclu( baseDados,palavra);//falta parte da base de dados.
        continue;

	}
	if(escolha==5){
        char palavra[128];
        printf("%s","Por favor insira uma palavra: \n");
        scanf("%s",&palavra);
        searchTexto(baseDados,palavra);//falta parte da base de dados.
        continue;
	}
	if(escolha==6){
	struct Historia h;
	novaHistoria(&h);
	baseDados[historias]=h;
	mainroot=savewords(&h, mainroot, 0);	
	mainroot=savewords(&h, mainroot, 1);
	mainroot=savewords(&h, mainroot, 2);
	mainroot=savewords(&h, mainroot, 3);
	nova(&storyBTrees[historias]);
	storyBTrees[historias]=savewords(&h, storyBTrees[historias], 0);	
	storyBTrees[historias]=savewords(&h, storyBTrees[historias], 1);
	storyBTrees[historias]=savewords(&h, storyBTrees[historias], 2);
	storyBTrees[historias]=savewords(&h, storyBTrees[historias], 3);	
	historias+=1;
        continue;
	}

    if(escolha==0){
        return;
    }

    //falta por isto como ciclo.
	}
}

