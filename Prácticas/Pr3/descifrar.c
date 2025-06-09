#include<stdio.h>
#include<stdlib.h>

void decrypt(unsigned * v, unsigned * k){
    const unsigned delta = 0x9e3779b9;
    unsigned sum = 0xC6EF3720;
    for(int i = 0; i < 32; i++){
        v[1] -= ((v[0] << 4)+k[2])^(v[0]+sum)^((v[0] >> 5)+k[3]);
        v[0] -= ((v[1] << 4)+k[0])^(v[1]+sum)^((v[1] >> 5)+k[1]);
        sum -= delta;
    }
}

int main(int argc, char const *argv[])
{
    unsigned k[4]={128, 129, 130, 131}; 
    FILE * ptr_entrada;
    FILE * ptr_salida;

    if(argc != 3){
        printf("Se esperaban 2 argumentos, el fichero de entrada y de salida");
        exit(-1);
    }

    ptr_entrada = fopen(argv[1],"rb");
    if(ptr_entrada == NULL){
        perror("No se puede leer el fichero de entrada");
        exit(-1);
    }

    ptr_salida = fopen(argv[2],"wb");
    if(ptr_salida == NULL){
        perror("No se puede escribir en el fichero de salida");
        exit(-1);
    }

    unsigned tam_fichero;

    if((fread(&tam_fichero,sizeof(unsigned),1,ptr_entrada))==0){ // Accedo a las dos primeras posiciones de memoria para obtener el tamaÃ±o del fichero final
        perror("El formato del fichero de entrada no es correcto");
    }else{
        unsigned tam_fichero_adaptado = tam_fichero;
        if(tam_fichero%8!=0) tam_fichero_adaptado += 8 - tam_fichero%8;

        unsigned *ptr_memoria = malloc(tam_fichero_adaptado);
        if(ptr_memoria==NULL){
            perror("No se puede pedir memoria para alojar el fichero");
            exit(-1);
        }

        if((fread(ptr_memoria,tam_fichero-(8 - tam_fichero%8),1,ptr_entrada))==0){
            perror("jjjaj k bien");
            exit(-1);
        }else{

            //hacer un if para ver si se ha leido correctamente
            for(size_t i = 0;i<tam_fichero_adaptado/sizeof(unsigned);i+=2){
                //Iteramos para desencriptar 2 a 2
                decrypt(&ptr_memoria[i],k);
            }
            fwrite(ptr_memoria,1,tam_fichero_adaptado,ptr_salida);
            free(ptr_memoria);
            fclose(ptr_entrada);
            fclose(ptr_salida);
        }    
    }
    return 0;
}
