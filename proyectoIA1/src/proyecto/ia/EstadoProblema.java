package proyecto.ia;

import IA.Red.*;
import java.util.Scanner;
import java.util.*;

/**
 * Created by idhrenniel on 24/03/17.
 */
public class EstadoProblema {

    private Sensores ss;
    private CentrosDatos cs;

    HashMap<Sensor, ArrayList<pair>> sensorMap = new HashMap<>();
    HashMap<Centro, ArrayList<pair>> centerMap = new HashMap<>();

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

        System.out.println("Entre dos semillas diferentes, guapo.");
        int seed1   = sc.nextInt();
        int seed2   = sc.nextInt();

        while(seed1 == seed2){
            System.out.println("Las semillas no pueden ser iguales. Introduzca dos de nuevo.");
            seed1   = sc.nextInt();
            seed2   = sc.nextInt();
        }

        ss = new Sensores(nSensors, seed1);
        cs = new CentrosDatos(nCentros, seed2);

        for(int i = 0; i != ss.size(); ++i){
            Sensor s = ss.get(i);
            ArrayList<pair> conexiones = new ArrayList<pair>();
            sensorMap.put(s,conexiones);
        }

        for(int i=0; i != cs.size(); ++i){
            Centro c = cs.get(i);
            ArrayList<pair> conexiones = new ArrayList<pair>();
            centerMap.put(c, conexiones);
        }

        Output();

    }

    void Output(){
        System.out.println("SENSORES");
        for(Sensor key: sensorMap.keySet()){
            System.out.println(key.getCoordX()+" "+key.getCoordY());
            conectarSS(key, key);
        }

        System.out.println("CENTROS");
        for(Centro key: centerMap.keySet()){
            System.out.println(key.getCoordX()+" "+key.getCoordY());
        }
    }

    //OPERADORES

    public void conectarSS(Sensor s1, Sensor s2) {
        pair p = new pair(s2.getCoordX(), s2.getCoordY());
        sensorMap.get(s1).add(p);
        p.x = s1.getCoordX(); p.y = s1.getCoordY();
        sensorMap.get(s2).add(p);
    }

    public void conectarSC(Sensor s, Centro c) {
        pair p = new pair(c.getCoordX(), c.getCoordY());
        sensorMap.get(s).add(p);
        p.x = s.getCoordX(); p.y = s.getCoordY();
        centerMap.get(c).add(p);
    }

    public void desconectarSS(Sensor s1, Sensor s2){
        pair p = new pair(s2.getCoordX(), s2.getCoordY());
        sensorMap.get(s1).remove(p);
        p.x = s1.getCoordX(); p.y = s1.getCoordY();
        sensorMap.get(s2).remove(p);
    }

    public void desconectarSC(Sensor s, Centro c){
        pair p = new pair(c.getCoordX(), c.getCoordY());
        sensorMap.get(s).remove(p);
        p.x = s.getCoordX(); p.y = s.getCoordY();
        centerMap.get(c).remove(p);
    }

}
