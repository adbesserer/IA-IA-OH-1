package proyecto.ia;

import IA.Red.*;

import java.util.Scanner;
import java.util.*;

public class EstadoProblema {
    public ArrayList<CenterData> cds = new ArrayList<CenterData>();
    public ArrayList<SensorData> sds = new ArrayList<SensorData>();

    //solo necesito saber cual de ellos es de cd y sd
    //el primero siempre es sensor
    private HashMap<Integer, Integer> connectionsMap = new HashMap<>();

    //================================================================================
    // Creadora
    //================================================================================

    public EstadoProblema(){
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

    public void Output(){
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

    public void switchcables (Integer keyS1, Integer keyS2){ //intercambiar la cosa a la que apuntan dos sensores
        Integer keydest1,keydest2;
        double capacidad1,capacidad2;
        keydest1=connectionsMap.get(keyS1);
        keydest2=connectionsMap.get(keyS2);
        capacidad1=sds.get(keyS1).getCapacidad();
        capacidad2=sds.get(keyS2).getCapacidad();
        boolean UNSWITCHABLE = false;
        //do the switching
        Integer aux = connectionsMap.get(keyS1);
        connectionsMap.put(keyS1,connectionsMap.get(keyS2));
        connectionsMap.put(keyS2,aux);

        //cambiar volumenes
        if(keydest1>= sds.size()) { // = es un centro
            keydest1-= sds.size();
            cds.get(keydest1).setVolumen((int) (cds.get(keydest1).getVolumen() + capacidad2 - capacidad1  ));
            if(cds.get(keydest1).getVolumen() > 150) UNSWITCHABLE = true;
        }else{ //es un sensor
            sds.get(keydest1).setVolumen((int) (sds.get(keydest1).getVolumen() + capacidad2 - capacidad1  ));
            if(sds.get(keydest1).getVolumen() > sds.get(keydest1).getCapacidad()*3) UNSWITCHABLE = true;
        }
        if(keydest2>= sds.size()) { // = es un centro
            keydest2-= sds.size();
            cds.get(keydest2).setVolumen((int) (cds.get(keydest2).getVolumen() + capacidad1 - capacidad2  ));
            if(cds.get(keydest2).getVolumen() > 150) UNSWITCHABLE = true;
        }else{ //es un sensor
            sds.get(keydest2).setVolumen((int) (sds.get(keydest2).getVolumen() + capacidad1 - capacidad2  ));
            if(sds.get(keydest2).getVolumen() > sds.get(keydest2).getCapacidad()*3) UNSWITCHABLE = true;
        }
        //check if switching was correct, else undo
        if(UNSWITCHABLE){
            System.out.println("VOLUME ERROR: cannot switch cables");
            switchcables(keyS1,keyS2);
        }
    }
    public void changecable(Integer KeySource, Integer KeyDest){  //el sensor Keysource pasa a estar conectado a keydest

    }

    //================================================================================
    //generador de solucion inicial
    //================================================================================
    //solucion inicial: conectar los sensores directamente a los centros mientras haya espacio
    //despues, conectar los sensores a otros sensores ya conectados
    public void generar_sol_ini(){
        int j = 0; // u es el indice del primer sensor desconectado
        for(int i = 0; i != cds.size();++i){
            while(cds.get(i).getnConnexions()<25 && j != sds.size()){
                Integer key1 = sds.get(j).getKey();
                Integer key2 = cds.get(i).getKey();
                connectionsMap.put(key1,key2+sds.size()); //las claves de los centros empiezan donde acaban las de los sensores
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
        if(j!= sds.size()) //tenemos un problema: hay sensores que no podemos conectar(no deberia pasar)
            System.out.println("ERROR: HAY SENSORES QUE NO PUEDEN SER CONECTADOS");
        for(Integer k: connectionsMap.keySet()){
            System.out.println(k + " está conectado a " + connectionsMap.get(k));
        }
        showconnections();
    }
    //================================================================================
    // Auxiliars, Setters, Getters
    //================================================================================
    public void showconnections(){
        for(Integer k: connectionsMap.keySet()){
            System.out.println(k + " está conectado a " + connectionsMap.get(k));
        }
    }
    public HashMap<Integer, Integer> getConnectionsMap() {
        return this.connectionsMap;
    }

    //por qué lo coge de el array de sensor data y no de la clase Sensores?
    public IA.Red.Sensor getSensorAt(Integer i) {
        return sds.get(i).getSensor();
    }

    public SensorData getSensorDataAt(Integer i) {
        return sds.get(i);
    }
}
