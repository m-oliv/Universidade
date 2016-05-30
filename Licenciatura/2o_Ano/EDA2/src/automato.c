#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "automato.h"


int calc_est(char *au, char comp, int estado){
	if(au[estado]==comp)
		return estado+1;
	char *car1=malloc((estado+1)*sizeof(char));
	strncpy(car1,au, estado);
	car1[estado]=comp;
	int i;
	for(i=estado-1; i>=0;i--){
	char *car2=malloc(i*sizeof(char));
	strncpy(car2,au,i);
	car1+=1;
	if(strcmp(car2,car1)){
		return i;
	}
	}
	return 0;
		
}

int* aut(char *texto, char *busca){
	int tam_texto = strlen(texto);
	int estado_acei= strlen(busca);
	int estado=0;
	int *tab_est=malloc(tam_texto*sizeof(int));
	int i;
	
	for(i=0; i<tam_texto;i++){
		if((texto[i])==NULL)
			break;
		tab_est[i]=calc_est(busca,texto[i],estado);
		estado=tab_est[i];
		if(estado==estado_acei)
			estado=0;
		}
	return tab_est;
}

