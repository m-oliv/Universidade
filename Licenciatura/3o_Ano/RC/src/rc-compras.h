typedef struct LOJA;
typedef struct PRODUTO;

// Estrutura que irá armazenar os dados relativos a cada produto.

struct PRODUTO{
	int ref;
	int comp_nome;
	char nome[40];
	int preco;
};

// Estrutura que irá armazenar os dados reltivos a cada loja.

struct LOJA{
	int comp_nome;
	char nome[40]; 
	int compIP;
	char endIP[16]; 
	int numPorta;
	int numProds;
	struct PRODUTO prod[100];
};

void printInfoProd(int numProd,struct PRODUTO *p); // Esta função escreve no output os dados relativos aos produtos.

void printInfoLoja(int numLojas,struct LOJA *l); // Esta função escreve no output os dados relativos às lojas.

int checkLoja(int escolha,int numLojas, struct Loja *l);

void inicioLigacao();

