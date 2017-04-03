package proyecto.ia;

import java.util.*;

public class HeuristicFunction {
    private Double minCost;
    private Integer maxVTransmissio;
    private Set<Integer> sensorsFound = new HashSet<Integer>();
    private Set<Integer> sensorsNotExplored = new HashSet<Integer>();

    public HeuristicFunction() {
        this.maxVTransmissio = 0;
        this.minCost = 0.0;
    }

    /* PARAMETRES DE LA FUNCIO HEURISTICA
        1- connexio de tots els sensors -> negaitu si tot connectat, sino, penalty = Valor*n_sensors_desconnectats
        2- Min cost transmissió
        3- Max vol transmissió
        4- Es poden connectar sensors a un altre que excedeixin la capacitat, però aixo fa
           que puji el cost del cable i no el volum. (checked)
    */

    public double getHeuristicValue(Object n) {
        Integer penalty = 5; //++connectivitat
        Double returnValue = 0.0;
        EstadoProblema state = (EstadoProblema) n;
        Integer volumenTotal = state.volumen_total();
        Double costeTotal = state.coste_total();
        returnValue = (costeTotal - this.minCost)/1000; //modificant aquest parametre --cost --volum
        if(costeTotal < minCost) {
            this.minCost = costeTotal;
        }
        returnValue = returnValue + (maxVTransmissio - volumenTotal)/(4*state.sds.size());
        if(volumenTotal > maxVTransmissio) {
            this.maxVTransmissio = volumenTotal;

        }
        returnValue = returnValue + areAllSensorsConnected((EstadoProblema)n)*penalty;
        return returnValue;
    }

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
