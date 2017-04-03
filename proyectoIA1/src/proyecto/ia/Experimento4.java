package proyecto.ia;

/*
 Dado el escenario de los apartados anteriores, estudiad como evoluciona el tiempo de ejecución para
 hallar la solución para valores crecientes de los parámetros siguendo la proporción 4:100. Comenzad
 con 4 centros de datos e incrementad el número de 2 en 2 hasta que se vea la tendencia. Usad el
 algoritmo de Hill Climbing y la misma función heurística que antes.
 */

/**
 * clase encargada de llevar a cabo el experimento 4
 */
public class Experimento4 {
    private int nsens = 100;
    private int ncents = 4;
    private int ntests = 10;
    private EstadoProblema ep;
    private double coste_final;
    private int volumen_final, seed1, seed2;

    void runTests (){
        ep = new EstadoProblema(nsens,ncents,seed1,seed2);
        System.out.println("Volumen inicial: ");
        for(int i = 0; i!= ntests; ++i){

        }
    }

}
