package proyecto.ia;

/*
Determinar qué conjunto de operadores da mejores resultados para una función heurística que optimice
el criterio de calidad del problema (3.2) con un escenario en el que el número de centros de datos es 4
y el de sensores es 100. Deberéis usar el algoritmo de Hill Climbing. Escoged una de las estrategias de
inicialización de entre las que proponéis. A partir de estos resultados deberéis fijar los operadores para
el resto de experimentos. Pensad que con estas proporciones, se podrán transmitir todos los datos.
 */

/**
 * Clase que encapsula los métodos y datos necesarios para el experimento 1
 */
public class Experimento1 {
    private final static int nsensores = 100;
    private final static int ncentros = 4;
    private EstadoProblema ep;
    private double coste_inicial, coste_final;
    private int volumen_inicial, volumen_final;

    public Experimento1() {
        ep = new EstadoProblema(nsensores, ncentros);
        ep.generar_sol_ini_1();
        System.out.println("CONFIGURACIÓN INICIAL:");
        ep.showconnections();
        ep.compute_volumes();
        coste_inicial   = ep.coste_total();
        volumen_inicial = ep.volumen_total();
    }

    public void testSwitch(){       // comprobar los resultados usando solo el operador switchcables
        HillClimbing hc = new HillClimbing();
        EstadoProblema best = hc.getBestSolution(new EstadoProblema(ep), true, false); //switch= true, change = false
        volumen_final = best.volumen_total();
        coste_final   = best.coste_total();

        System.out.println("La mejora en coste usando sólo el operador de switch cables es :"
                        + (coste_final-coste_inicial));
        System.out.println("La diferencia en volumen es" + (volumen_final-volumen_inicial));
    }
    public void testChange(){       // comprobar los resultados usando solo el operador change cable
        HillClimbing hc = new HillClimbing();
        EstadoProblema best = hc.getBestSolution(new EstadoProblema(ep), false, true); //switch= false, change = true
        volumen_final = best.volumen_total();
        coste_final   = best.coste_total();

        System.out.println("La mejora en coste usando sólo el operador de switch cables es :"
                + (coste_final-coste_inicial));
        System.out.println("La diferencia en volumen es" + (volumen_final-volumen_inicial));
    }
    public void testBoth(){         // comprobar los resultados usando ambos operadores: esperamos el mejor rendimiento aqui
        HillClimbing hc = new HillClimbing();
        EstadoProblema best = hc.getBestSolution(new EstadoProblema(ep), true, true); //switch= true, change = true
        volumen_final = best.volumen_total();
        coste_final   = best.coste_total();

        System.out.println("La mejora en coste usando sólo el operador de switch cables es :"
                + (coste_final-coste_inicial));
        System.out.println("La diferencia en volumen es" + (volumen_final-volumen_inicial));
    }
    public void run(){ // corre los tres tests
        testChange();
        testSwitch();
        testBoth();
    }

}
