#ifndef BTREE_H_GUARD
#define BTREE_H_GUARD


#define ordem 11
#define maxfilhos 2*ordem
#define maxchaves 2*ordem-1

typedef struct Node node;

typedef struct BTree btree;

struct Node {
	char * value;
	int frequencia;

};

struct BTree {
	int folha;
  	int n;
	struct Node *chaves[maxchaves];
	struct BTree *filho[maxfilhos];
};

void novoNo(struct Node **n);
void nova(struct BTree **t);
int compareString(char *left, char *right);
void split(struct BTree *parent, int pos, struct BTree *fullChild);
void insertNotFullLeaf(struct BTree *b, char * string);
void insertNotFullNonLeaf(struct BTree *b, char * string);
void listar(struct BTree *b, int apenasRoot);
struct BTree *inserir(struct BTree *b, char * string);


#endif
