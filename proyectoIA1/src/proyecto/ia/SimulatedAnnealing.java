package proyecto.ia;

import java.lang.reflect.Array;
import java.util.*;

/**
 * La clase Simulated Annealing, recoge e itera por todos los estados vecinos
 * que podrían representar una solución desde nuestro estado inicial. Por cada
 * uno y hasta que se considere uno como mejor estado, se le aplica la técnica
 * de Simulated Annealing, que dados una temperatura y un coolingRate, existe
 * la probabilidad de escoger los estados sucesores como mejores. Si el estado
 * sucesor es mejor al aplicarse el heurístico, directamente se considera el
 * mejor estado.
 * @author Miguel Ángel Muñoz
 * @version 1.0
 */
public class SimulatedAnnealing {

    private double temp = 10000.0;
    private double coolingRate = 0.003;

    /**
     * Pre: StateH y newStateH no pueden ser el mismo.
     * Post: Devuelve la probabilidad de considerar newStateH como mejor opción
     * entre los sucesores.
     * @param stateH Representa el valor que se ha conseguido con la función heuristica
     *               sobre el estado actual.
     * @param newStateH Representa el valor que se ha conseguido con la función
     *                  heuristica sobre el posible estado sucesor.
     * @return Devuelve la probabilidad de dos maneras, si el nuevo sucesor es mejor, será 1.
     * Si no, se realizará la función demandada por la técnica Simulated Annealing.
     */
    private double acceptanceProbability(double stateH, double newStateH){
        if(newStateH < stateH) return 1.0;
        return Math.exp((stateH - newStateH) / this.temp);
    }

    /**
     * Pre: estadoProblema es el estado actual del grafo de sensores y centros.
     * Post: Usando el método de Simulated Annealing selecciona y devuelve el mejor
     * estado sucesor, usando la temperatura y coolingRate correspondientes.
     * @param state Representa el estado actual del grafo.
     * @return Devuelve el mejor estado local según la técnica de Simulated Annealing.
     */
    public EstadoProblema getBestSolution(EstadoProblema state){
        SuccesorFunction sf = new SuccesorFunction();
        while(temp > 1){
            List retval = sf.getSuccessors(state);

            EstadoProblema newState = ((ArrayList<EstadoProblema>) retval).get(0);
            if (acceptanceProbability(state.coste_total(), newState.coste_total()) > Math.random()) {
                if(state.coste_total() > newState.coste_total()) state = newState;
            }

            this.temp *= 1-coolingRate;
        }

        return state;
    }

    /**
     * Pre: ---
     * Post: El nuevo valor de temp es el parametro newTemp.
     * @param newTemp Representa el valor de la temperatura con la cual
     * se realizará la técnica de Simulated Annealing.
     */
    public void setTemp(double newTemp) {
        this.temp = newTemp;
    }

    /**
     * Pre: ---
     * Post: EL nuevo valor de coolingRate es el parametro newCoolingRate.
     * @param newCoolingRate Representa el valor con el que se consigue la nueva
     * temperatura por iteración.
     */
    public void setCoolingRate(double newCoolingRate){
        this.coolingRate = newCoolingRate;
    }

}