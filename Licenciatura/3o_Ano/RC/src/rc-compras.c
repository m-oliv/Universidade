#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "rc-net-lib.h"
#include "rc-compras.h"
#include <ctype.h> // para a funcao isdigit (indica se é numero ou não)

//Variáveis
char endereco[] = "193.137.179.82";
int porta = 9020;

char id[6]; //Armazenar o ID de cliente

int i,j,p; // variaveis dos for's

int s,c,x; //descritores

char opcao[2]; // para o Inicio da Sessao

void printInfoProd(int numProd,struct PRODUTO *p){ // Esta função escreve no output os dados relativos aos produtos. 
int i = 0;

	for(i=0;i<numProd;i++){
		printf("Referência do Produto: %d \n",p[i].ref);
		printf("Nome do Produto: %s \n",p[i].nome);
		printf("Preço do Produto: %d \n",p[i].preco);
		printf("\n");
	}
}
		
void printInfoLoja(int numLojas,struct LOJA *l){// Esta função escreve no output os dados relativos às lojas.
int j = 0;

	for(j=0;j<numLojas;j++){
		printf("Loja Número: %d \n",j+1);
		printf("Nome da Loja: %s \n",l[j].nome);
		printf("Endereço de IP: %s \n",l[j].endIP);
		printf("Porta: %d \n",l[j].numPorta);
		printf("\n");
	}
}

int checkLoja(int escolha,int numLojas, struct LOJA *l){ // Verifica se a loja escolhida pelo utilizador é uma das lojas activas.

int j = 0;	

	for(j=0;j<numLojas;j++){
		if(escolha == j+1){
			return 1;
		}
	}
	return 0;
}
	
void inicioLigacao(){
	puts("Deseja iniciar sessão? S=Sim/N=Não");
	scanf("%s", opcao);
	
	if(opcao[0] == 's'){
		puts("Insira a sua identificação:");
		scanf("%s", id);
	// O Cliente liga-se à Central utilizando o endereço e a porta predefinidos. Nesta ligação é utilizado o protocolo TCP.
		s = rc_connect(endereco,porta,TCP);
		if(s==-1){
			printf("Erro de ligação! \n");
		}
		printf("Ligação estabelecida com sucesso! \n");
		// O Cliente envia a identificação do utilizador à Central.
		c = rc_send(s,id,strlen(id)*sizeof(char));
	
		if(c==-1){
			printf("Ocorreu um erro no envio da identificação! \n");	
		}
		printf("Identificação enviada com sucesso! \n");
	}
	else {
		puts("Deseja sair da aplicação? S=Sim/N=Não");
		scanf("%s", opcao);
		if(opcao[0] == 's'){
			exit(0);
		}
		else inicioLigacao();
	}
}	

