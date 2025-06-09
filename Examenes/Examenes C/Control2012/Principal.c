#include "arbolB.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
  T_Arbol arb;
  CrearABB(&arb);

  InsertarEnABB(&arb, 10);
  InsertarEnABB(&arb, 15);
  InsertarEnABB(&arb, 12);
  InsertarEnABB(&arb, 13);
  InsertarEnABB(&arb, 5);
  InsertarEnABB(&arb, 2);
  InsertarEnABB(&arb, 1);
  InsertarEnABB(&arb, 6);
  InsertarEnABB(&arb, 7);

  RecorrerABB(arb);
  printf("\n");

  DestruirABB(&arb);

  RecorrerABB(arb);

  return 0;
}
