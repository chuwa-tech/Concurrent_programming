#include "colaListas.h"
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

const int MAXPRIO = 6;

void Crear(T_Cola *c) { (*c) = NULL; }

int buscarPrio(T_Cola c, int prioridad) {
  int encontrado = 0;
  T_Cola it = c;
  while (it != NULL && encontrado == 0) {
    if (it->prio == prioridad) {
      encontrado = 1;
    } else {
      it = it->sig;
    }
  }
  return encontrado;
}

T_Cola crearNewPosCola(int prioridad, T_Nodo newNode) {
  T_Cola newPosCola = malloc(sizeof(struct Cola));
  if (newPosCola == NULL) {
    perror("Error al pedir memoria");
    exit(-1);
  }
  newPosCola->prio = prioridad;
  newPosCola->procs = newNode;
  return newPosCola;
}

T_Nodo getList(T_Cola c, int prioridad) {
  int mismaPrioridad = 0;
  while (c != NULL && mismaPrioridad == 0) {
    if (c->prio == prioridad) {
      mismaPrioridad = 1;
    } else {
      c = c->sig;
    }
  }
  return c->procs;
}

T_Nodo crearNodo(int idProc) {
  T_Nodo node = malloc(sizeof(struct Nodo));
  node->dato = idProc;
  node->sig = NULL;
  return node;
}

void AñadirProceso(T_Cola *c, int prioridad, int idProc) {
  if (*c == NULL) {
    T_Nodo newNode = crearNodo(idProc);
    *c = crearNewPosCola(prioridad, newNode);
  } else {
    int esta = -1;
    T_Cola itproc = *c;
    T_Nodo proc;
        while(itproc != NULL && esta == -1){
            proc = itproc->procs;
            while(proc != NULL && esta == -1){
                if(proc->dato == idProc){
                    esta = 0;
                }
                else{
                    proc = proc->sig;
                }
            }
            itproc = itproc->sig;
        }
    if (esta == -1) {
      T_Nodo newNode = crearNodo(idProc);
      int prioFound = buscarPrio(*c, prioridad);
      if (prioFound == 0) {
        T_Cola newPos = crearNewPosCola(prioridad, newNode);
        if ((*c)->prio > newPos->prio) {
          newPos->sig = *c;
          *c = newPos;
        } else {
          T_Cola aux_it = *c;
          while (aux_it->sig != NULL && aux_it->sig->prio < prioridad) {
            aux_it = aux_it->sig;
          }
          newPos->sig = aux_it->sig;
          aux_it->sig = newPos;
        }
      } else {
        T_Nodo it_lista = getList(*c, prioridad);
        while (it_lista->sig != NULL) {
          it_lista = it_lista->sig;
        }
        it_lista->sig = newNode;
      }
    }
  }
}

void EjecutarProceso(T_Cola *c){
    if((*c) == NULL){
        printf("No existen procesos por ejecutar.");
    }
    else{
        T_Nodo aux_proc;
        if((*c)->procs->sig == NULL){
            T_Cola aux = *c;
            aux_proc = (*c)->procs;
            free(aux_proc);
            aux_proc = NULL;
            *c = (*c)->sig;
            free(aux);
            aux = NULL;
        }
        else{
            aux_proc = (*c)->procs;
            (*c)->procs = (*c)->procs->sig;
            free(aux_proc);
            aux_proc = NULL;
        }
    }
}

int Buscar(T_Cola c, int idProc) {
  int procFound = -1;
  while (c != NULL && procFound == -1) {
    if(c->procs->dato == idProc){
      procFound = 1;
    }else{
      T_Nodo aux_it = c ->procs;
      while (aux_it->sig != NULL && procFound == -1) {
        if (aux_it->dato == idProc) {
          procFound = 1;
        } else {
          aux_it = aux_it->sig;
        }
      }
      if (procFound == -1) {
        c = c->sig;
      }
    }
  }
  if (procFound == -1) {
    return procFound;
  } else {
    return c->prio;
  }
}

void Mostrar(T_Cola c) {
  while (c != NULL) {
    printf("Prioridad %i: {%i", c->prio, c->procs->dato);
    c->procs = c->procs->sig;
    while (c->procs != NULL) {
      printf(", %i", c->procs->dato);
      c->procs = c->procs->sig;
    }
    printf("} \n");
    c = c->sig;
  }
}
