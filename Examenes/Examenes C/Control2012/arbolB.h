typedef struct Arbol * T_Arbol;

struct Arbol{
  int dato;
  T_Arbol right;
  T_Arbol left;
};

void CrearABB(T_Arbol *arb);
void InsertarEnABB(T_Arbol *arb, int elem);
void RecorrerABB(T_Arbol arb);
void DestruirABB(T_Arbol *arb);
  
