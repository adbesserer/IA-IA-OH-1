package proyecto.ia;

/*
 Dado el escenario de los apartados anteriores, estudiad como evoluciona el tiempo de ejecución para
 hallar la solución para valores crecientes de los parámetros siguendo la proporción 4:100. Comenzad
 con 4 centros de datos e incrementad el número de 2 en 2 hasta que se vea la tendencia. Usad el
 algoritmo de Hill Climbing y la misma función heurística que antes.
 */

import java.util.Random;

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

    /**
     * Función que comienza el Experimento 4.
     */
    void runTests (){
        Random r = new Random();
        seed1   = r.nextInt();
        seed2   = r.nextInt();

        for(int i = 0; i!= ntests; ++i){
            long time_start, time_end;
            time_start = System.currentTimeMillis();
            ncents= 4 + 2*i;
            nsens = (ncents*100)/4;
            ep = new EstadoProblema(nsens,ncents,seed1,seed2);
            ep.generar_sol_ini_3();
            ep.compute_volumes();

            ep.compute_volumes();
            HillClimbing hc = new HillClimbing();
            EstadoProblema best = hc.getBestSolution(new EstadoProblema(ep), true, true); //switch= false, change = true
            volumen_final = best.volumen_total();
            coste_final = best.coste_total();
            System.out.println("Nº DE CENTROS = " + ncents+ "; Nº DE SENSORES = "+ nsens);
            System.out.println("Coste final:"+ coste_final + "; Volumen final: " + volumen_final +"\n");

            time_end = System.currentTimeMillis();
            System.out.println("Tiempo total: "+ (( time_end - time_start )/1000) +"s");
        }
    }

}
