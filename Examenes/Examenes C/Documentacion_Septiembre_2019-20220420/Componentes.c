#include "Componentes.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int Lista_Vacia(Lista lista) {
  if (lista == NULL) {
    return 1;
  } else {
    return 0;
  }
}

int Num_Elementos(Lista lista) {
  int nELms = 0;

  while (lista != NULL) {
    nELms++;
    lista = lista->sig;
  }

  return nELms;
}

void Adquirir_Componente(long *codigo, char *texto) {
  printf("Introduzca el código y texto: ");
  scanf("%ld", codigo);
  fgets(texto, MAX_CADENA, stdin);
}

void Lista_Imprimir(Lista lista) {
  while (lista != NULL) {
    printf("Componente --> Codigo: %ld, Texto: %s", lista->codigoComponente,
           lista->textoFabricante);
    lista = lista->sig;
  }
}

/*
La funcion Lista_Salvar se encarga de guardar en el fichero binario
"examen.dat" la lista enlazada completa que se le pasa como parametro.
Para cada nodo de la lista, debe almacenarse en el fichero
el código y el texto de la componente correspondiente.
*/
void Lista_Salvar(Lista lista) {
  FILE *ptr_fichero = fopen("Examen.data", "wb");
  if (ptr_fichero == NULL) {
    printf("Gilipollas te has equivocado");
  } else {
    while (lista != NULL) {
      fwrite(&lista, sizeof(Componente), 1, ptr_fichero);
      lista = lista->sig;
    }
  }
}

/*
La funcion Lista_Crear crea una lista enlazada vacia
de nodos de tipo Componente.
*/
Lista Lista_Crear() {
  Lista ptr = NULL;
  return ptr;
}

/*
La funcion Lista_Agregar toma como parametro un puntero a una lista,
el código y el texto de un componente y  anyade un nodo al final de
la lista con estos datos.
*/
void Lista_Agregar(Lista *lista, long codigo, char *textoFabricante) { 
  Lista newNode = malloc(sizeof(Componente));
  newNode -> codigoComponente = codigo;
  strcpy(newNode -> textoFabricante, textoFabricante);
  newNode -> sig = NULL;
  if(*lista == NULL){
    *lista = newNode;
  }else{
    Lista it = *lista;
    while(it->sig != NULL){
      it = it -> sig; 
    }
    it -> sig = newNode;
  }
}

/*
Lista_Extraer toma como parametro un puntero a una Lista y elimina el
Componente que se encuentra en su ultima posicion.
*/
void Lista_Extraer(Lista *lista) { 
  if(*lista == NULL){
    printf("Anormal");
  }else{
    if((*lista)->sig == NULL){
      free(*lista);
      *lista = NULL;
    }else{
      Lista ant;
      Lista current = (*lista) -> sig;
      while(current -> sig != NULL){
        ant = current;
        current = current -> sig;
      }
      ant -> sig = current -> sig;
      free(current);
      current = NULL;
    }
  }
}

/*
Lista_Vaciar es una funcion que toma como parametro un puntero a una Lista
y elimina todos sus Componentes.
*/
void Lista_Vaciar(Lista *lista) { 
  Lista it = *lista;
  while(it != NULL){
    *lista = (*lista) ->sig;
    free(it);
    it = *lista;
  } 
  *lista = NULL;
}
