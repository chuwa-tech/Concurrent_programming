#include<stdio.h>
#include<stdlib.h>
#include "Planificador.h"

int main(){
    T_Planificador planif;
    unsigned int ok;
    crear(&planif);

    insertar_tarea(&planif,4,"t2");
    insertar_tarea(&planif,8,"t1");
    insertar_tarea(&planif,5,"t3");
    insertar_tarea(&planif,9,"t4");

    mostrar(planif);

    eliminar_tarea(&planif, "t1", &ok);

    eliminar_tarea(&planif, "t4", &ok);

    eliminar_tarea(&planif, "t3", &ok);

    eliminar_tarea(&planif, "t2", &ok);

    mostrar(planif);

    planificar(&planif);

    insertar_tarea(&planif,4,"t2");
    insertar_tarea(&planif,8,"t1");
    insertar_tarea(&planif,5,"t3");
    insertar_tarea(&planif,9,"t4");
    insertar_tarea(&planif,4,"t2");
    insertar_tarea(&planif,8,"t1");
    insertar_tarea(&planif,5,"t3");
    insertar_tarea(&planif,9,"t4");

    mostrar(planif);

    planificar(&planif);

    mostrar(planif);

    destruir(&planif);

    mostrar(planif);
}