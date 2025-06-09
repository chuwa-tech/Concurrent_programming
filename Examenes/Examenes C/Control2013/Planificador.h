
typedef struct Planificador * T_Planificador;

struct Planificador{
  int prioridad;
  char * id;
  T_Planificador sig;
};

void crear(T_Planificador *planif);
void insertar_tarea(T_Planificador *planf, int prio, char *id);
void mostrar (T_Planificador planificador);
void eliminar_tarea(T_Planificador *planif, char *id, unsigned *ok);
void planificar(T_Planificador *planif);
void destruir(T_Planificador *planif);
