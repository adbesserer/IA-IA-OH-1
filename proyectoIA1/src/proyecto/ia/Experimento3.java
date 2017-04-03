package proyecto.ia;

/**
 * Created by adriamunuera on 3/4/17.
 */
public class Experimento3 {
    private final static int nsensores = 100;
    private final static int ncentros = 4;
    private EstadoProblema ep;


    public Experimento3() {
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
