package proyecto.ia;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Experimento 1
        Experimento1 e1 = new Experimento1();
        e1.runTests();


        /*EstadoProblema ep = new EstadoProblema();
        ep.generar_sol_ini_3();
        double costeInicial = ep.coste_total();
        int volumenInicial = ep.volumen_total();
        System.out.println("List of Volumes going through the nodes: ");

        ep.compute_volumes();
        ep.showconnections();
        System.out.println("Total cost: " + ep.coste_total());
        System.out.println("Total volume: " + ep.volumen_total() + "Mb/s");

        HillClimbing hc = new HillClimbing();
        ep = hc.getBestSolution(ep);
        ep.Output();
        ep.showconnections();
        double costeFinal = ep.coste_total();
        int volumenFinal = ep.volumen_total();
        System.out.println("Costes antes y después: " +costeInicial+ " "+ costeFinal+
                                "\nVolumenes antes y después: " + volumenInicial +" " +  volumenFinal);
                                */
    }
}
