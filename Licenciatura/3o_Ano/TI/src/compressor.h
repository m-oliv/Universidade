#ifndef AUT_H_GUARD
#define AUT_H_GUARD

int _numeroBlocos;
int _blocos;

int* pesqDic(char *valor, char dicionario[_blocos][_numeroBlocos]);
void comprime(char *formato, int fsize,char *imagem, int numCarImg,FILE *fw,FILE *dic);
void fileOperations(char * filename);

#endif
