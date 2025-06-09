#include "colaListas.h"
#include <stdio.h>

int main() {
  T_Cola cola;
  Crear(&cola);

  AñadirProceso(&cola,3,2);
  AñadirProceso(&cola,3,3);
  AñadirProceso(&cola,4,4);
  AñadirProceso(&cola,4,5);
  AñadirProceso(&cola,1,6);
  EjecutarProceso(&cola);
  EjecutarProceso(&cola);
  int p = Buscar(cola,4);
  printf("%i \n",p);
  Mostrar(cola);
  return 0;
}
