#include "Lista.h"
#include <stdio.h>
#include <stdlib.h>

void crearLista(TLista *lista) { (*lista) = NULL; }

int buscar(TLista lista, struct Punto p) {
  int res = 0;
  TLista aux = lista;
  while (aux != NULL && res == 0) {
    if (aux->punto.x == p.x) {
      res = 1;
    }
    aux = aux->sig;
  }
  return res;
}

void insertarPunto(TLista *lista, struct Punto punto, int *ok) {
  TLista newNode = malloc(sizeof(struct Nodo));
  newNode->punto = punto;
  int encontrado = buscar(*lista, punto);

  if (encontrado == 0) {
    if (*lista == NULL) {
      newNode->sig = NULL;
      *lista = newNode;
      (*ok) = 1;
    } else if ((*lista)->punto.x > punto.x) {
      newNode->sig = *lista;
      *lista = newNode;
      (*ok) = 1;
    } else {
      TLista aux = *lista;
      while (aux->sig != NULL && aux->sig->punto.x < punto.x) {
        aux = aux->sig;
      }
      newNode->sig = aux->sig;
      aux->sig = newNode;
      (*ok) = 1;
    }
  } else {
    (*ok) = 0;
  }
}

void eliminarPunto(TLista *lista, float x, int *ok) {
  if (*lista == NULL) {
    (*ok) = 0;
  } else {
    TLista aux = *lista;
    if ((*lista)->punto.x == x) {
      aux = (*lista)->sig;
      free(*lista);
      *lista = aux;
    } else {
      int encontrado = 0;
      TLista aux_eliminado = NULL;
      while (aux->sig != NULL && encontrado == 0) {
        if (aux->sig->punto.x == x) {
          aux_eliminado = aux->sig;
          aux->sig = aux->sig->sig;
          free(aux_eliminado);
          aux_eliminado = NULL;
          encontrado = 1;
        }
        aux = aux->sig;
      }
    }
  }
}

void mostrarLista(TLista lista) {
  int pos = 1;
  TLista aux = lista;
  while (aux != NULL) {
    printf("Punto %i: (%f,%f) \n", pos, aux->punto.x, aux->punto.y);
    aux = aux->sig;
    pos++;
  }
  printf("\n");
  printf("--------------------------------------------------------- \n");
  printf("\n");
}

void destruir(TLista *lista) {
  TLista aux = *lista;
  while ((*lista)->sig != NULL) {
    *lista = (*lista)->sig;
    free(aux);
    aux = *lista;
  }
  *lista = NULL;
}

void leePuntos(TLista *lista, char *nFichero) {
  int ok;
  crearLista(&(*lista));
  FILE *ptr_file = fopen(nFichero, "rb");
  if (ptr_file == NULL) {
    perror("No existe el archivo especificado");
    exit(-1);
  } else {
    struct Punto p;
    while (fread(&p, sizeof(struct Punto), 1, ptr_file) == 1) {
      insertarPunto(&(*lista), p, &ok);
    }
    fclose(ptr_file);
  }
}
