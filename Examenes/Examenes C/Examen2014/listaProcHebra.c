#include <stdio.h>
#include <stdlib.h>

typedef struct L_Hebra *P_Hebra;

struct L_Hebra {
  char *idHebra;
  int prioridad;
  P_Hebra sig;
};

typedef struct LProc *LSistema;

struct LProc {
  int id;
  LSistema sigProc;
  P_Hebra sigH;
};

void Crear(LSistema *ls) { (*ls) = NULL; }

void InsertarProceso(LSistema *ls, int idproc) {
  LSistema newNode = malloc(sizeof(struct LProc));
  if (newNode == NULL) {
    perror("Error. No hay memoria suficiente");
    exit(-1);
  } else {
    newNode->id = idproc;
    newNode->sigProc = NULL;
    if ((*ls) == NULL) {
      *ls = newNode;
    } else {
      if ((*ls)->sigProc == NULL) {
        (*ls)->sigProc = newNode;
      } else {
        LSistema aux = *ls;
        while (aux->sigProc != NULL) {
          aux = aux->sigProc;
        }
        aux->sigProc = newNode;
      }
    }
  }
}

LSistema buscarProc(LSistema ls, int idproc) {
  LSistema aux = ls;
  while (aux != NULL && aux->id != idproc) {
    aux = aux->sigProc;
  }
  return aux;
}

void InsertarHebra(LSistema *ls, int idproc, char *idhebra, int priohebra) {
  if ((*ls) == NULL) {
    printf("Error. No se puede insertar ninguna hebra");
  } else {
    LSistema pos = buscarProc(*ls, idproc);
    if (pos == NULL) {
      printf("No se encontró el proceso especificado \n");
    } else {
      P_Hebra newNode = malloc(sizeof(struct L_Hebra));

      if (newNode == NULL) {
        perror("Error. No hay memoria suficiente");
        exit(-1);
      } else {
        newNode->idHebra = idhebra;
        newNode->prioridad = priohebra;
        if (pos->sigH == NULL) {
          newNode->sig = NULL;
          pos->sigH = newNode;
        } else {
          if (pos->sigH->sig == NULL) {
            if (pos->sigH->prioridad < newNode->prioridad) {
              newNode->sig = pos->sigH;
              pos->sigH = newNode;
            } else {
              newNode->sig = NULL;
              pos->sigH->sig = newNode;
            }
          } else {
            P_Hebra aux = pos->sigH;
            while (aux->sig != NULL && newNode->idHebra < aux->idHebra) {
              aux = aux->sig;
            }
            if (aux->sig != NULL) {
              newNode->sig = aux;
              aux = newNode;
            } else {
              newNode->sig = aux->sig;
              aux->sig = newNode;
            }
          }
        }
      }
    }
  }
}

void Mostrar(LSistema ls) {
  if (ls == NULL) {
    printf("Lista vacia");
  } else {
    while (ls != NULL) {
      printf("Proceso (%i), Hebras: ", ls->id);
      P_Hebra aux = ls->sigH;
      if (aux == NULL) {
        printf("Sin hebras \n");
      } else {
        while (aux != NULL) {
          printf("(%s,%i), ", aux->idHebra, aux->prioridad);
          aux = aux->sig;
        }
      }
      printf("\n");
      ls = ls->sigProc;
    }
  }
}

void EliminarHebras(P_Hebra *hebras) {
  P_Hebra aux;
  while (*hebras != NULL) {
    aux = (*hebras)->sig;
    *hebras = (*hebras)->sig;
    free(aux);
  }
  *hebras = NULL;
}

void EliminarProceso(LSistema *ls, int idproc) {
  if (ls != NULL) {
    if ((*ls)->sigProc == NULL && ((*ls)->id == idproc)) {
      if ((*ls)->sigH != NULL) {
        P_Hebra aux = (*ls)->sigH;
        while ((*ls)->sigH->sig != NULL) {
          (*ls)->sigH = (*ls)->sigH->sig;
          free(aux);
          aux = (*ls)->sigH;
        }
        (*ls)->sigH = NULL;
      }

      free(ls);
      *ls = NULL;

    } else {
      LSistema iter = *ls;
      LSistema ant = *ls;
      int cnt = 0;
      while (iter->id != idproc) {
        if (cnt != 0) {
          ant = ant->sigProc;
        }
        iter = iter->sigProc;
        ++cnt;
      }
      if ((iter)->sigH != NULL) {
        P_Hebra aux = iter->sigH;
        while ((iter)->sigH->sig != NULL) {
          (iter)->sigH = (iter)->sigH->sig;
          free(aux);
          aux = (iter)->sigH;
        }
        (iter)->sigH = NULL;
      }

      LSistema proceso = iter;
      if (iter->id == ant->id) {
        (*ls) = iter->sigProc;
      } else {
        ant->sigProc = iter->sigProc;
      }
      free(proceso);
      proceso = NULL;
    }
  } else {
    printf("Lista vacía\n");
  }
}

void Destruir(LSistema *ls) {
  if (*ls == NULL) {
    printf("Lista vacia");
  } else {
    LSistema iterador = *ls;

    while (*ls != NULL) {
      if ((*ls)->sigH != NULL) {
        EliminarHebras(&((*ls)->sigH));
      }
      iterador = iterador->sigProc;
      free(*ls);
      *ls = iterador;
    }
  }
}
