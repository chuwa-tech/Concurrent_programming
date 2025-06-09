#include "Mprocesos.h"
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

void Crear(LProc *lroundrobin) { (*lroundrobin) = NULL; }

void AnadirProceso(LProc *lroundrobin, int idproc) {
  LProc newNode = malloc(sizeof(struct Proc));
  newNode->id = idproc;
  if (newNode == NULL) {
    perror("Error al pedir memoria");
    exit(-1);
  }
  if (*lroundrobin == NULL) {
    newNode->sig = newNode;
    *lroundrobin = newNode;
  } else {
    newNode->sig = (*lroundrobin)->sig;
    (*lroundrobin)->sig = newNode;
    *lroundrobin = newNode;
  }
}

void EjecutarProcesos(LProc lroundrobin) {
  if (lroundrobin == NULL) {
    printf("Lista vacia \n");
  } else {
    LProc it = lroundrobin->sig;
    printf("En ejecución:");
    while (it != NULL && it->id != lroundrobin->id) {
      printf(" %i -->", it->id);
      it = it->sig;
    }
    printf(" %i \n", it->id);
  }
}

void EliminarProceso(int id, LProc *lista) {
  if (*lista == NULL) {
    printf("No hay procesos para eliminar \n");
  } else {
    LProc it_lista = *lista;
    if ((*lista)->id == id) {
      if ((*lista)->id != (*lista)->sig->id) {
        while (it_lista->sig->id != (*lista)->id) {
          it_lista = it_lista->sig;
        }
        it_lista->sig = (*lista)->sig;
        free(*lista);
        *lista = it_lista;
      } else {
        (*lista)->sig = NULL;
        free(*lista);
        *lista = NULL;
      }
    } else {
      int ok = 0;
      LProc aux;
      while (it_lista->sig->id != (*lista)->id && ok == 0) {
        if (it_lista->sig->id == id) {
          ok = 1;
          aux = it_lista->sig;
        } else {
          it_lista = it_lista->sig;
        }
      }

      if (ok == 1) {
        it_lista->sig = aux->sig;
        free(aux);
        aux = NULL;
      }
    }
  }
}

int calcNProc(LProc l) {
  int res = 1;
  if (l != NULL) {
    LProc aux = l;
    while (aux != NULL && aux->sig->id != l->id) {
      res++;
      aux = aux->sig;
    }
    return res;
  } else {
    return 0;
  }
}

void EscribirFichero(char *nomf, LProc *lista) {
  FILE *ptr_fichero = fopen(nomf, "wb");

  if (ptr_fichero == NULL) {
    printf("No existe el fichero especificado");
    exit(-1);
  } else {
    int numProcs = calcNProc(*lista);

    int ok = fwrite(&numProcs, sizeof(int), 1, ptr_fichero);
    assert(ok == 1);

    while (numProcs > 0) {
      ok = fwrite(&((*lista)->id), sizeof(int), 1, ptr_fichero);
      assert(ok == 1);
      EliminarProceso((*lista)->id, &(*lista));
      numProcs--;
    }
  }
  fclose(ptr_fichero);
}
