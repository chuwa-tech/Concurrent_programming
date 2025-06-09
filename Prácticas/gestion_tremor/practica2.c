/*
 ============================================================================
 Name        : Practica2.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

//Macro, si es windows incluye una libería, si no, incluye otra.
#ifdef _WIN32
#include <Windows.h>
#else
#include <unistd.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "gestion_tremor.h"

int main(void) {

	int ok;
	T_Lista lista_tremor_head = NULL;
	time_t now_time;
	time_t pass_time;
	
	time( &now_time );
	registrar(&lista_tremor_head,&now_time,60,&ok);

	//Nos paramos un segundo, para simular un retraso en el registro de nuevos episodios
	#ifdef _WIN32
	Sleep(1000);
	#else
	sleep(1);
	#endif
	

	assert(lista_tremor_head->fecha==now_time);
	assert(lista_tremor_head->duracion==60);
	assert(lista_tremor_head->sig==NULL);
	assert(ok==1);

	time( &now_time );
	registrar(&lista_tremor_head,&now_time,50,&ok);
	assert(ok==1);

	#ifdef _WIN32
	Sleep(1000);
	#else
	sleep(1);
	#endif

	//Guardamos este momento para eliminar todos los anteriores
	time( &pass_time );
	time( &now_time );
	registrar(&lista_tremor_head,&now_time,40,&ok);
	assert(ok==1);

	#ifdef _WIN32
	Sleep(1000);
	#else
	sleep(1);
	#endif

	time( &now_time );
	registrar(&lista_tremor_head,&now_time,20,&ok);
	assert(ok==1);
	#ifdef _WIN32
	Sleep(1000);
	#else
	sleep(1);
	#endif

	mostrar_nuevos2antiguos(lista_tremor_head);

	int borrados = liberar(&lista_tremor_head,&pass_time);

	assert(lista_tremor_head->duracion==40);
	assert(lista_tremor_head->sig!=NULL);
	assert(borrados==2);

	destruir(&lista_tremor_head);

	return EXIT_SUCCESS;
}
