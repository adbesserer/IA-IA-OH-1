package proyecto.ia;

import IA.Red.*;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.security.Key;
import java.util.Scanner;
import java.util.*;

/**
 * La clase que representa el estado del problema
 */
public class EstadoProblema {
    public ArrayList<CenterData> cds = new ArrayList<CenterData>();
    public ArrayList<SensorData> sds = new ArrayList<SensorData>();

    //solo necesito saber cual de ellos es de cd y sd
    //el primero siempre es sensor
    private HashMap<Integer, Integer> connectionsMap = new HashMap<>();

    //================================================================================
    // Creadora
    //================================================================================

    /**
     * creadora por defecto, pedirá que se introduzcan las variables del problema
     */
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

    /**
     * Inicializa el estado del problema con un número determinado de sensores y centros
     * @param nsens
     * @param ncents
     */
    public EstadoProblema(int nsens, int ncents){
        if(nsens < 1 || ncents < 1)
                System.out.println("Tanto el numero de sensores como de centros debe ser > 0");
        else{
            Random r = new Random();
            int seed1 = r.nextInt();
            int seed2 = r.nextInt();
            Sensores ss = new Sensores(nsens, seed1);
            CentrosDatos cs = new CentrosDatos(ncents, seed2);

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
        }

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

    /**
     *
     * @param keyS1
     * @param keyS2
     * NB: los operadores ya no consideran invalidos los cambios que hacen que un elemento se pase de su volumen maximo,
     * simplemente lo fijan al maximo y lo que sobra se pierde
     */
    public void switchcables (Integer keyS1, Integer keyS2){ //intercambiar la cosa a la que apuntan dos sensores
        Integer keydest1,keydest2;
        double volumen1,volumen2;
        keydest1=connectionsMap.get(keyS1);
        keydest2=connectionsMap.get(keyS2);
        volumen1=sds.get(keyS1).getVolumen();
        volumen2=sds.get(keyS2).getVolumen();
        //do the switching
        Integer aux = connectionsMap.get(keyS1);
        connectionsMap.put(keyS1,connectionsMap.get(keyS2));
        connectionsMap.put(keyS2,aux);

        //cambiar volumenes
        if(keydest1>= sds.size()) { // = es un centro
            keydest1-= sds.size();
            cds.get(keydest1).setVolumen((int) (cds.get(keydest1).getVolumen() + volumen2 - volumen1  ));
            if(cds.get(keydest1).getVolumen() > 150)
                cds.get(keydest1).setVolumen(150);
            keydest1+=sds.size();
        }else{ //es un sensor
            sds.get(keydest1).setVolumen((int) (sds.get(keydest1).getVolumen() + volumen2 - volumen1  ));
            if(sds.get(keydest1).getVolumen() > sds.get(keydest1).getCapacidad()*3)
                sds.get(keydest1).setVolumen((int)sds.get(keydest1).getCapacidad()*3);
        }
        if(keydest2>= sds.size()) { // = es un centro
            keydest2-= sds.size();
            cds.get(keydest2).setVolumen((int) (cds.get(keydest2).getVolumen() + volumen1 - volumen2  ));
            if(cds.get(keydest2).getVolumen() > 150)
                cds.get(keydest2).setVolumen(150);
            keydest2+=sds.size();
        }else{ //es un sensor
            sds.get(keydest2).setVolumen((int) (sds.get(keydest2).getVolumen() + volumen1 - volumen2  ));
            if(sds.get(keydest2).getVolumen() > sds.get(keydest2).getCapacidad()*3)
                sds.get(keydest2).setVolumen((int)sds.get(keydest2).getCapacidad()*3);
        }
        //no puedes intercambiar cables entre dos sensores si uno apunta al otro
        boolean UNSWITCHABLE = false;
        if(connectionsMap.get(keyS2)==keyS2 || connectionsMap.get(keyS1)==keyS1){
            UNSWITCHABLE=true;
            System.out.println("You cannot switch cables if one is connected to the other");
        }
        //check if switching was correct, else undo
        if(UNSWITCHABLE){
            System.out.println("ERROR: cannot switch cables");
            switchcables(keyS1,keyS2);
        }
    }

    /**
     * pre: KeySource is the key of a sensor and KeyDest is the key of a sensor or center
     * post: KeySource is connected to KeyDest
     * @param KeySource
     * @param KeyDest
     */
    public void changecable(Integer KeySource, Integer KeyDest){  //el sensor Keysource pasa a estar conectado a keydest
        int vol = sds.get(KeySource).getVolumen();
        //cambio
        Integer OldKey = connectionsMap.get(KeySource);
        connectionsMap.put(KeySource,KeyDest);

        //actualizar volumen del nuevo destino
        if(KeyDest >= sds.size()) { // es un centro
            KeyDest-=sds.size();
            cds.get(KeyDest).setVolumen((cds.get(KeyDest).getVolumen() + vol));
            if(cds.get(KeyDest).getVolumen() > 150) {
                cds.get(KeyDest).setVolumen(150);
            }
            KeyDest+=sds.size();
        }
        else{
            sds.get(KeyDest).setVolumen((sds.get(KeyDest).getVolumen() + vol));
            if(sds.get(KeyDest).getVolumen() > sds.get(KeyDest).getCapacidad()*3) {
                sds.get(KeyDest).setVolumen((int) sds.get(KeyDest).getCapacidad() * 3);
            }
        }
        //actualizar volumen del viejo destino
        if(OldKey >= sds.size()) { // es un centro
            OldKey-=sds.size();
            cds.get(OldKey).setVolumen((int) (cds.get(OldKey).getVolumen() - vol));;
            OldKey+=sds.size();
        }
        else{
            sds.get(OldKey).setVolumen((int) (sds.get(OldKey).getVolumen() - vol));
        }
    }

    //================================================================================
    //generador de solucion inicial
    //================================================================================
    /**
     * solucion inicial: conectar los sensores directamente a los centros mientras haya espacio
     * despues, conectar los sensores a otros sensores ya conectados
     */
    public void generar_sol_ini_1(){
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
    }

    /**
     * generador de soluciones iniciales alternativo utilizando modulo y aleatoriedad
     * primero conecta los sensores a los centros distribuidos uniformemente siempre que haya mas sensores que centros
     * despues conecta los sensores que quedan aleatoriamente a otros sensores
     */
    public void generar_sol_ini_2(){
        ArrayList<Integer> conectados = new ArrayList<>();
        for(int i=0; i!=sds.size(); ++i){ //para cada sensor
            int centro = i % cds.size();
            if(cds.get(centro).getnConnexions()<25) {
                conectados.add(i);
                connectionsMap.put(i, centro + sds.size());
                cds.get(centro).setnConnexions(cds.get(centro).getnConnexions()+1);
            }
            else{
                Random r = new Random();
                int sensor = r.nextInt(conectados.size());
                while(sensor==i || sds.get(sensor).getnConnexions()==3){
                    sensor = r.nextInt(conectados.size());
                }
                conectados.add(i);
                connectionsMap.put(i,sensor);
                sds.get(sensor).setnConnexions(sds.get(sensor).getnConnexions()+1);
            }
        }
    }

    /**
     * generador de soluciones alternativo que intenta perder poco volumen
     * los sensores de 5 -> centro, los de 2 ->5 y los de 1->2
     */
    public void generar_sol_ini_3() {
        ArrayList<SensorData> conectados5 = new ArrayList<>();
        ArrayList<SensorData> conectados2 = new ArrayList<>();
        ArrayList<SensorData> conectados1 = new ArrayList<>();
        ArrayList<SensorData> sortedsensors = new ArrayList<>(sds);
        sortedsensors.sort(new sdComparator());
        int i = 0; //i = indice del sensor en sortedsensors
        int n = sortedsensors.size();
        SensorData sd = sortedsensors.get(i);
        while(i < n && sd.getCapacidad()==5){ //conectar a los centros
            int centro = sd.getKey() % cds.size();
            if(cds.get(centro).getnConnexions()<25) {
                conectados5.add(sd);
                connectionsMap.put(sd.getKey(), centro + sds.size());
                cds.get(centro).setnConnexions(cds.get(centro).getnConnexions()+1);
            }
            else {
                int u = 0;
                while(conectados5.get(u).getnConnexions() == 3)++u;
                connectionsMap.put(sd.getKey(), conectados5.get(u).getKey());
                conectados5.get(u).setnConnexions(conectados5.get(u).getnConnexions()+1);
                conectados5.add(sd);
            }
            if(++i < n)
                sd = sortedsensors.get(i);
        }
        if(i==0) generar_sol_ini_2(); //si no hay sensores con 5 de captura, cambiamos de estrategia
        else {
            while (i < n && sd.getCapacidad() == 2) { //conectar a los sensores de 5
                int u = 0;
                while (conectados5.get(u).getnConnexions() == 3) ++u;
                if (u < conectados5.size()) {
                    connectionsMap.put(sd.getKey(), conectados5.get(u).getKey());
                    conectados5.get(u).setnConnexions(conectados5.get(u).getnConnexions() + 1);
                    conectados2.add(sd);
                } else {
                    int j = 0;
                    while (conectados2.get(j).getnConnexions() == 3) ++j;
                    connectionsMap.put(sd.getKey(), conectados2.get(j).getKey());
                    conectados2.get(j).setnConnexions(conectados2.get(j).getnConnexions() + 1);
                    conectados2.add(sd);
                }
                if (++i < n)
                    sd = sortedsensors.get(i);
            }
        if(i== conectados5.size()) conectados2 = conectados5; //si no hay sensores de 2, conectamos los de 1 a los de 5
            while (i < n && sd.getCapacidad() == 1) { // conectar a los sensores de 2
                int u = 0;
                while (conectados2.get(u).getnConnexions() == 3) ++u;
                if (u < conectados2.size()) {
                    connectionsMap.put(sd.getKey(), conectados2.get(u).getKey());
                    conectados2.get(u).setnConnexions(conectados2.get(u).getnConnexions() + 1);
                    conectados1.add(sd);
                } else {
                    int j = 0;
                    while (conectados1.get(j).getnConnexions() == 3) ++j;
                    connectionsMap.put(sd.getKey(), conectados1.get(j).getKey());
                    conectados1.get(j).setnConnexions(conectados1.get(j).getnConnexions() + 1);
                    conectados1.add(sd);
                }
                if (++i < n)
                    sd = sortedsensors.get(i);

            }
        }
    }
    public class sdComparator implements Comparator<SensorData> {
        @Override
        public int compare(SensorData sd1, SensorData sd2) {
            return (int)(sd2.getCapacidad() - sd1.getCapacidad());
        }
    }
    /**
     * funcion que utiliza un toposort para fijar para cada sensor o centro la cantidad de datos que pasan por el
     */
    public void compute_volumes(){
        //necesitamos fijar los volumenes a los valores iniciales para recalcularlos:
        for(CenterData cd : cds){
            cd.setVolumen(0);
        }
        for(SensorData sd : sds){
            sd.setVolumen((int) sd.getCapacidad());
        }
        //primero generamos la ordenación topológica de los sensores
        Integer ge[] = new Integer[sds.size()+cds.size()];
        for(int i = 0;i!=ge.length;++i){    //inicializar vector de incidencias a 0
            ge[i]=0;
        }
        for(int i = 0; i!= sds.size();++i){//rellenar vector de invidencias
            ++ge[connectionsMap.get(i)]; //connectionsmap.get(i) es a lo que apunta el sensor i
        }
        Stack<Integer> s = new Stack<>(); //stack que contiene claves de s. y c.
        for(int u = 0; u != ge.length; ++u){
            if(ge[u]==0)
                s.push(u);
        }
        ArrayList<Integer> L = new ArrayList<>(ge.length); //L contiene los elementos en orden topológico
        while(!s.empty()){
            int u = s.pop();
            L.add(u);
            if(u < sds.size() && --ge[connectionsMap.get(u)] == 0)
                s.push(connectionsMap.get(u));
        }

        //recorrer L actualizando los volumenes de datos
        for(Integer i : L){
            if(i<sds.size()){ //solo recorremos sensores
                int vol = sds.get(i).getVolumen();
                Integer keyDest = connectionsMap.get(i); //keydest es a lo que esta conectado el sensor i
                if(keyDest>=sds.size()) { //keyDest es centro
                    keyDest -= sds.size();
                    cds.get(keyDest).setVolumen(cds.get(keyDest).getVolumen() + vol);
                    if (cds.get(keyDest).getVolumen() > 150)
                        cds.get(keyDest).setVolumen(150);
                    keyDest += sds.size();
                }
                else{//keyDest es sensor
                    sds.get(keyDest).setVolumen(sds.get(keyDest).getVolumen()+vol);
                    if(sds.get(keyDest).getVolumen() > (int) (sds.get(keyDest).getCapacidad())*3)
                        sds.get(keyDest).setVolumen((int)sds.get(keyDest).getCapacidad()*3);
                }
            }
        }
    }


    /**
     *
     * @return la suma de volumenes de datos que llegan a cada sensor y centro
     */
    public int volumen_total(){
        int suma = 0;
        for(SensorData sd : sds){
            suma += sd.getVolumen();
        }
        for(CenterData cd : cds){
            suma += cd.getVolumen();
        }
        return suma;
    }

    /**
     *
     * @return coste total de todas las conexiones en el estado actual
     */

    public double coste_total(){
        compute_volumes();
        int suma = 0;
        for(Integer k : connectionsMap.keySet()){
            Integer keyDest = connectionsMap.get(k);
            pair coordORIG = new pair(sds.get(k).getCoordX(), sds.get(k).getCoordY());
            pair coordDEST;
            if(keyDest>= sds.size()){ //el destino es un centro
                keyDest -= sds.size();
                coordDEST = new pair (cds.get(keyDest).getCoordX(), cds.get(keyDest).getCoordY());
                keyDest += sds.size();
            }else{
                coordDEST = new pair (sds.get(keyDest).getCoordX(), sds.get(keyDest).getCoordY());
            }
            double distancia = Math.sqrt(( Math.pow(coordORIG.x-coordDEST.x,2) + Math.pow(coordORIG.y-coordDEST.y,2)));
            double cost = Math.pow(distancia,2) * sds.get(k).getVolumen();
            suma+=cost;
        }
        return suma;
    }
    //================================================================================
    // Auxiliars, Setters, Getters
    //================================================================================
    public void showconnections(){
        for(Integer k: connectionsMap.keySet()){
            int vol;
            if(k >= sds.size()) { //k es centro de datos
                vol = cds.get(k).getVolumen();
            }
            else vol = sds.get(k).getVolumen();
            System.out.println(k + " está conectado a " + connectionsMap.get(k) +
                " y su volumen de datos es " + vol);
        }
        for(CenterData cd : cds){
            System.out.println("El volumen del centro de datos " + (cd.getKey()+sds.size()) + " es "+ cd.getVolumen());
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

    public double getDistance(Integer i1, Integer i2){
        int posx1, posx2, posy1, posy2;

        //KEY 1
        if(i1 >= sds.size()){ //i1 -> Centro
            posx1 = cds.get(i1 - sds.size()).getCoordX();
            posy1 = cds.get(i1 - sds.size()).getCoordY();
        }
        else{ //i1 -> Sensor
            posx1 = sds.get(i1).getCoordX();
            posy1 = sds.get(i1).getCoordY();
        }

        //KEY 2
        if(i2 >= sds.size()){ //i2 -> Centro
            posx2 = cds.get(i2 - sds.size()).getCoordX();
            posy2 = cds.get(i2 - sds.size()).getCoordY();
        }
        else{ //i2 -> Sensor
            posx2 = sds.get(i2).getCoordX();
            posy2 = sds.get(i2).getCoordY();
        }

        return Math.sqrt(Math.pow((posx1 - posx2), 2) + Math.pow((posy1 - posy2), 2));
    }
}
