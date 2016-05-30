#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "compressor.h"
#define SIZEF 9

int cod[3]; //armazena os valores que indicam o resultado da comparação entre o bloco da imagem e o do dicionario e as coordendas do dicionario

// Função que executa uma pesquisa no dicionario à procura de ocorrencias dentro do dicionario e que retorna um array que contem um valor do resultado da comparação
// e as coordenadas do dicionario onde foi encontrada a comparação ou onde ainda o dicionario não tem valores
int* pesqDic(char *valor, char dicionario[_blocos][_numeroBlocos]){

int i,j,k,m,blocos; //variaveis auxiliares
char aux[_numeroBlocos]; //array que vai armazenar o valor de um bloco do dicionario

    for(i=0;i<_blocos;i++){
        for(j=0; j<_numeroBlocos;j++){
            aux[j] = dicionario[i][j]; //copiar o bloco de informação do dicionario
        }
        if(valor[0] == aux[0] && valor[1] == aux[1] && valor[2] == aux[2]){ //verificar se os blocos da imagem e do dicionario são iguais
            m = 0;
        }
        else m = 1;
        if(m == 0){ // se os blocos forem iguais
            cod[0] = 0; // variavel que representa a igualdade entre os blocos do dicionario e imagem
            cod[1] = i; // Coordenadas do dicionario que indicam onde se encontra o bloco no dicionario
            cod[2] = j;
            return  cod;
        }
        else if(aux[0] == '-'){ // se os blocos forem diferentes
            cod[0] = 1; // variavel que representa a diferença entre os blocos do dicionario e imagem
            cod[1] = i; // Coordenadas do dicionario que indicam onde se encontra o bloco no dicionario
            cod[2] = j;
            return cod;
        }
    }
}

void comprime(char *formato, int fsize,char *imagem, int numCarImg,FILE *fw,FILE *dic){ // Função que vai efectuar a codificação e compressão da imagem em formato PBM

    // variaveis auxiliares e apontadores
FILE *fk;
int i,j,k,x,y,sdt,blocos,numBloc;

	fw = fopen("comprimida.data","wb");
	dic = fopen("dicionario.d","wb");

	for(i=0;i<fsize;i++){ //escreve as informaçoes do formato no ficheiro comprimido
		if(fw!=NULL){
            		fprintf(fw,"%c",formato[i]);
        	}
	}

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


blocos = numCarImg / numBloc; //calcula o numero de blocos

	_numeroBlocos = numBloc;

	_blocos = blocos;

//constroi o dicionario com nlinhas = numero de blocos e ncolunas = tamanho do bloco.
//cada linha do dicionario armazena um bloco
char dicionario[blocos][numBloc];
//matriz temporaria que vai armazenar a imagem
char temp[blocos][numBloc];

//coloca a imagem em blocos na matriz temporaria
	for(k=0;k<numCarImg;k++){
		for(i=0;i<blocos;i++){
			for(j=0;j<numBloc;j++){
				temp[i][j] = imagem[k];
				k++;
			}
		}
	}
// Inicializa a Matriz do Dicionario com '-'
	for(j = 0; j<blocos;j++){
		for(k=0;k<numBloc;k++){
			dicionario[j][k] = '-';
		}
	}
// variaveis e apontadores auxiliares
int m,n,o,p;
char bloctemp[numBloc]; // array temporario que vai armazenar um bloco da imagem
int *auxiliar;
char *indice;

    for(m = 0; m < blocos; m++){
        for(n = 0 ; n < numBloc; n++){
            bloctemp[n] = temp[m][n]; // preenche o array temporario com os valores da imagem que vão formar um bloco
        }
        auxiliar = pesqDic(bloctemp, dicionario); // variavel auxiliar para armazenar o resultado da pesquisa sobre o dicionario
        if(auxiliar[0] == 0){  // se o resultado da pesquisa indicar que o bloco da imagem é igual ao do dicionario
            fprintf(fw,"%d",auxiliar[1]); // escreve no novo ficheiro(comprimido) o indice do Dicionario onde o bloco já está inserido
        }
        else if(auxiliar[0] == 1){ // se o resultado da pesquisa indicar que o bloco da imagem é diferente do dicionario
            for(n = 0 ; n < numBloc; n++){
                dicionario[auxiliar[1]][n] = temp[m][n]; // Escreve no fim do dicionario o novo bloco encontrado
                fprintf(dic,"%c",dicionario[auxiliar[1]][n]); // Escreve no fim do ficheiro dicionario o novo bloco encontrado
            }
            fprintf(dic,"%c",'\n');
            fprintf(fw,"%d",auxiliar[1]);// Escreve no novo ficheiro(comprimido) o indice do Dicionario
        }
    }

	printf("Compressão Concluída! \n");
}

