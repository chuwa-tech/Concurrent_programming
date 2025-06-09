/*
 ============================================================================
 Name        : Practica1.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "gestion_memoria.h"

int main(void) {


	T_Manejador manej;
	unsigned ok;
	unsigned dir;


	crear(&manej);
	assert(manej!=NULL);
	assert(manej->inicio==0);
	assert(manej->fin==MAX-1);
	assert(manej->sig==NULL);

	mostrar(manej);

	
	//Se solicita memoria para alojar *tamanyo_pedir*
	obtener(&manej,500,&dir,&ok); 

	if (ok) {
		//Se ha podido pedir memoria, deberíamos tener dos trozos.
		assert(manej!=NULL);
		assert(manej->inicio==500);
		assert(manej->fin==MAX-1);
		assert(manej->sig==NULL);
		mostrar(manej);
		printf("la direccion de comienzo es: %d\n", dir);
	} else {
		//No se ha podido pedir memoria, se debería quedar como estaba.
		assert(manej!=NULL);
		assert(manej->inicio==0);
		assert(manej->fin==MAX-1);
		assert(manej->sig==NULL);
		
		printf("No es posible obtener esa memoria\n");
		return 0;
	}

	//Se ha enviado parte de la foto. Ya se puede dejar libre esa memoria
	//Esta debería estar al principio
	devolver(&manej, 200,0); 
	assert(manej!=NULL);
	assert(manej->inicio==0);
	assert(manej->fin==200-1);
	assert(manej->sig!=NULL);
	assert(manej->sig->inicio==500);
	assert(manej->sig->fin==MAX-1);
	assert(manej->sig->sig==NULL);

	mostrar (manej);


	// Se ha hecho otra foto 
	//Se reservará donde entre, que en el primer hueco no es...
	obtener(&manej,250,&dir,&ok); 
	if (ok) {
		assert(manej!=NULL);
		assert(manej->inicio==0);
		assert(manej->fin==200-1);
		assert(manej->sig!=NULL);
		assert(manej->sig->inicio==750);
		assert(manej->sig->fin==MAX-1);
		assert(manej->sig->sig==NULL);

		mostrar(manej);
		printf("la direccion de comienzo es: %d\n", dir);
	} else {
		assert(manej!=NULL);
		assert(manej->inicio==0);
		assert(manej->fin==200-1);
		assert(manej->sig!=NULL);
		assert(manej->sig->inicio==500);
		assert(manej->sig->fin==MAX-1);
		assert(manej->sig->sig==NULL);
		printf("No es posible obtener esa memoria\n");
		return 0;
	}

	// Se ha enviado parte de la foto. Ya se puede liberar esa memoria 
	// Liberar en la posición 500, 100 unidades
	devolver(&manej,100,500); 

	assert(manej!=NULL);
	assert(manej->inicio==0);
	assert(manej->fin==200-1);
	assert(manej->sig!=NULL);
	assert(manej->sig->inicio==500);
	assert(manej->sig->fin==500+100-1);
	assert(manej->sig->sig!=NULL);
	assert(manej->sig->sig->inicio==750);
	assert(manej->sig->sig->fin==MAX-1);
	assert(manej->sig->sig->sig==NULL);

	mostrar(manej);

	// Otra foto realizada, se necesitan 250 unidades de memoria.
 	obtener(&manej,250,&dir,&ok);
	if (ok) {
		assert(manej!=NULL);
		assert(manej->inicio==0);
		assert(manej->fin==200-1);
		assert(manej->sig!=NULL);
		assert(manej->sig->inicio==500);
		assert(manej->sig->fin==500+100-1);
		assert(manej->sig->sig==NULL);
		mostrar(manej);
		printf("la direccion de comienzo es: %d\n", dir);
	} else {
		assert(manej!=NULL);
		assert(manej->inicio==0);
		assert(manej->fin==200-1);
		assert(manej->sig!=NULL);
		assert(manej->sig->inicio==500);
		assert(manej->sig->fin==500+100-1);
		assert(manej->sig->sig!=NULL);
		assert(manej->sig->sig->inicio==750);
		assert(manej->sig->sig->fin==MAX-1);
		assert(manej->sig->sig->sig==NULL);
		printf("No es posible obtener esa memoria\n");
		return 0;
	}

	// Se ha enviado parte de la foto. Ya se puede liberar esa memoria 
	// Liberar en la posición 750, 200 unidades
	devolver(&manej, 200,750); 
	mostrar(manej);

	assert(manej!=NULL);
	assert(manej->inicio==0);
	assert(manej->fin==200-1);
	assert(manej->sig!=NULL);
	assert(manej->sig->inicio==500);
	assert(manej->sig->fin==500+100-1);
	assert(manej->sig->sig!=NULL);
	assert(manej->sig->sig->inicio==750);
	assert(manej->sig->sig->fin==750+200-1);
	assert(manej->sig->sig->sig==NULL);

	//Para asegurar que se destruye bien vamos a guardar algun puntero
	T_Manejador manej_testing;
	manej_testing = manej->sig;

	destruir(&manej);
	assert(manej==NULL);

	mostrar (manej);

	printf("Fin Programa\n");

	return EXIT_SUCCESS;
}
