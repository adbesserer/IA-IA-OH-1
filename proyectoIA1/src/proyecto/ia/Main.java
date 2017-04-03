package proyecto.ia;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection, nexp;

        while (true) {
            System.out.println("\nENTER THE NUMBER OF THE EXPERIMENT YOU WANT TO RUN");
            selection = sc.nextInt();

            if (selection == 1) {
                System.out.println("EXPERIMENTO 1: \n");
                nexp = 10;
                for (int i = 1; i != nexp + 1; ++i) {
                    System.out.println("\nNº DE EJECUCIÓN " + i + "\n");
                    Experimento1 e1 = new Experimento1();
                    e1.runTests();
                }
            }
            if (selection == 2) {
                System.out.println("EXPERIMENTO 2: \n");
                nexp = 10;
                for(int i = 1; i!= nexp+1; ++i){
                    System.out.println("\nNº DE EJECUCIÓN " + i + "\n");
                    Experimento2 e2 = new Experimento2();
                    e2.runTests();
                }

            }
            if (selection == 3){
                System.out.println("EXPERIMENTO 3: \n");
            }
        }
    }
}