void fileOperations(char * filename){ // funções que executa operações relacionadas com a correcta inicialização de ficheiros e comparação de formatos pbm

int i,j,s,k,sbt,calc,counter,si,simg,sformcom;
char formato[SIZEF];
FILE *f,*fw,*dic;

	f = fopen(filename,"rb"); // abre o ficheiro para escrita

	if(f == NULL){
    		printf("Impossível abrir o ficheiro! \n");
	}
	else{
			printf("Ficheiro aberto com sucesso! \n");
	}

	fread(formato, sizeof(char), 9, f);  //lê o formato e as dimensoes da imagem para um buffer temporario(formato)

	if(formato[0]!='P' && formato[1]!='4'){ // Confirma se o o formato da imagem é o correcto
    		printf("Formato PBM errado! \n");
	}
	else{
    		printf("Formato PBM correcto! \n");
	}

	fseek(f,0,SEEK_END);//coloca o ponteiro no fim do ficheiro

	s = ftell(f); //numero de bytes do ficheiro

	fseek(f,0,SEEK_SET); //coloca o ponteiro no inicio do ficheiro
	fseek(f,9,SEEK_SET); //anda 9 posiçoes pra frente, saltando o formato

	k = ftell(f); //numero de bytes ocupado pelo formato da imagem (P4 + dimensoes)
	sbt = s - k; //calcula o numero de bytes restantes, sem o formato aka tamanho do buffer temporario
	
char buffertemp[sbt];

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
//copia o comentario para um array auxiliar
char comment[calc];
	for(i=0;i<calc;i++){
		comment[i] = buffertemp[i];
	}
//calcula o tamanho da imagem
	si = sbt - calc;

//calcula o numero de espaços, newlines e carriage retunrs
	counter = 0;
	for(i = calc; i < sbt; i++){
		if(buffertemp[i]==' ' || buffertemp[i]=='\n' || buffertemp[i]=='\r'){
			counter++;
    		}
	}
	
	simg = si - counter; //calcula o tamanho da imagem sem espaços, newlines e carriage returns
	
char img[simg]; // array que irá conter os bytes que compõem a imagem final

// coloca a imagem num array auxiliar
j = 0;
	for(i=calc; i<sbt; i++){
		if(buffertemp[i]!=' ' && buffertemp[i]!='\n' && buffertemp[i]!='\r'){
			img[j] = buffertemp[i];
			j++;
		}
	}
    //calcula o tamanho de um buffer que irá conter o formato da imagem, as dimensoes e os comentarios da mesma.
	sformcom = SIZEF + calc;
	
char formcom[sformcom];

	fseek(f,0,SEEK_SET); //coloca o ponteiro no inicio do ficheiro
	fread(formcom,sizeof(char),sformcom,f);
	
	//inicia a compressao
	comprime(formcom, sformcom,img, simg,fw,dic);
}

void main(){
	char namefile[500];
	char extensao[] = ".pbm";
	printf("Insira o nome do ficheiro a comprimir: \n");
	gets(namefile);
	
	if(strstr(namefile,extensao)==NULL){            // se o nome do ficheiro não contem a extensão .pbm
		printf("Não é um ficheiro PBM! \n");    // imprime uma mensagem de erro
		main();            // e volta à função
	}
	else{
		fileOperations(namefile);
	}
}


