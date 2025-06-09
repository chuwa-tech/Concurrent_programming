#include <stdlib.h>
#include <stdio.h>
#include "gestion_memoria.h"

int MAX  = 1000;

typedef struct T_Nodo* T_Manejador;

/*
Función privada recomendada:
Recibe una lista y compacta elementos que son consecutivos, devolviendo la lista compactada.
*/
void compactar(T_Manejador *manejador_ptr){
     T_Manejador ptr_aux = manejador_ptr;
     while(ptr_aux != NULL){
         while(ptr_aux -> sig != NULL && (ptr_aux -> fin + 1 == ptr_aux -> sig -> inicio)){
             ptr_aux -> fin = ptr_aux -> sig -> fin;
             ptr_aux -> sig = ptr_aux -> sig -> sig;
         }
         ptr_aux = ptr_aux -> sig;
     }
}

/* Crea la estructura utilizada para gestionar la memoria disponible. Inicialmente, s�lo un nodo desde 0 a MAX 
Por recordar:
typedef struct T_Nodo* T_Manejador;

struct T_Nodo {
	unsigned inicio;
	unsigned fin;
	T_Manejador sig;
};

En el main se define la lista como T_Manejador manej; (un puntero a una structura T_Nodo).
¿Porqué se pasa un puntero a T_Manejador? ¿Que pasa si pasamos T_Manejador y no un puntero a T_Manejador y cambiamos su valor (su valor es una zona de memporia)?
*/
void crear(T_Manejador* manejador){  
    T_Manejador ptr = malloc(sizeof(struct T_Nodo));
    if (ptr != NULL)
    {
        ptr->inicio = 0;
        ptr->fin = MAX - 1;
        ptr->sig = NULL;
    }else
    {
        perror("Malloc memory fail");
        exit(-1);
    }
    //manejador = &ptr;
}

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El par�metro manejador debe terminar apuntando a NULL 

Consejo: Para saber si te estas dejando memoria por ahí, en el main crea un bucle infinito que crea y destruye, si la memoria no se mantiene constante, está mal.

*/
void destruir(T_Manejador* manejador){
    T_Manejador aux = *manejador;
    if(aux != NULL){
        while(manejador != NULL){
            *manejador = (*manejador) -> sig;
           free(aux);
           aux = *manejador;
        }
    }
}

/* Devuelve en �dir� la dirección de memoria �simulada� (unsigned) donde comienza el trozo de memoria continua de tamaño �tam� solicitada.
Si la operación se pudo llevar a cabo, es decir, existe un trozo con capacidad suficiente, devolvera TRUE (1) en �ok�; FALSE (0) en otro caso.
 */
void obtener(T_Manejador *manejador, unsigned tam, unsigned *dir, unsigned *ok){
    compactar(manejador);
    T_Manejador aux = manejador;
    ok = 0;
    while(aux -> sig != NULL && !ok){
        if(aux -> fin - aux -> inicio > tam){
            ok = 1;
            dir = aux -> fin - tam;
            aux -> fin = dir - 1;
        }else if (aux -> fin - aux -> inicio == tam){
            ok = 1;
            manejador = aux -> sig;
            dir = aux -> inicio-1;
            free(aux);
        }else{
            if((aux->sig != NULL) && (aux->sig->fin - aux-> sig -> inicio == tam)){
                ok = 1;
                dir = aux -> sig -> inicio;
                aux->sig = aux-> sig ->sig;
            }
            aux = aux->sig;
        }
    }
}

/* Muestra el estado actual de la memoria, bloques de memoria libre */
void mostrar (T_Manejador manejador){
    while(manejador != NULL){
        printf("Inicio: %u Fin: %u ", manejador ->inicio,manejador ->fin);
        manejador = manejador -> sig;
    }
  ;
}

/* Devuelve el trozo de memoria continua de tamaño �tam� y que
 * comienza en �dir�.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
    T_Manejador ptr_aux = manejador;
    unsigned stop = 0;
    while (ptr_aux -> sig != NULL){
        unsigned ok = dir > ptr_aux -> fin && dir < ptr_aux -> sig -> inicio; 
        if(ok){
            stop = 1;
            T_Manejador newNode = malloc(sizeof(struct T_Nodo));
            newNode -> inicio = dir;
            newNode -> fin = dir + tam;
            newNode -> sig = ptr_aux -> sig;
            ptr_aux -> sig = newNode;
        }else{
            ptr_aux = ptr_aux -> sig;
        }
    }
    compactar(manejador);
}