#include "listaProcHebra.c"
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
  LSistema p;

  Crear(&p);
  assert(p == NULL);

  InsertarProceso(&p, 3);
  assert(p->id == 3);
  InsertarProceso(&p, 4);
  assert(p->sigProc->id == 4);
  InsertarProceso(&p, 100);
  assert(p->sigProc->sigProc->id);

  InsertarHebra(&p, 4, "h1", 7);
  InsertarHebra(&p, 4, "h2", 8);
  InsertarHebra(&p, 4, "h3", 4);
  InsertarHebra(&p, 4, "h5", 6);
  InsertarHebra(&p, 100, "h4", 9);
  InsertarHebra(&p, 5, "h7", 1);

  Mostrar(p);

  EliminarProceso(&p, 4);

  Destruir(&p);

  return 0;
}
