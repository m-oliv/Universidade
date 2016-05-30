typedef PCB;
typedef fila;

struct PCB{
	
	int pid; //id do processo
	int pc;
	char estado; //inicial do estado R-Ready, U - Running (porque Ready tem a mesma inicial),...
	int t_wait; //tempo em espera
	int t_cpu; //tempo do processo em CPU at� ser interrompido por quantum.
	int t_cpu_total; //tempo do processo em CPU desse tempo de CPU, contando todos os quantums.
	int t_cpu_somado; //tempo do processo em todas as utiliza��es do CPU
	int current; //�ndice dos tempos que indica qual est� a ser processado actualmente, seja de CPU ou num dispositivo.
	int timeInDisp; //tempo decorrido num dispositivo
	int instI; //instante de entrada para new
	int * tempos; //tempos de execu��o segundo o input
	int maxT; //n�mero de valores no array de tempos
	
};

struct fila{
	
	struct PCB *p;
	struct fila *next;
	int size;
	int tdisp;
};
