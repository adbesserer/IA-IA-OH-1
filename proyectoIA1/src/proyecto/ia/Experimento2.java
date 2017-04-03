package proyecto.ia;


/*
Determinar qué estrategia de generación de la solución inicial da mejores resultados para la función
heurística usada en el apartado anterior, con el escenario del apartado anterior y usando el algoritmo
de Hill Climbing. A partir de estos resultados deberéis fijar también la estrategia de generación de la
solución inicial para el resto de experimentos
 */

/**
 * Clase que encapsula los datos y métodos del experimento 2
 */
public class Experimento2 {
    private final static int nsensores = 100;
    private final static int ncentros = 4;
    private EstadoProblema ep;
    private double coste_inicial, coste_final;
    private int volumen_inicial, volumen_final;

    public Experimento2() {
        ep = new EstadoProblema(nsensores, ncentros);
    }

    public void testGen1() {     //comprobar los resultados del primer generador de soluciones iniciales
        ep.generar_sol_ini_1();
        run();
        System.out.println("La mejora en coste y el cambio en volumen con el generador 1 son respectivamente: "
                            + (coste_final-coste_inicial) + (volumen_final-volumen_inicial));
    }
    public void testGen2(){     //comprobar los resultados del segundo generador de soluciones iniciales
        ep.generar_sol_ini_2();
        run();
        System.out.println("La mejora en coste y el cambio en volumen con el generador 1 son respectivamente: "
                + (coste_final-coste_inicial) + (volumen_final-volumen_inicial));
    }
    public void testGen3(){     //comprobar los resultados del tercer generador de soluciones iniciales
        ep.generar_sol_ini_3();
        run();
        System.out.println("La mejora en coste y el cambio en volumen con el generador 1 son respectivamente: "
                + (coste_final-coste_inicial) + (volumen_final-volumen_inicial));
    }
    public void run() {
        HillClimbing hc = new HillClimbing();
        EstadoProblema best = hc.getBestSolution(new EstadoProblema(ep), true, true); //switch= false, change = true
        volumen_final = best.volumen_total();
        coste_final = best.coste_total();
    }
}
