#include "ListaJugadores.h"
#include <stdio.h>
#include <stdlib.h>

void crear(TListaJugadores *lj) { (*lj) = NULL; }

int buscarJug(TListaJugadores lj, unsigned int id) {

  int igual = 0;
  while (lj != NULL && igual == 0) {
    if (lj->nJ == id) {
      igual = 1;
    }
    lj = lj->sig;
  }

  return igual;
}

void insertar(TListaJugadores *lj, unsigned int id) {
  TListaJugadores newNode;
  if (*lj == NULL) {
    newNode = malloc(sizeof(struct ListaJugadores));
    newNode->nJ = id;
    newNode->nGoles = 1;
    newNode->sig = NULL;
    *lj = newNode;
  } else {
    int idIdem = buscarJug(*lj, id);
    if (idIdem == 0) {
      newNode = malloc(sizeof(struct ListaJugadores));
      newNode->nJ = id;
      newNode->nGoles = 1;
      int esMenor = 0;
      TListaJugadores aux_it = *lj;
      while (aux_it->sig != NULL && esMenor == 0) {
        if (id < aux_it->sig->nJ) {
          esMenor = 1;
        } else if (aux_it->sig != NULL) {
          aux_it = aux_it->sig;
        }
      }

      newNode->sig = aux_it->sig;
      aux_it->sig = newNode;
    } else {
      int encontrado = 0;
      TListaJugadores aux_it = *lj;
      while (aux_it != NULL && encontrado == 0) {
        if (id == aux_it->nJ) {
          encontrado = 1;
        } else {
          aux_it = aux_it->sig;
        }
      }
      aux_it->nGoles++;
    }
  }
}

void recorrer(TListaJugadores lj) {
  while (lj != NULL) {
    printf("Jugador %u ha marcado %u \n", lj->nJ, lj->nGoles);
    lj = lj->sig;
  }
}

int longitud(TListaJugadores lj) {
  int res = 0;
  while (lj != NULL) {
    res++;
    lj = lj->sig;
  }
  return res;
}

void eliminar(TListaJugadores *lj, unsigned int n) {
  TListaJugadores ant = NULL;
  TListaJugadores current = *lj;
  int cnt = longitud(*lj);

  if (*lj != NULL) {
    while (current != NULL) {
      if (current->nGoles < n) {
        if (ant == NULL || ant == current) {
          ant = current->sig;
          free(current);
          current = ant;
        } else {
          ant->sig = current->sig;
          free(current);
          current = ant->sig;
        }
        cnt--;
      } else {
        ant = current;
        current = current->sig;
      }
    }

    if (cnt == 1) {
      *lj = ant;
    } else if (cnt == 0) {
      *lj = NULL;
    }
  }
}

unsigned int maximo(TListaJugadores lj) {
  if (lj != NULL) {
    unsigned int maximo = lj->nGoles;
    unsigned int id = lj->nJ;
    lj = lj->sig;
    while (lj != NULL) {
      if (lj->nGoles > maximo) {
        maximo = lj->nGoles;
        id = lj->nJ;
      }
      lj = lj->sig;
    }

    return id;
  } else {
    return 0;
  }
}

void destruir(TListaJugadores *lj) {
  TListaJugadores aux_it = *lj;
  while (*lj != NULL) {
    *lj = (*lj)->sig;
    free(aux_it);
    aux_it = *lj;
  }
  *lj = NULL;
}
