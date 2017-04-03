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

    /**
     * Pre: aState es un objeto que representa el estado actual.
     * Post: devuelve si aState es un subóptimo del problema.
     * @param aState idealmente es un objeto EstadoProblema que representa el estado actual.
     * @return si aState es un subóptimo del problema.
     */
    public boolean isGoalState(Object aState) {
        EstadoProblema ep = (EstadoProblema) aState;
        Double newCost = ep.coste_total();
        if(bestCost > newCost) {
            setBestCost(newCost);
            return true;
        } else {
            return false;
        }
    }

    public Double getBestCost() {
        return this.bestCost;
    }

    private void setBestCost(Double newBest) {
        if(newBest > this.bestCost) this.bestCost = newBest;
    }
}
