package proyecto.ia;

import java.util.*;

public class HeuristicFunction {
    private Double minCost;
    private Integer maxVTransmissio;
    private Set<Integer> sensorsFound = new HashSet<Integer>();
    private Set<Integer> sensorsNotExplored = new HashSet<Integer>();

    /**
     * Pre: ---
     * Post: crea un nuevo objeto HeuristicFunction
     */
    public HeuristicFunction() {
        this.maxVTransmissio = 0;
        this.minCost = 0.0;
    }

    /**
     * Pre: ----
     * Post: devuevle el valor heurístico para el EstadoProblema n
     * @param n debería ser un EstadoProblema
     * @return double que indica el valor heurístico de n
     */
    public double getHeuristicValue(Object n) {
        /* Integer penalty = 5; */
        Double returnValue = 0.0;
        EstadoProblema state = (EstadoProblema) n;
        Integer volumenTotal = state.volumen_total();
        Double costeTotal = state.coste_total();
        returnValue = (costeTotal - this.minCost)/1000; /* modificant aquest parametre modifiquem cost i volum */
        if(costeTotal < minCost) {
            this.minCost = costeTotal;
        }
        returnValue = returnValue + (maxVTransmissio - volumenTotal)/(4*state.sds.size());
        if(volumenTotal > maxVTransmissio) {
            this.maxVTransmissio = volumenTotal;

        }
        return returnValue;
    }

    /**
     * Pre: ---
     * Post: devuelve el número de sensores desconectados de un centro
     * @param state
     * @return devuelve el número de sensores desconectados de un centro
     */
    public Integer areAllSensorsConnected(EstadoProblema state) {
        this.sensorsFound.clear();
        HashMap<Integer, Integer> connections = state.getConnectionsMap();
        for(int i = 0; i < state.sds.size(); i++) {
            this.sensorsNotExplored.add(i);
        }
        while (!sensorsNotExplored.isEmpty()) {
            Iterator it = sensorsNotExplored.iterator();
            this.areAllSensorsConnectedAux((Integer) it.next(), connections, state.sds.size(), 500);
        }
        return  (state.sds.size() - sensorsFound.size());
    }

    /**
     * Pre: ---
     * Post: devuelve si el sensor identificado con la llave key está conectado a un centro
     * @param key llave para el sensor que tratamos si tiene camino hasta un centro
     * @param connections mapa de conexiones
     * @param nSensors número de sensores
     * @param deep profundidad para parar la recursividad
     * @return devuelve si el sensor identificado con la llave key está conectado a un centro
     */
    public Boolean areAllSensorsConnectedAux(Integer key, HashMap<Integer, Integer> connections, Integer nSensors, Integer deep) {
        if(deep == 0) return false;
        else {
            if(sensorsNotExplored.contains(key)) sensorsNotExplored.remove(key);
            if(connections.get(key) >= nSensors) {
                sensorsFound.add(key);
                return true;
            } else {
                if(areAllSensorsConnectedAux(connections.get(key), connections, nSensors, deep - 1)) {
                    sensorsFound.add(key);
                    return true;
                }
            }
        }
        return false;
    }
}
