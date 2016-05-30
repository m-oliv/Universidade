#include "trabalho.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 2

struct fila *new;
struct fila *ready;
struct fila *running;
struct fila *exitU;

struct fila *d1;
struct fila *d2;
struct fila *d3;

void initUSERDISP(struct fila * fi, int tdisp){ //Inicia uma fila de dispositivos com os valores default.
	fi->size=0;
	fi->p=NULL;
	fi->next=NULL;
	fi->tdisp=tdisp;
}
struct PCB* removeUSER (struct fila * fi, int tdisp){
	struct PCB *v; //elemento que vai ser removido. Este temporário é para o guardar, para retornar.
	if(isEmpty(fi)){
		return NULL; //não é possível remover duma fila vazia!
	}
	else{
		v=fi->p; //elemento a remover
		fi->size--; //fila decrementa o tamanho, já que perdeu 1 elemento
		if(tdisp!=-1) //se fôr fila de dispositivos, tem de manter o tempo de dispositivo guardado à cabeça.
			fi->next->tdisp=tdisp; //coloca o tempo de dispositivo na nova cabeça
		*fi = *fi->next; //Avança na fila, ficando a nova cabeça o elemento seguinte, efectivamente perdendo-se
						//a antiga cabeça, sendo "removida".

		return v;
	}
}

void initUSER(struct fila * fi){ //inicializa uma fila (não de dispositivos) com valores default
	fi->size=0;
	fi->p=NULL;
	fi->next=NULL;
}

void insertFIFO (struct PCB *p, struct fila * fi, int tdisp){ //insere o processo p na fila fi

	while( fi->next )
		fi=fi->next; //procura a cauda da fila para garantir comportamento FIFO

	fi->p=p; //coloca o novo elemento na cauda fa fila antiga

	struct fila * cauda=(struct fila*) malloc(sizeof(struct fila)); //cria a nova cauda sem elemento
	if(tdisp!=-1) //se for fila de dispositivos, propaga o tempo do dispositivo à cauda da nova fila
		initUSERDISP(cauda, tdisp);
	else
		initUSER(cauda);
	fi->next=cauda; //coloca a cauda da fila ligada ao elemento novo inserido
}


int isEmpty(struct fila *fi){ //se fi está vazia retorna 1, else retorna 0
	if((fi->p==NULL)&&(fi->next==NULL)){
		return 1;
	}
	return 0;
}

struct PCB* getFirst(struct fila *f){ //obtém o topo da fila
	return f->p;
}


int size ( struct fila *fi){//devolve o tamanho da fila

	int i = 0; 
	struct fila *temp=fi;
	while(!isEmpty(temp)){
		i++;
		temp=temp->next;}
	return i;
}

void newToReady(){
	/*ADMIT*/
	if(ready->size < MAX || ready->size == MAX){
		struct PCB *temp = removeUSER(new,-1);
		temp->estado = 'r';
		temp->t_cpu=0;
		insertFIFO(temp,ready, -1);
	}
	else{
		printf("Não é possível admitir novos processos na fila ready.\n");
	}


}

void blockedToReady(int disp){

	/*Event Occurs*/
	
	struct PCB *temp;
	if(disp==1)
		temp = removeUSER(d1, d1->tdisp);
	if(disp==2)
		temp = removeUSER(d2, d2->tdisp);
	if(disp==3)
		temp = removeUSER(d3, d3->tdisp);
		
	temp->estado = 'r';
	insertFIFO(temp,ready, -1);

}

void runningToReady(){

	/*Timeout*/
	struct PCB *temp = removeUSER(running, -1);
	temp->estado = 'r';
	insertFIFO(temp,ready,-1);

}


