package proyecto.ia;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EstadoProblema ep = new EstadoProblema();
        ep.generar_sol_ini_1();
        double a = ep.coste_total();
        Scanner sc = new Scanner(System.in);
        String command;
        while(true) {
            System.out.println("List of Volumes going through the nodes: ");
            ep.compute_volumes();
            ep.showconnections();
            System.out.println("Total cost: " + ep.coste_total());
            System.out.println("Total volume: " + ep.volumen_total() + "Mb/s");
            //System.out.println("Write a command (e.g. change or switch");
            //command = new String(sc.next());
            /*if(command.equals("change")) {
                System.out.println("Introduce la clave del sensor y la del nuevo destino del cable");
                Integer k1 = new Integer(sc.nextInt());
                Integer k2 = new Integer(sc.nextInt());
                ep.changecable(k1, k2);
            }
            else if(command.equals("switch")){
                System.out.println("Introduce las claves de los sensores cuyos cables quieres intercambiar");
                Integer k1 = new Integer(sc.nextInt());
                Integer k2 = new Integer(sc.nextInt());
                ep.switchcables(k1,k2);
            }*/

            ////////////////////////////////////////////////////////////////////////////
            //MAMASO Y EL CARBASO
            ////////////////////////////////////////////////////////////////////////////
            //System.out.println("1. HILL CLIMBING\n2. SIMULATED ANNEALING");
            HillClimbing hc = new HillClimbing();

            ep = hc.getBestSolution(ep);

            System.out.println("\n\n\nAQUI ESTA");
            ep.Output();

            ep.showconnections();

            double b = ep.coste_total();

            System.out.println("COSTES TOTALES\n"+a+"\n"+b+"\n---------------------------");
            while(true);
        }
    }
}
