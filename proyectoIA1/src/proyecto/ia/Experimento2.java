package proyecto.ia;


/*
Determinar qué estrategia de generación de la solución inicial da mejores resultados para la función
heurística usada en el apartado anterior, con el escenario del apartado anterior y usando el algoritmo
de Hill Climbing. A partir de estos resultados deberéis fijar también la estrategia de generación de la
solución inicial para el resto de experimentos
 */
public class Experimento2 {
    private final static int nsensores = 100;
    private final static int ncentros = 4;
    private EstadoProblema ep;


    public Experimento2() {
        ep = new EstadoProblema(nsensores, ncentros);
        ep.generar_sol_ini_1();
        System.out.println("CONFIGURACIÓN INICIAL:");
        ep.showconnections();
    }

    public void testGen1(){     //comprobar los resultados del primer generador de soluciones iniciales

    }
    public void testGen2(){     //comprobar los resultados del segundo generador de soluciones iniciales

    }
    public void testGen3(){     //comprobar los resultados del tercer generador de soluciones iniciales

    }
}
