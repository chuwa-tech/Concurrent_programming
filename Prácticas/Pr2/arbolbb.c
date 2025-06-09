/*
 * arbolbb.c
 *
 *  Created on: 15 mar. 2021
 *      Author: Laura
 */
#include <stdio.h>
#include <stdlib.h>
#include "arbolbb.h"


// Inicializa la estructura a NULL.
void Crear(T_Arbol* arbol_ptr){
    *arbol_ptr = NULL;
}

// Destruye la estructura utilizada.
void Destruir(T_Arbol *arbol_ptr){
    T_Arbol ptr = *arbol_ptr;
    if (ptr != NULL){
        if (ptr -> izq != NULL){
            Destruir(&ptr -> izq);
        }
        if (ptr -> der != NULL){
            Destruir(&ptr -> der);
        }
        free(ptr);
    }
    *arbol_ptr = NULL;
}

// Inserta num en el arbol
void Insertar(T_Arbol *arbol_ptr,unsigned num)
{
    if((*arbol_ptr) == NULL){
        T_Arbol newNode = malloc(sizeof(struct T_Nodo));
        newNode -> dato = num;
        newNode -> izq = NULL;
        newNode -> der = NULL;
       *arbol_ptr = newNode;
    }else{
        if(num < (*arbol_ptr) -> dato){
            Insertar(&(*arbol_ptr) -> izq, num);
        }else if (num > (*arbol_ptr) -> dato){
            Insertar(&(*arbol_ptr) -> der, num);
        }
    }
}

// Muestra el contenido del árbol en InOrden
void Mostrar(T_Arbol arbol){
    T_Arbol aux = arbol;
    if(aux != NULL){

        if(aux -> izq != NULL){
            Mostrar(aux ->izq);
        }

        printf("%u ", arbol -> dato);

        if(aux -> der != NULL){
            Mostrar(aux ->der);
        }
        
    }else{
        printf("Es un arbol vacio");
    }
}


// Guarda en disco el contenido del arbol - recorrido InOrden
void Salvar(T_Arbol arbol, FILE *fichero){
	T_Arbol ptr = arbol;
    if (ptr != NULL){
        if (ptr -> izq != NULL){
            Salvar(ptr -> izq, fichero);
        }

        fwrite(&ptr -> dato, sizeof(unsigned),1,fichero);

        if (ptr -> der != NULL){
            Salvar(ptr -> der, fichero);
        }

    }
}
