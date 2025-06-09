#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

typedef struct T_Nodo * LProc;

struct T_Nodo{
    int id;
    LProc sig;
};

void MostrarLista(LProc list){
    LProc iterador = list;
    int cnt = 0;
    if(list == NULL){
        printf("La lista esta vacia.");
    }
    else{
        int elem = list->id;
        printf("Proceso nº%i: %i\n", cnt, iterador->id);
        cnt++;
        iterador = iterador->sig;
        while(iterador->id != elem){

            printf("Proceso nº%i: %i\n", cnt, iterador->id);
            cnt++;
            iterador = iterador->sig;

        }
    }

}

void Crear(LProc *lista)
{
    (*lista) = NULL;
}

void AñadirProceso(LProc *lista, int idproc)
{
    LProc newNode = malloc(sizeof(struct T_Nodo));

    if(newNode == NULL){
        perror("No hay memoria disponible");
        exit(-1);
    }

    newNode -> id = idproc;
    
    if(*lista == NULL){
        newNode -> sig = NULL;
        *lista = newNode;
    }else{
        newNode -> sig = (*lista);
        if((*lista) -> sig == NULL){
            (*lista) -> sig = newNode;
        }else{
            LProc ptr = (*lista);
            
            while(ptr -> sig -> id != (*lista) -> id){
                ptr = ptr ->sig;
            }

            ptr -> sig = newNode;
        }
    }
}

void EjecutarProceso(LProc *lista)
{
    LProc aux = *lista;

    while(aux -> sig )

    aux -> sig = (*lista) -> sig;
}

int main(){
    LProc lista;
    Crear(&lista);
    MostrarLista(lista);
    AñadirProceso(&lista, 1);
    assert(lista -> id == 1);
    AñadirProceso(&lista, 5);
    assert(lista -> sig -> id == 5);
    AñadirProceso(&lista, 3);
    assert(lista -> sig -> sig -> id == 3);
    AñadirProceso(&lista, 4);
    MostrarLista(lista);
    EjecutarProceso(&lista);
    assert(lista -> id == 5);
    MostrarLista(lista);
    EjecutarProceso(&lista);
    assert(lista -> id == 3);
    EjecutarProceso(&lista);
    MostrarLista(lista);
    return 0;
}