void runningToBlocked(){

	/*Event Wait*/

	struct PCB *temp = removeUSER(running, -1);
	temp->estado = 'b';
	if(temp->tempos[temp->current]==1)
		insertFIFO(temp,d1, d1->tdisp);
	if(temp->tempos[temp->current]==2)
		insertFIFO(temp,d2, d2->tdisp);
	if(temp->tempos[temp->current]==3)
		insertFIFO(temp,d3,d3->tdisp);
	temp->current++; //salta
}



void runningToExit(){

	/*Release*/
	struct PCB *temp = removeUSER(running,-1);
	temp->estado = 'e';
	insertFIFO(temp,exitU, -1);

}

void readyToRunning(){

	/*Dispatch*/

	struct PCB *temp = removeUSER(ready,-1);
	temp->estado = 'u';
	insertFIFO(temp,running, -1);

}

struct PCB * newProcess(int pID, int * tempos){ //cria um processo
	struct PCB *p = malloc(sizeof(struct PCB));
	p->pid =pID;
	p->timeInDisp=0;
	p->instI = tempos[0];
	p->estado = 'n';
	p->t_cpu=0;
	p->t_cpu_total=0;
	p->t_cpu_somado=0;
	p->tempos=tempos;
	int count=0;
	while(tempos[count]!=-1)
		count++;
	p->maxT=count-1;	
	p->current=1; //SALTA VALOR DE INICIALIZAÇÃO (índice 0)
	return p;
}

void terminate(struct PCB * p []){ //Termina
	printf("\n<%f> \n<%f> \n<%f> \n",(float)p[0]->t_wait/(float)p[0]->t_cpu_somado,
					(float)p[1]->t_wait/(float)p[1]->t_cpu_somado,
					(float)p[2]->t_wait/(float)p[2]->t_cpu_somado);
	printf("Processos terminados.\n");
	exit(1);
}

void printProcesses(struct PCB * p [], int nproc){ //imprime output
	int i;
	for(i=0; i<nproc; i++){
		printf("<%c> ", p[i]->estado);					
		}
	puts("\n");
	}

void run(){ //executa um burst de CPU (1 unidade)
	if(isEmpty(running))
		return;
	struct PCB *temp = getFirst(running);
	
	
	temp->t_cpu++;
	temp->t_cpu_total++;
	temp->t_cpu_somado++;

}
void scheduler(){ 
	if(isEmpty(running))
		return;
	struct PCB *temp = getFirst(running);
	if(temp->t_cpu_total==temp->tempos[temp->current])
		//processo já terminou o CPU actual
		if(temp->current==temp->maxT){
			//sendo o último, termina
			runningToExit();
			temp->t_cpu=0;
			temp->t_cpu_total=0;
			if(!isEmpty(ready))
				readyToRunning();
			return;}
		else{	
			//não sendo, passa a blocked aguardando evento
			temp->current++;
			runningToBlocked();
			temp->t_cpu=0;
			temp->t_cpu_total=0;
			if(!isEmpty(ready))
				readyToRunning();
			return;
			}
	if(temp->t_cpu%4==0&&temp->t_cpu!=0){
			//verifica quantum
			temp->t_cpu=0;
			if(!isEmpty(running))
				runningToReady();
			if(!isEmpty(ready))
				readyToRunning();
			return;
			}
}

void disp(struct PCB * p, int disp){
	p->timeInDisp++;
	p->t_wait++;
	//avança tempo nos dispositivos
	if(disp==1)
		if(p->timeInDisp==d1->tdisp){
			p->timeInDisp=0;
			blockedToReady(1);}
	
	if(disp==2)
		if(p->timeInDisp==d2->tdisp){
			p->timeInDisp=0;
			blockedToReady(2);}

	if(disp==3)
		if(p->timeInDisp==d3->tdisp){
			p->timeInDisp=0;
			blockedToReady(3);}
}

