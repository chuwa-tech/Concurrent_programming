/*
============================================================================
 Name        : gestion_memoria.h
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#ifndef _GESTION_TREMOR_
#define _GESTION_TREMOR_



#include <time.h>
typedef struct T_Nodo* T_Lista;

/*
Se va almacenar por orden cronológico, primero los más antiguos.
*/
struct T_Nodo {
	time_t fecha;
	unsigned duracion;
	T_Lista sig;
};


/* Muestra los episodios de tremor que ha tenido el usuario por orden cronológico, primero los más antiguos, se le pasa la cola de la lista */
	void mostrar_nuevos2antiguos (T_Lista lista);

/* 
Registra un episodio de tremor, con su fecha y duración
 */
	void registrar(T_Lista * ptr_lista_head,const time_t * fecha, unsigned duracion, unsigned* ok);

/* 
Libera todos los episodios que son anteriores a la fecha dada
 */
	int liberar(T_Lista * ptr_lista_head,const time_t * fecha);

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El parámetro manejador debe terminar apuntando a NULL */

	void destruir(T_Lista* ptr_lista_head);

#endif
