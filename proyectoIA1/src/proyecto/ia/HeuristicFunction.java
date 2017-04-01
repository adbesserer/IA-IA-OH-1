package proyecto.ia;

import java.util.*;

public class HeuristicFunction {
    private Double minCost = Double.MAX_VALUE;
    private Double maxVTransmissio = 0.0;
    private Set<Integer> sensorsFound = new HashSet<Integer>();
    private Set<Integer> sensorsNotExplored = new HashSet<Integer>();

    public double getHeuristicValue(Object n) {
        /* PARAMETRES DE LA FUNCIO HEURISTICA
        1- connexio de tots els sensors -> negaitu si tot connectat, sino, penalty = Valor*n_sensors_desconnectats
        2- Min cost transmissió
        3- Max vol transmissió
        4- Es poden connectar sensors a un altre que excedeixin la capacitat, però aixo fa
           que puji el cost del cable i no el volum.
         */
        Integer penalty = 1;
        return areAllSensorsConnected((EstadoProblema)n)*penalty;
    }

    public Integer areAllSensorsConnected(EstadoProblema state) {
        this.sensorsFound.clear();
        HashMap<Integer, Integer> connections = state.getConnectionsMap();
        for(int i = 0; i < state.sds.size(); i++) {
            this.sensorsNotExplored.add(state.sds.get(i).getKey());
        }
        while (!sensorsNotExplored.isEmpty()) {
            Iterator it = sensorsNotExplored.iterator();
            this.areAllSensorsConnectedAux((Integer) it.next(), connections, state.sds.size());
        }
        return  (state.sds.size() - sensorsFound.size());
    }

    public Boolean areAllSensorsConnectedAux(Integer key, HashMap<Integer, Integer> connections, Integer nSensors) {
        sensorsNotExplored.remove(key);
        if(connections.get(key) >= nSensors) {
            sensorsFound.add(key);
            return true;
        } else {
            if(areAllSensorsConnectedAux(connections.get(key), connections, nSensors)) {
                sensorsFound.add(key);
                return true;
            }
        }
        return false;
    }
}