main(){

	inicioLigacao();
	
	// O Cliente recebe o número de lojas activas enviadas pela Central.
	int size_l; // Inteiro que irá armazenar os dados recebidos.

	c = rc_recv(s,&size_l,sizeof(int));
	
	if(c==-1){
		printf("Não foi recebido o número de lojas activas! \n");
	}
	
	int l_conv = ntohl(size_l); // O número de lojas activas é convertido de um inteiro em network byte order para um inteiro em host byte order.
	
	printf("Número de lojas activas: %d \n \n",l_conv);

	struct LOJA l[l_conv]; 	// Array que irá conter a informação relativa às várias lojas activas.

	// A informação relativa a cada loja que será recebida é armazenada no array l. 

	for(i=0;i<l_conv;i++){
		
		// O Cliente recebe o comprimento do nome da loja.
		
		int size_n; // Inteiro que irá armazenar os dados recebidos.

		c = rc_recv(s,&size_n,sizeof(int));

		if(c==-1){
			printf("Não foi recebido o comprimento do nome da loja! \n");
		}

		int sn_conv = ntohl(size_n); // O comprimento do nome da loja é convertido de um inteiro em network byte order para um inteiro em host byte order.
	
		l[i].comp_nome = sn_conv; // O comprimento do nome da loja é armazenado no inteiro criado para o efeito na estrutura que representa a loja.
	
		char ntemp[sn_conv]; // Array temporário que irá armazenar os dados recebidos.

		// O Cliente recebe o nome da loja.

		c = rc_recv(s,ntemp,sn_conv*sizeof(char));

		if(c == -1){
			printf("Erro ao receber o nome da loja! \n");
		}
		
		// O nome da loja é armazenado no array criado para o efeito na estrutura que representa a loja.
		
		for(j=0;j<sn_conv;j++){
			l[i].nome[j] = ntemp[j];
		}

		l[i].nome[sn_conv] = '\0';// Coloca o caractere nulo no final da string que contém o nome da loja.

		// O Cliente recebe o comprimento do endereço de IP da loja.
		int ips; // Inteiro que irá armazenar os dados recebidos.

		c = rc_recv(s,&ips,sizeof(int));
		
		if(c==-1){
			printf("Não foi recebido o comprimento do endereço de IP da loja! \n");
		}

		int ips_conv = ntohl(ips); // O comprimento do endereço de IP da loja é convertido de um inteiro em network byte order para um inteiro em host byte order.

		l[i].compIP = ips_conv; // O comprimento do endereço de IP da loja é armazenado no inteiro criado para o efeito na estrutura que representa a loja.

		char iptemp[ips_conv]; // Array temporário que irá armazenar os dados recebidos.

		// O Cliente recebe o endereço de IP da loja.

		c = rc_recv(s,iptemp,ips_conv*sizeof(char));
		
		if(c == -1){
			printf("Erro ao receber o endereço de IP da loja! \n");
		}
		
		// O endereço de IP da loja é armazenado no array criado para o efeito na estrutura que representa a loja.
		
		for(j=0;j<ips_conv;j++){
			l[i].endIP[j] = iptemp[j];
		}

		l[i].endIP[ips_conv] = '\0'; // Coloca o caractere nulo no final da string que contém o endereço de IP da loja.
		
		// O Cliente recebe a o número da porta da loja.

		int plj; // Inteiro que irá armazenar os dados recebidos.

		c = rc_recv(s,&plj,sizeof(int));
		
		if(c == -1){
			printf("Erro ao receber o número da porta da loja! \n");
		}

		int plj_conv = ntohl(plj); // O número da porta da loja é convertido de um inteiro em network byte order para um inteiro em host byte order.
		
		l[i].numPorta = plj_conv; // O número da porta da loja é armazenado no inteiro criado para o efeito na estrutura que representa a loja.
	}

	printInfoLoja(l_conv,l); // O Cliente imprime a informação relativa às lojas activas.

	char *endloja; // Temporário que armazena o nome da loja a que o Cliente se irá ligar.
	int portaloja; // Temporário que armazena o número da porta da loja a que o Cliente se irá ligar.
	int escolha; // Inteiro correspondente à escolha efectuada pelo utilizador.
	int k = 0; // Inteiro temporário que actua como auxiliar na verificação seguinte.

	while(k == 0){ //Se o utilizador inserir uma escolha inválida, o Cliente volta a pedir ao utilizador para escolher uma nova loja.
		
			printf("Por favor escolha a loja onde pretende efectuar as suas compras: \n");
			scanf("%d",&escolha);
			if(checkLoja(escolha, l_conv,l) == 1){
				printf("Loja escolhida: %d \n",escolha);
				k = checkLoja(escolha, l_conv,l);
			}
			else{
				 k == 0;
				 printf("Insira uma loja válida!\n");
			 }
	}

	endloja = l[escolha-1].endIP; // O Cliente coloca o endereço de IP da loja escolhida numa variável temporária.
	portaloja = l[escolha-1].numPorta; // O Cliente coloca o número da porta da loja escolhida numa variável temporária.

	// O Cliente estabelece uma ligação com a loja utilizando o protocolo TCP.
	
	x = rc_connect(endloja,portaloja,TCP);
	
	if(x==-1){
		printf("Ocorreu um erro durante a ligação à loja! \n");
	}

	printf("Ligação estabelecida com sucesso! \n");

	// O Cliente envia à loja a informação do utilizador.

	c = rc_send(x,id,strlen(id)*sizeof(char));

	if(c == -1){
		printf("Ocorreu um erro no envio da identificação! \n");	
	}
	
	printf("Envio da indentificação do utilizador bem sucedido! \n");

	// O Cliente recebe o número de produtos disponíveis na loja.

	int size_p; // Inteiro que irá armazenar os dados recebidos.	

	c = rc_recv(x,&size_p,sizeof(int));

	if(c == -1){
		printf("Erro ao receber o número de produtos! \n");
	}

	int p_conv = ntohl(size_p);// O número de produtos disponíveis na loja é convertido de um inteiro em network byte order para um inteiro em host byte order.

	printf("Número de produtos: %d \n \n",p_conv);
	
	l[escolha-1].numProds = p_conv; // O número de produtos disponíveis na loja é armazenado no inteiro criado para o efeito na estrutura que representa a loja.

	// A informação relativa a produto que será recebida é armazenada no array que irá conter a informação dos produtos de uma loja.

	for(i=0;i<p_conv;i++){ 

		// O Cliente recebe a referência de um produto.

		int r; // Inteiro que irá armazenar os dados recebidos.		

		c = rc_recv(x,&r,sizeof(int));
			
		if(c == -1){
			printf("Erro ao receber a referência do produto! \n");
		}
		
		int r_conv = ntohl(r); // A referência do produto é convertida de um inteiro em network byte order para um inteiro em host byte order.
		
		l[escolha-1].prod[i].ref = r_conv; // A referência do produto é armazenada no inteiro criado para o efeito na estrutura que representa o produto.
		
		// O Cliente recebe o comprimento do comprimento do nome do produto.

		int nps; // Inteiro que irá armazenar os dados recebidos.

		c = rc_recv(x,&nps,sizeof(int));
		
		if(c == -1){
			printf("Erro ao receber o comprimento do nome do produto! \n");
		}
		
		int nps_conv = ntohl(nps); // O comprimento do nome do produto é convertido de um inteiro em network byte order para um inteiro em host byte order.

		l[escolha-1].prod[i].comp_nome = nps_conv; // O comprimento do nome do produto é armazenado no inteiro criado para o efeito na estrutura que representa o produto.

		char nptemp[nps_conv]; // Array temporário que irá armazenar os dados recebidos.

		// O Cliente recebe o nome do produto.
		
		c = rc_recv(x,nptemp,nps_conv*sizeof(char));

		if(c == -1){
			printf("Erro ao receber o nome do produto! \n");
		}

		// O nome do produto é armazenado no array criado para o efeito na estrutura que representa o produto.
		
		for(j=0;j<nps_conv;j++){
			l[escolha-1].prod[i].nome[j] = nptemp[j];
		}	

		l[escolha-1].prod[i].nome[nps_conv] ='\0';// Coloca o caractere nulo no final da string que contém o nome do produto.
		
		// O Cliente recebe o preço do produto.

		int pr; //Inteiro que irá armazenar os dados recebidos.
		c = rc_recv(x,&pr,sizeof(int));
		
		if(c == -1){
			printf("Erro ao receber o preço do produto! \n");
		}

		int pr_conv = ntohl(pr);// O preço do produto é convertido de um inteiro em network byte order para um inteiro em host byte order.

		l[escolha-1].prod[i].preco = pr_conv;// O preço do produto é armazenado no inteiro criado para o efeito na estrutura que representa o produto.

	}

	printInfoProd(p_conv,l[escolha-1].prod); // O Cliente imprime a informação relativa aos produtos disponíveis na loja.
	
	
	char stop; // Temporário que irá armazenar o char '=' quando o utilizador decidir finalizar a compra.

	int refc_conv,qtd, qtd_conv,mont,mont_conv,refc_temp; // Temporários auxiliares para o ciclo seguinte.

	while(stop != '='){ //verificar funcionamento das vers extra!
		
		printf("Insira a referência do produto que deseja comprar: \n");
		scanf("%d",&refc_temp);		
		
		printf("Para desistir do produto insira '0'. \n");
		printf("Insira a quantidade: \n");
		scanf("%d",&qtd);
		
		refc_conv = htonl(refc_temp); // O preço do produto é convertido de um inteiro em host byte order para um inteiro em network byte order.

		// O Cliente envia a referência inserida para a loja.
		
		c = rc_send(x,&refc_conv,sizeof(int));
		
		if(c== -1){
			printf("Ocorreu um erro durante o envio da referência! \n");
		}
		
		qtd_conv = htonl(qtd);// A quantidade é convertida de um inteiro em host byte order para um inteiro em network byte order.

		// O Cliente envia a quantidade à loja.
		
		c = rc_send(x,&qtd_conv,sizeof(int));
		
		if(c== -1){
			printf("Ocorreu um erro durante o envio da quantidade! \n");
		}

		// O Cliente recebe o montante actual da encomenda.
		
		c = rc_recv(x,&mont,sizeof(int));
		
		if(c== -1){
			printf("Erro ao receber o montante da encomenda! \n");
		}
		
		char consult; // temporário
		
		mont_conv = ntohl(mont);// O montante actual da encomenda é convertido de um inteiro em network byte order para um inteiro em host byte order.
		
		char as = getchar(); // Auxiliar
		
		printf("Montante Actual: %d \n \n",mont_conv);
		printf("Deseja voltara consultar a lista de produtos disponíveis? \n");
		printf("Sim - ? ; Não - enter \n");
		scanf("%c",&consult);
		
		// Verifica se foi inserido um ?. Se isto se verificar, o Cliente volta a imprimir a lista de produtos disponíveis.
		
		if(consult == '?'){
			printInfoProd(p_conv,l[escolha-1].prod);
		}
		
		// Verifica se foi inserido um =. Se isto se verificar, o Cliente termina a compra.
		
		printf("Deseja Concluir a compra? \n");
		printf("Sim - = ; Não - enter \n");
		scanf("%c",&stop);
		
		
	}

	// O Cliente envia à loja o inteiro que indica se o utilizador pretende ou não concluir a encomenda.
	
	c = rc_send(x,&stop,sizeof(char));

	if(c==-1){
		printf("Ocorreu um erro ao concluir a compra! \n");
	}

	// Se o valor da encomenda for igual a zero quando o utilizador concluir a encomenda, a ligação é terminada.
	
	if(mont_conv ==0){
		printf("Ligação terminada! \n");
	}

	// Se o valor da encomenda for diferente de zero quando o utilizador concluir a encomenda, o Cliente irá receber da loja os dados relativos ao banco e o código a enviar ao mesmo.
	
	if(mont_conv !=0){
		
		// O Cliente recebe o comprimento do endereço de IP do banco.
		int compipb; // Inteiro que irá armazenar os dados recebidos.

		c = rc_recv(x,&compipb,sizeof(int));
	
		if(c == -1){
			printf("Ocorreu um erro ao receber o comprimento do endereço de IP do banco! \n");
		}

		int compipb_conv = ntohl(compipb); // O comprimento do endereço de IP do banco é convertido de um inteiro em network byte order para um inteiro em host byte order.

		char ipbanco[compipb_conv]; // Array que irá armazenar o endereço de IP do banco.
		
		//O Cliente recebe da loja o endereço de IP do banco.
	
		c = rc_recv(x,ipbanco,compipb_conv*sizeof(char));
		
		if(c == -1){
			printf("Ocorreu um erro ao receber o endereço de IP do banco! \n");
		}
		
		ipbanco[compipb_conv]='\0';// Coloca o caractere nulo no final da string que contém o endereço de IP do banco.
		
		// O Cliente recebe o número da porta do banco.
		
		int port; // Inteiro que irá armazenar os dados recebidos.

		c = rc_recv(x,&port,sizeof(int));
	
		if(c == -1){
			printf("Ocorreu um erro ao receber a porta do banco! \n");
		}

		int port_conv = ntohl(port);// O número da porta do banco é convertido de um inteiro em network byte order para um inteiro em host byte order.

		char codloj[128]; // Array que irá conter o código enviado pela loja.

		// O Cliente recebe o código. O valor retornado pela função rc_recv irá corresponder ao número de bytes recebidos, o que neste caso corresponde ao tamanho do código.
		
		int cod = rc_recv(x,codloj,sizeof(char)*128);

		if(cod==-1){
		printf("Erro ao receber o codigo! \n");
	}
		int size_c = cod; // Inteiro correspondente ao tamanho do código.
		
		codloj[size_c] = '\0';// Coloca o caractere nulo no final da string que contém o código a ser enviado ao banco.

		// O Cliente estabelece uma ligação com o banco usando o protocolo UDP.

		int dec= rc_connect(ipbanco,port_conv,UDP);

		if(dec==-1){
			printf("Erro ao estabelecer a ligação com o banco! \n");
		}

		c = rc_send(dec,codloj,sizeof(char)*128); // WARNING: se der porcaria o erro ta aqui!

		if(c==-1){
			printf("Ocorreu um erro ao enviar o código ao banco! \n");
		}	

		char codbanco[128]; // String que irá conter o código que será enviado ao banco.

		// O Cliente recebe a resposta do banco.O valor retornado pela função rc_recv irá corresponder ao número de bytes recebidos, o que neste caso corresponde ao tamanho do código.
		
		int cc = rc_recv(dec,codbanco,sizeof(int)*128);

		if(cc==-1){
			printf("Ocorreu um erro ao receber a resposta do banco! \n");
		}
	
		if(cc==0){
			printf("O código enviado ao banco é inválido! \n");
		}
		
		codbanco[cc]='\0'; // Coloca o caractere nulo no final da string que contém o código que irá ser enviado à loja.
		
		// O Cliente envia para a loja o código recebido do banco. 
		
		c = rc_send(x,codbanco,sizeof(char)*cc);
	
		if(c==-1){
			printf("Ocorreu um erro ao enviar a resposta do banco! \n");
		}
		
		char conf[3]; // String que irá conter a resposta enviada pela loja.

		c = rc_recv(x,&conf,sizeof(char)*3);
	
		conf[3]='\0'; // Coloca o caractere nulo no final da string que contém a resposta enviada pela loja.
		
		if(c==-1){
			printf("Ocorreu um erro ao receber a resposta da loja! \n");
		}	

		// O Cliente verifica qual foi a resposta enviada pela loja.
		
		if(conf[0] == 'O' && conf[1] == 'K'){
			printf("Resposta da Loja: %s \n",conf);
			printf("Transacção Autorizada! \n");
		}

		if(conf[0] == 'K' && conf[1] =='O'){
			printf("Resposta da Loja: %s \n",conf);
			printf("Transacção Não Autorizada! \n");
		}
			
		if(conf[0] == 'X' && conf[1]=='X'){
			printf("Resposta da Loja: %s \n",conf);
			printf("Ocorreu um erro! \n");
		}
		
		printf("Ligação Terminada! \n");
		
	}
}