void mainCycle(int nprocessos, int * processos []){
	int i=0;
	int pID=0; //incrementa-se para garantir pids únicos
	struct PCB * prcPrint [nprocessos]; //para efeitos de print
	prcPrint[0]->estado='x'; //default para não inicializado (ainda não entrou na fila new)
	prcPrint[1]->estado='x';
	prcPrint[2]->estado='x';
	while(exitU->size < nprocessos && i<80){
		


		int j = 0;
		for(j=0; j<nprocessos; j++)
			if(processos[j][0]==i){
				//se o tempo actual do ciclo fôr o de inicialização do processo, passa-o para a fila de new
				prcPrint[j]=newProcess(pID, processos[j]);
				insertFIFO(prcPrint[j],new, -1);
				pID++;}
		
		i++;
/*REVER*/
		
		
		/*1 - filas de dispositivos para ready*/
		if(!isEmpty(d1)){
			disp(getFirst(d1), 1);}
		if(!isEmpty(d2))
			disp(getFirst(d2), 2);
		if(!isEmpty(d3))
			disp(getFirst(d3), 3);

		/*2 - fila de new para ready*/
		if(!isEmpty(new))
			newToReady();

		/*3 - fila de CPU para ready*/
		if(isEmpty(running))
			if(!isEmpty(ready))
				readyToRunning();

		
		//verifica o processo em running
		scheduler();
		printf("<%d> ",i-1);
		int k=0;
		for(k=0; k<nprocessos; k++){
			printf("<%c> ", prcPrint[k]->estado);					
		}
		puts("\n");
		//executa 1 unidade de tempo no CPU
		run();
		//termina se exit tem todos os processos		
		if(size(exitU)==nprocessos) {
			terminate(prcPrint);
			}
	
		
	}
}

int * toArrayInt(char * values, int * convertedR ){
	int count=0; //contar posição actual da string
	int nints=0; //contar inteiros convertidos
	convertedR=malloc(sizeof(int)*strlen(values));
	char * t = malloc(sizeof(char)*3); //temporário
	while(count<strlen(values)){
		int t2=0;
		int i=0;
		while( values[count]!=' '&&values[count]!='\0'){
			t[i]=values[count];
			count++;} //lê string
		count++; //salta espaço
		convertedR[nints]=atoi(t); //converte
		nints++;
		t=malloc(sizeof(char)*3); //realoca temporário
		}
	convertedR[nints]=-1; //termina os convertidos com -1 para indicar o fim do array
	return convertedR;
		

}

main(int argc, char * argv[]){
	//garante input certo
	if(argc<=1){
		printf("Erro de chamada da função.");
		exit(1);}
	//leitura do input geral
	int nprocessos;
	int ndispositivos;
	int temp=0;
	int * input;

	if(argc>=2){
		//transformar string input em valores
		
		input=toArrayInt(argv[1], input);
		nprocessos=input[0];
		ndispositivos=input[1];
	}

	//garante veracidade do input
	if(argc!=(2+nprocessos)){
		
		printf("Falha: nprocessos não corresponde ao número de processos dos argumentos.");
		exit(1);	
	
	}

	//inicializa as filas (vazias)
	new=malloc(sizeof(struct fila));
	ready=malloc(sizeof(struct fila));
	running=malloc(sizeof(struct fila));
	exitU=malloc(sizeof(struct fila));
	d1=malloc(sizeof(struct fila));
	d2=malloc(sizeof(struct fila));
	d3=malloc(sizeof(struct fila));

	initUSER(new);
	initUSER(ready);
	initUSER(running);
	initUSER(d1);
	initUSER(d2);
	initUSER(d3);
	initUSER(exitU);

	d1->tdisp=input[2];
	d2->tdisp=input[3];
	d3->tdisp=input[4];	//tempos dos dispositivos

//ler inputs seguintes, dados por processo.

	int currentProcess=0;
	int * tempos [nprocessos];
	while(currentProcess<nprocessos){
		int i;
		int * te=toArrayInt(argv[2+currentProcess],te);
		
		tempos[currentProcess]=te;
		currentProcess++;
		}


/*executa os processos*/
	mainCycle(nprocessos,tempos);
}


