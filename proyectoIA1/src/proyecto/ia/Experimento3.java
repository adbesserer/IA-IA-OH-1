package proyecto.ia;

/*
Determinar los parámetros que dan mejor resultado para el Simulated Annealing con el mismo esce-
nario, usando la misma función heurística y los operadores y la estrategia de generación de la solución
inicial escogidos en los experimentos anteriores.
 */
import IA.Connectat.ES;

import java.util.Random;

/**
 * Clase que encapsula los datos y métodos del experimento 3
 */
public class Experimento3 {
    private final static int nsensores = 100;
    private final static int ncentros = 4;
    private EstadoProblema ep;
    private double coste_final;
    private int volumen_final, seed1, seed2;

    private double[] TEMPS = {12000,15000,18000,22000,25000,28000,31000,34000,37000,40000};
    private double cr = 0.0375;

    public void runTests(){
        Random r = new Random();
        seed1   = r.nextInt();
        seed2   = r.nextInt();

        for(int i = 0; i != TEMPS.length; ++i){
            System.out.println("Nº DE PRUEBA "+(i+1));
            double tp = TEMPS[i];
            System.out.println("COOLING RATE = " +cr+ "\nTEMPERATURE = " +tp);

            ep = new EstadoProblema(nsensores,ncentros,seed1,seed2);
            ep.generar_sol_ini_3();
            SimulatedAnnealing SA = new SimulatedAnnealing(tp,cr);
            EstadoProblema best = SA.getBestSolution(ep);

            volumen_final= best.volumen_total();
            coste_final = best.coste_total();
            System.out.println("Volumen final: " + volumen_final + "; " + " Coste final: "+ coste_final + "\n");
        }
    }
}
