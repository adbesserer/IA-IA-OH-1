package proyecto.ia;


/*
Determinar qué estrategia de generación de la solución inicial da mejores resultados para la función
heurística usada en el apartado anterior, con el escenario del apartado anterior y usando el algoritmo
de Hill Climbing. A partir de estos resultados deberéis fijar también la estrategia de generación de la
solución inicial para el resto de experimentos
 */

import java.util.Random;

/**
 * Clase que encapsula los datos y métodos del experimento 2
 */
public class Experimento2 {
    private final static int nsensores = 100;
    private final static int ncentros = 4;
    private EstadoProblema ep;
    private double coste_final;
    private int volumen_final, seed1, seed2;


    public void testGen1() {     //comprobar los resultados del primer generador de soluciones iniciales
        ep = new EstadoProblema(nsensores, ncentros,seed1,seed2);
        ep.generar_sol_ini_1();
        run();
        System.out.println("El coste final usando el generador 1 es: " + coste_final + ", y el volumen es: " + volumen_final);
    }
    public void testGen2(){     //comprobar los resultados del segundo generador de soluciones iniciales
        ep = new EstadoProblema(nsensores, ncentros,seed1,seed2);
        ep.generar_sol_ini_2();
        run();
        System.out.println("El coste final usando el generador 2 es: " + coste_final + ", y el volumen es: " + volumen_final);
    }
    public void testGen3(){     //comprobar los resultados del tercer generador de soluciones iniciales
        ep = new EstadoProblema(nsensores, ncentros,seed1,seed2);
        ep.generar_sol_ini_3();
        run();
        System.out.println("El coste final usando el generador 3 es: " + coste_final + ", y el volumen es: " + volumen_final);
    }
    private void  run() {
        ep.compute_volumes();
        HillClimbing hc = new HillClimbing();
        EstadoProblema best = hc.getBestSolution(new EstadoProblema(ep), true, true); //switch= false, change = true
        volumen_final = best.volumen_total();
        coste_final = best.coste_total();
    }
    public void runTests(){
        Random r = new Random();
        seed1   = r.nextInt();
        seed2   = r.nextInt();
        testGen1();
        testGen2();
        testGen3();
    }
}
