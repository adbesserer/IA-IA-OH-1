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

    HashMap<Sensor, pair> sensorMap = new HashMap<>();
    HashMap<Centro, pair> centerMap = new HashMap<>();

    //================================================================================
    // Creadora
    //================================================================================

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

        pair pairVacio = new pair(0, 0);
        for(int i = 0; i != ss.size(); ++i){
            Sensor s = ss.get(i);
            sensorMap.put(s, pairVacio);
        }

        for(int i=0; i != cs.size(); ++i){
            Centro c = cs.get(i);
            centerMap.put(c, pairVacio);
        }
        Output();
    }

    //================================================================================
    // Output
    //================================================================================

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

    //================================================================================
    // Operadors
    //================================================================================
    public void conectarSS(Sensor s1, Sensor s2) {
        if((sensorMap.get(s2).x != s1.getCoordX()) || (sensorMap.get(s2).y != s1.getCoordY())) {
            pair p = new pair(s2.getCoordX(), s2.getCoordY());
            sensorMap.put(s1, p);
        }
    }

    public void conectarSC(Sensor s, Centro c) {
        if(((centerMap.get(c).x+s.getCapacidad()) <= 150) && ((centerMap.get(c).y + 1) >= 25)) {
            pair q = new pair(c.getCoordX(), c.getCoordY());
            sensorMap.put(s, q);
            pair p = centerMap.get(c);
            p.x = p.x + (int)s.getCapacidad();
            p.y = p.y + 1;
        }
    }

    public void desconectarSS(Sensor s1){
        sensorMap.remove(s1);
    }

    public void desconectarSC(Sensor s, Centro c){
        desconectarSS(s);
        pair p = centerMap.get(c);
        p.x = p.x - (int)s.getCapacidad();
        p.y = p.y - 1;
        centerMap.put(c, p);
    }


    public void cambiarCable(Sensor s, Object ini, Object dest) {
        boolean desconectado = false;
        if (ini instanceof Centro) {
            desconectarSC(s, (Centro)ini);
            desconectado = true;

        } else if(ini instanceof Sensor) {
            desconectarSS(s);
            desconectado = true;
        }
        if(desconectado) {
            if (dest instanceof Centro) {
                conectarSC(s, (Centro)ini);

            } else if(dest instanceof Sensor) {
                conectarSS(s, (Sensor)ini);
            }
        }
    }
    //================================================================================
    // Auxiliars, Setters, Getters
    //================================================================================

    HashMap<Sensor, pair> getSensorMap() {
        return this.sensorMap;
    }
}
