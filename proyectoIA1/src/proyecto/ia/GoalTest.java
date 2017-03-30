package proyecto.ia;

import IA.Red.Sensor;
import java.math.*;
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
        /*
        for(cada sensor amb conexio) {
            returnValue = value + costConnecio();
         }
         */
        return returnValue;
    }

    public double costOfConnection(Sensor inici, Sensor desti) {
        return distance(inici.getCoordX(), inici.getCoordY(), desti.getCoordX(), desti.getCoordY())*inici.getCapacidad();
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
