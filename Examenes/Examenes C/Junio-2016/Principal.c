/*
 * Principal.c
 *
 *  Created on: 14/6/2016
 *      Author: esc
 */

#include "ListaJugadores.h"
#include <stdio.h>
#include <stdlib.h>



// Lee el fichero y lo introduce en la lista
void cargarFichero (char * nombreFich, TListaJugadores *lj)
{
	FILE * ptr_fichero = fopen(nombreFich,"rb");
	if(ptr_fichero == NULL){
		printf("No existe dicho archivo");
		exit(-1);
	}else{
		unsigned int id[3];
		while(fread(&id,sizeof(unsigned int),3,ptr_fichero) == 3){
			insertar(&(*lj),id[1]);
		}
		fclose(ptr_fichero);
	}
}


int main(){

	TListaJugadores lj;
	crear(&lj);
    unsigned int num_goles;
	cargarFichero ("goles.bin",&lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	fflush(stdout);

	recorrer(lj);
	fflush(stdout);
	printf("Introduce un número de goles: \n");
	fflush(stdout);
	scanf("%d",&num_goles);


	eliminar(&lj,num_goles);
	printf("--------------------------------------\n");
	recorrer(lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	fflush(stdout);

	printf ("El jugador que más goles ha marcado es el que tiene ID: %d",maximo(lj));
	fflush(stdout);
	destruir (&lj);

	return 0;
}
