package proyecto.ia;
import proyecto.ia.EstadoProblema;
import proyecto.ia.HillClimbing;

import java.util.Random;
import java.util.Random;

/**
 * Clase que encapsula los datos y métodos del experimento 6
 */
public class Experimento6 {
    private int nsens = 100;
    private int ncents = 0;
    private int ntests = 5;
    private EstadoProblema ep;
    private double coste_final;
    private int volumen_final, seed1, seed2;

    /**
     * Función que comienza el Experimento 6.
     */
    void runTests (){
        Random r = new Random();
        seed1   = r.nextInt();
        seed2   = r.nextInt();

        for(int i = 0; i!= ntests; ++i){
            long time_start, time_end;
            time_start = System.currentTimeMillis();
            ncents = ncents + 2;
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
        this.ncents = 0;
        for(int i = 0; i!= ntests; ++i){
            long time_start, time_end;
            time_start = System.currentTimeMillis();
            ncents = ncents + 2;
            ep = new EstadoProblema(nsens,ncents,seed1,seed2);
            ep.generar_sol_ini_3();
            ep.compute_volumes();

            ep.compute_volumes();
            SimulatedAnnealing SA = new SimulatedAnnealing(25000,0.0375);
            EstadoProblema best = SA.getBestSolution(ep);
            volumen_final = best.volumen_total();
            coste_final = best.coste_total();
            System.out.println("Nº DE CENTROS = " + ncents+ "; Nº DE SENSORES = "+ nsens);
            System.out.println("Coste final:"+ coste_final + "; Volumen final: " + volumen_final +"\n");

            time_end = System.currentTimeMillis();
            System.out.println("Tiempo total: "+ (( time_end - time_start )/1000) +"s");
        }
    }
}
