typedef struct Nodo * T_Nodo;

struct Nodo{
   int dato;
   T_Nodo sig; 
};

typedef struct Cola * T_Cola;

struct Cola{
    int prio;
    T_Nodo procs;
    T_Cola sig;
};

void Crear(T_Cola * c);
void AñadirProceso(T_Cola *c, int prioridad, int idProc);
void EjecutarProceso(T_Cola * c);
int Buscar(T_Cola c, int idPoc);
void Mostrar(T_Cola c);
