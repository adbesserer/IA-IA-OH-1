package proyecto.ia;

/*
Determinar los parámetros que dan mejor resultado para el Simulated Annealing con el mismo esce-
nario, usando la misma función heurística y los operadores y la estrategia de generación de la solución
inicial escogidos en los experimentos anteriores.
 */
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

    public void runTests(){
        Random r = new Random();
        seed1   = r.nextInt();
        seed2   = r.nextInt();
    }
}
