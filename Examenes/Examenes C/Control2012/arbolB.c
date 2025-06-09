#include "arbolB.h"
#include <stdio.h>
#include <stdlib.h>

void CrearABB(T_Arbol *arb) { *arb = NULL; }

void InsertarEnABB(T_Arbol *arb, int elem) {
  T_Arbol newNode = malloc(sizeof(struct Arbol));
  newNode->dato = elem;
  newNode->right = NULL;
  newNode->left = NULL;
  if (*arb == NULL) {
    *arb = newNode;
  } else {
    if ((*arb)->dato < elem) {
      InsertarEnABB(&((*arb)->right), elem);
    } else {
      InsertarEnABB(&((*arb)->left), elem);
    }
  }
}

void RecorrerABB(T_Arbol arb) {
  if (arb == NULL) {
    printf(" ");
  } else {
    if (arb->right == NULL && arb->left == NULL) {
      printf(" %i", arb->dato);
    } else if (arb->left == NULL) {
      printf(" %i", arb->dato);
      RecorrerABB(arb->right);
    } else {
      RecorrerABB(arb->left);
      printf(" %i", arb ->dato);
      RecorrerABB(arb->right);
    }
  }
}

void DestruirABB(T_Arbol *arb) {
  if ((*arb)->right == NULL && (*arb)->left == NULL) {
    free(*arb);
    *arb = NULL;
  } else if ((*arb)->left == NULL) {
    DestruirABB(&((*arb)->right));
    free(*arb);
    *arb = NULL;
  } else {
    DestruirABB(&((*arb)->left));
    free(*arb);
    *arb = NULL;
  }
}
