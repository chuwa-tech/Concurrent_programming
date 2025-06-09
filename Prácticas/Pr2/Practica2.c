/*
 ============================================================================
 Name        : Practica2B.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : 
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <errno.h>
#include "arbolbb.h"

/**
 * Recibe un número "tam" del usuario, y
 * crea un fichero binario para escritura con el nombre "nfichero"
 * en que escribe "tam" numeros (unsigned int) aleatorios
 * Se utiliza srand(time(NULL)) para establecer la semilla (de la libreria time.h)
 * y rand()%100 para crear un n�mero aleatorio entre 0 y 99.
 */
void creafichero_tam_random(char* nfichero, unsigned tam)
{
	srand(time(NULL));
	unsigned array_random[tam];

	for(int i = 0; i < tam; i++){
		array_random[i] = rand()%tam;
	}

	FILE * ptr_file = fopen(nfichero,"wb");
	if(ptr_file == NULL){
		perror("Fichero creado incorrecto");
	}else{
		fwrite(array_random,sizeof(unsigned), tam, ptr_file);
		fclose(ptr_file);
	}
	//rand()%tam;	
}
/**
 * Muestra por pantalla la lista de números (unsigned int) almacenada
 * en el fichero binario "nfichero"
 */
void muestrafichero(char* nfichero)
{
	FILE * ptr_file = fopen(nfichero,"rb");
	if(ptr_file == NULL){
		perror("Error al abrir el fichero");
	}else{
		unsigned basura;
		while(!feof(ptr_file)){
			fread(&basura, sizeof(unsigned),1,ptr_file);
			printf("%i ",basura);
		}
		printf("\n");
		fclose(ptr_file);
	}
}

/**
 * Guarda en el arbol "*miarbol" los números almacenados en el fichero binario "nfichero"
 */

void cargaFichero(char* nfichero, T_Arbol* miarbol)
{
	FILE * ptr_file = fopen(nfichero,"rb");
	if(ptr_file == NULL){
		perror("Error al abrir el fichero");
		exit(-1);
	}else{
		unsigned basura;
		while(!feof(ptr_file)){
			fread(&basura, sizeof(unsigned),1,ptr_file);
			Insertar(miarbol,basura);
		}
		printf("\n");
		fclose(ptr_file);
	}
}

int main(void) {
	char nfichero[50];
	unsigned num_random;

	printf ("Introduce el nombre del fichero binario:\n");
	scanf ("%s",nfichero);

	printf ("Introduce el número de elementos a crear:\n");
	scanf ("%i",&num_random);

	creafichero_tam_random(nfichero, num_random);

	printf("\nAhora lo leemos y mostramos\n");
	muestrafichero(nfichero);

	printf ("\nAhora lo cargamos en el arbol\n");
	T_Arbol miarbol;
	Crear (&miarbol);
	cargaFichero(nfichero,&miarbol);

	printf ("\nY lo mostramos ordenado\n");
	Mostrar(miarbol);
	

	printf("\nAhora lo guardamos ordenado\n");
	FILE * fich;
	fich = fopen (nfichero, "wb"); //al abrir con w se borra el contenido
	if(fich != NULL){
		Salvar (miarbol, fich);
		fclose (fich);
	}
	printf("\nY lo mostramos ordenado\n");
	muestrafichero(nfichero);
	Destruir (&miarbol);

	return EXIT_SUCCESS;
}
