#include "Planificador.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void crear(T_Planificador *planif) { *planif = NULL; }

int buscarId(T_Planificador p, char *id) {
  int ok = -1; // El -1 significa que no se ha encontrado
  if (p != NULL) {
    while (p != NULL && ok == -1) {
      if (strcmp(p->id, id) == 0) {
        ok = 1; // Lo encuentra
      } else {
        p = p->sig;
      }
    }
  }
  return ok;
}

void insertar_tarea(T_Planificador *planf, int prio, char *id) {
  if (buscarId(*planf, id) == -1) { // Si no lo encuentra
    T_Planificador newNode = malloc(sizeof(struct Planificador));

    if (newNode == NULL) {
      perror("No hay memoria suficiente");
      exit(-1);
    }

    newNode->prioridad = prio;
    newNode->id = malloc(strlen(id) + 1);
    strcpy(newNode->id, id);

    if (*planf == NULL) {
      newNode->sig = NULL;
      *planf = newNode;
    } else {
      if ((*planf)->prioridad < newNode->prioridad) {
        newNode->sig = *planf;
        *planf = newNode;
      } else {
        if ((*planf)->sig == NULL) {
          newNode->sig = NULL;
          (*planf)->sig = newNode;
        } else {
          T_Planificador it_planf = *planf;
          while (it_planf->sig != NULL &&
                 newNode->prioridad < it_planf->sig->prioridad) {
            it_planf = it_planf->sig;
          }
          newNode->sig = it_planf->sig;
          it_planf->sig = newNode;
        }
      }
    }
  }
}

void mostrar(T_Planificador planificador) {
  while (planificador != NULL) {
    printf("Identificador: %s; y Prioridad: %i \n", planificador->id,
           planificador->prioridad);
    planificador = planificador->sig;
  }
}

void eliminar_tarea(T_Planificador *planif, char *id, unsigned int *ok) {
  if (*planif == NULL) {
    printf("Error: Esta intentando eliminar un elemento de una lista vacia");
  } else {
    T_Planificador it_planf;
    if (strcmp((*planif)->id, id) == 0) {
      it_planf = (*planif)->sig;
      free(*planif);
      *planif = it_planf;
    } else {
      T_Planificador it_ant_planf = *planif;
      it_planf = (*planif)->sig;
      while (it_planf->sig != NULL && (*ok) == 0) {
        if (strcmp(it_planf->id, id) == 0) {
          (*ok) = 1;
        } else {
          it_ant_planf = it_planf;
          it_planf = it_planf->sig;
        }
      }

      if ((*ok) == 1) {
        it_ant_planf->sig = it_planf->sig;
        free(it_planf);
        it_planf = NULL;
      } else {
        if (strcmp(it_planf->id, id) == 0) {
          it_ant_planf->sig = it_planf->sig;
          free(it_planf);
          it_planf = NULL;
          (*ok) = 1;
        } else {
          printf("El elemento a eliminar no se encuentra en nuestra lista");
        }
      }
    }
  }
}

void planificar(T_Planificador *planif) {
  if(*planif == NULL){
    printf("El planificador esta vacio \n");
  }else{
    if ((*planif)->sig != NULL) {
      T_Planificador aux = (*planif)->sig;
      free(*planif);
      *planif = aux;
    } else {
      free(*planif);
      *planif = NULL;
    }
  }
}

void destruir(T_Planificador *planif) {
  if (*planif != NULL) {
    T_Planificador it = *planif;
    while (*planif != NULL) {
      *planif = (*planif)->sig;
      free(it);
      it = *planif;
    }
    *planif = NULL;
  }
}
