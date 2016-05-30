typedef PCB;
typedef fila;

struct PCB{
	
	int pid; //id do processo
	int pc;
	char estado; //inicial do estado R-Ready, U - Running (porque Ready tem a mesma inicial),...
	int t_wait; //tempo em espera
	int t_cpu; //tempo do processo em CPU até ser interrompido por quantum.
	int t_cpu_total; //tempo do processo em CPU desse tempo de CPU, contando todos os quantums.
	int t_cpu_somado; //tempo do processo em todas as utilizações do CPU
	int current; //índice dos tempos que indica qual está a ser processado actualmente, seja de CPU ou num dispositivo.
	int timeInDisp; //tempo decorrido num dispositivo
	int instI; //instante de entrada para new
	int * tempos; //tempos de execução segundo o input
	int maxT; //número de valores no array de tempos
	
};

struct fila{
	
	struct PCB *p;
	struct fila *next;
	int size;
	int tdisp;
};
