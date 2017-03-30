package proyecto.ia;

import IA.Red.*;

import java.util.Scanner;
import java.util.*;

public class EstadoProblema {
    ArrayList<CenterData> cds = new ArrayList<CenterData>();
    ArrayList<SensorData> sds = new ArrayList<SensorData>();

    //solo necesito saber cual de ellos es de cd y sd
    //el primero siempre es sensor
    HashMap<Integer, Integer> connectionsMap = new HashMap<>();

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
            sds.add(i, sData);
            connectionsMap.put(i, -1);
        }

        for(Integer i=0; i != cs.size(); ++i){
            Centro c = cs.get(i);
            CenterData cData = new CenterData(c, i);
            cds.add(i, cData);
        }
        Output();
    }

    //================================================================================
    // Output
    //================================================================================

    void Output(){
        System.out.println("SENSORES");
        for(Integer key: connectionsMap.keySet()){
            System.out.println(sds.get(key).getCoordX()+" "+sds.get(key).getCoordY());
        }

        System.out.println("CENTROS");
        for(Integer i=0; i != cds.size(); i++){
            System.out.println(cds.get(i).getCoordX()+" "+cds.get(i).getCoordY());
        }
    }

    //================================================================================
    // Operadors
    //================================================================================
    public void conectarSS(SensorData s1, SensorData s2) {
        if(connectionsMap.get(s2) == -1 || (s2.getCapacidad()*3 >= (s2.getVolumen() + s1.getVolumen()))) {
            connectionsMap.put(s1.getKey(), s2.getKey());
            s2.setVolumen(s2.getVolumen() + s1.getVolumen());
            sds.add(s2.getKey(), s2);
        }
    }

    public void conectarSC(SensorData s, CenterData c) {
        if(((c.getCapacitat()+s.getVolumen()) <= 150) && ((c.getnConnexions() + 1) >= 25)) {
            pair q = new pair(c.getCoordX(), c.getCoordY());
            connectionsMap.put(s.getKey(), sds.size() + c.getKey()); //TODO: Connexions amb centres. Faig una guarrada de mentres  que es que per a tot index referit a centre es sensors.size()+Key del centre
            c.setCapacitat(c.getCapacitat()+s.getVolumen());
            c.setnConnexions(c.getnConnexions() + 1);
            cds.add(c.getKey(), c);
        }
    }

    public void desconectarSS(SensorData s1){
        boolean esCentre = false;
        Integer vol = s1.getVolumen();
        Integer key = s1.getKey();
        while(!esCentre) {
            key = connectionsMap.get(key);
            if(key >= sds.size()) {
                esCentre = true;
                CenterData cData = cds.get(key - sds.size()); //TODO: consquencies de la guarrada maxima feta més amunt.
                cData.setCapacitat(cData.getCapacitat() - vol);
            } else {
                SensorData sData  = sds.get(key);
                sData.setVolumen(sData.getVolumen() - vol);
            }
        }
        connectionsMap.remove(s1.getKey());
    }

    public void desconectarSC(SensorData s, CenterData c){
        desconectarSS(s);
        c.setCapacitat(c.getCapacitat() - s.getVolumen());
        c.setnConnexions(c.getnConnexions() - 1);
        cds.add(c.getKey(), c);
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
    //generador de solucion inicial
    //================================================================================
    //solucion mierder
    public void generar_sol_ini(){
        int j = 0; // u es el indice del primer sensor desconectado
        for(int i = 0; i != cds.size();++i){
            while(cds.get(i).getnConnexions()<25 && j != sds.size()){
                Integer key1 = sds.get(j).getKey();
                Integer key2 = cds.get(i).getKey();
                connectionsMap.put(key1,key2);
                cds.get(i).setnConnexions(cds.get(i).getnConnexions()+1);
                ++j;
            }
        }
        if(j!= sds.size()) { //todavia quedan sensores sin conectar y los centros de datos estan llenos
            for(int i = 0; i != j; ++i){
                while(sds.get(i).getnConnexions()<3 && j != sds.size()){
                    Integer key1 = sds.get(j).getKey();
                    Integer key2 = sds.get(i).getKey();
                    connectionsMap.put(key1,key2);
                    sds.get(i).setnConnexions(sds.get(i).getnConnexions()+1);
                    ++j;
                }
            }
        }
        if(j!= sds.size()) //tenemos un problema: hay sensores que no podemos conectar
            System.out.println("ERROR: HAY SENSORES QUE NO PUEDEN SER CONECTADOS");
        for(Integer k: connectionsMap.keySet()){
            System.out.println(k + " está conectado a " + connectionsMap.get(k));
        }
    }
    //================================================================================
    // Auxiliars, Setters, Getters
    //================================================================================

    HashMap<Integer, Integer> getSensorMap() {
        return this.connectionsMap;
    }

    IA.Red.Sensor getSensorAt(Integer i) {
        return sds.get(i).getSensor();
    }

    SensorData getSensorDataAt(Integer i) {
        return sds.get(i);
    }
}
