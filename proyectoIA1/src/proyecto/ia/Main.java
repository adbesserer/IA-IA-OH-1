package proyecto.ia;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EstadoProblema ep = new EstadoProblema();
        ep.generar_sol_ini();

        System.out.println("Introduce las claves de los sensores cuyos cables quieres intercambiar");
        Scanner sc = new Scanner(System.in);
        Integer k1 = new Integer(sc.nextInt());
        Integer k2 = new Integer(sc.nextInt());
        ep.switchcables(k1,k2);
        ep.showconnections();
    }
}
