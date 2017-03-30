package proyecto.ia;

import IA.Red.*;
import java.util.Scanner;
import java.util.*;

/**
 * Created by idhrenniel on 24/03/17.
 */
public class EstadoProblema {
    ArrayList<CenterData> cd = new ArrayList<CenterData>();
    ArrayList<SensorData> sd = new ArrayList<SensorData>();

    //solo necesito saber cual de ellos es de cs y sd
    HashMap<Integer, Integer> sensorMap = new HashMap<>();

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
        int seed1  = sc.nextInt();
        int seed2  = sc.nextInt();

        while(seed1 == seed2){
            System.out.println("Las semillas no pueden ser iguales. Introduzca dos de nuevo.");
            seed1  = sc.nextInt();
            seed2  = sc.nextInt();
        }

        Sensores ss = new Sensores(nSensors, seed1);
        CentrosDatos cs = new CentrosDatos(nCentros, seed2);

        pair pairVacio = new pair(0, 0);
        for(Integer i = 0; i != ss.size(); ++i){
            Sensor s = ss.get(i);
            SensorData sData = new SensorData(s, i);
            sd.add(i, sData);
            sensorMap.put(i, -1);
        }

        for(Integer i=0; i != cs.size(); ++i){
            Centro c = cs.get(i);
            CenterData cData = new CenterData(c, i);
            cd.add(i, cData);
        }
        Output();
    }

    //================================================================================
    // Output
    //================================================================================

    void Output(){
        System.out.println("SENSORES");
        for(Integer key: sensorMap.keySet()){
            System.out.println(sd.get(key).getCoordX()+" "+sd.get(key).getCoordY());
        }

        System.out.println("CENTROS");
        for(Integer i=0; i != cd.size(); i++){
            System.out.println(cd.get(i).getCoordX()+" "+cd.get(i).getCoordY());
        }
    }

    //================================================================================
    // Operadors
    //================================================================================
    public void conectarSS(SensorData s1, SensorData s2) {
        if(sensorMap.get(s2) == -1 || (s2.getCapacidad()*3 >= (s2.getVolumen() + s1.getVolumen()))) {
            sensorMap.put(s1.getKey(), s2.getKey());
            s2.setVolumen(s2.getVolumen() + s1.getVolumen());
            sd.add(s2.getKey(), s2);
        }
    }

    public void conectarSC(SensorData s, CenterData c) {
        if(((c.getCapacitat()+s.getVolumen()) <= 150) && ((c.getnConnexions() + 1) >= 25)) {
            pair q = new pair(c.getCoordX(), c.getCoordY());
            sensorMap.put(s.getKey(), sd.size() + c.getKey()); //TODO: Connexions amb centres. Faig una guarrada de mentres  que es que per a tot index referit a centre es sensors.size()+Key del centre
            c.setCapacitat(c.getCapacitat()+s.getVolumen());
            c.setnConnexions(c.getnConnexions() + 1);
            cd.add(c.getKey(), c);
        }
    }

    public void desconectarSS(SensorData s1){
        boolean esCentre = false;
        Integer vol = s1.getVolumen();
        Integer key = s1.getKey();
        while(!esCentre) {
            key = sensorMap.get(key);
            if(key >= sd.size()) {
                esCentre = true;
                CenterData cData = cd.get(key - sd.size()); //TODO: consquencies de la guarrada maxima feta més amunt.
                cData.setCapacitat(cData.getCapacitat() - vol);
            } else {
                SensorData sData  = sd.get(key);
                sData.setVolumen(sData.getVolumen() - vol);
            }
        }
        sensorMap.remove(s1.getKey());
    }

    public void desconectarSC(SensorData s, CenterData c){
        desconectarSS(s);
        c.setCapacitat(c.getCapacitat() - s.getVolumen());
        c.setnConnexions(c.getnConnexions() - 1);
        cd.add(c.getKey(), c);
    }


    public void cambiarCable(SensorData s, Object ini, Object dest) {
        boolean desconectado = false;
        if (ini instanceof CenterData) {
            desconectarSC(s, (CenterData) ini);
            desconectado = true;

        } else if(ini instanceof SensorData) {
            desconectarSS(s);
            desconectado = true;
        }
        if(desconectado) {
            if (dest instanceof CenterData) {
                conectarSC(s, (CenterData)ini);

            } else if(dest instanceof Sensor) {
                conectarSS(s, (SensorData) ini);
            }
        }
    }
    //================================================================================
    // Auxiliars, Setters, Getters
    //================================================================================

    HashMap<Integer, Integer> getSensorMap() {
        return this.sensorMap;
    }

    Sensor getSensorAt(Integer i) {
        return sd.get(i).getSensor();
    }

    SensorData getSensorDataAt(Integer i) {
        return sd.get(i);
    }
}
