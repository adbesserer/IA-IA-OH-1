package proyecto.ia;

import IA.Red.Sensor;
import java.math.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by idhrenniel on 29/03/17.
 */
public class GoalTest {
    //================================================================================
    // Solucions, parcials sempre
    //================================================================================
    private Double bestCost = Double.MAX_VALUE;

    public boolean isGoalState(Object aState) {
        Double newCost = calculateCost(aState);
        if(bestCost > newCost) {
            setBestCost(newCost);
            return true;
        } else {
            return false;
        }
    }

    public double calculateCost(Object aState) {
        EstadoProblema state = (EstadoProblema) aState;
        double returnValue = 0;
        HashMap<Integer, Integer> sensorMap = state.getConnectionsMap();
        Iterator it = sensorMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entrada = (Map.Entry)it.next();
            SensorData aux = state.getSensorDataAt((Integer)entrada.getKey());
            Sensor aux2 = state.getSensorAt((Integer)entrada.getValue());
            pair p = new pair(aux2.getCoordX(), aux2.getCoordY());
            returnValue = returnValue + costOfConnection(aux, p);
            it.remove(); // avoids a ConcurrentModificationException; (no sé si aquesta linia es necessaria, pero igualment, he fet una copia del sensorMap més amunt aixi que no haurai de ser problematica)
        }
        return returnValue;
    }

    public double costOfConnection(SensorData inici, pair desti) {
        return distance(inici.getCoordX(), inici.getCoordY(), desti.x, desti.y)*inici.getVolumen();
    }

    public double distance(int Xi, int Yi, int Xj, int Yj) {
        double A = Math.pow((double)Xi - Yi, 2);
        double B = Math.pow((double)Xj - Yj, 2);
        return Math.sqrt(A + B);
    }

    public Double getBestCost() {
        return this.bestCost;
    }

    private void setBestCost(Double newBest) {
        this.bestCost = newBest;
    }
}
