#ifndef INDEXSTORY_H_GUARD
#define INDEXSTORY_H_GUARD

typedef struct Historia historia;


struct Historia{

	char * conclusoes;
	char * personagens;
	char * titulo;
	char * texto;
	char * fileName;

};


int calcsize(FILE *f, int ignoreNewLines);
void getString(FILE *f, char *s, int size);
int file_ops(char *st, struct Historia *h);
struct Historia *novaHistoria(struct Historia *h);
void searchPersonagem(struct Historia *basededados, char *nome);
void searchConclu(struct Historia *basededados, char *palavra);
void searchTexto(struct Historia *basededados, char *palavra);

#endif
