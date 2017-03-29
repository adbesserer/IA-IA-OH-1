package proyecto.ia;

import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.Scanner;

/**
 * Created by idhrenniel on 24/03/17.
 */
public class EstadoProblema {

    private Sensores s;
    private CentrosDatos c;


    EstadoProblema(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Entre numero de sensores.");
        int nSensors = sc.nextInt();
        while(nSensors < 0){
            System.out.println("El numero de sensores no puede ser negativo.");
            nSensors = sc.nextInt();
        }

        System.out.println("Entre numero de centros.");
        int nCentros = sc.nextInt();
        while(nCentros < 0){
            System.out.println("El numero de centros no puede ser negativo.");
            nCentros = sc.nextInt();
        }

        System.out.println("Entre su semilla, guapo.");
        int seed     = sc.nextInt();
        while(seed < 0){
            System.out.println("El numero de la semilla no puede ser negativo.");
            seed     = sc.nextInt();
        }

        s = new Sensores(nSensors, seed);
        c = new CentrosDatos(nCentros, seed);
    }



}
