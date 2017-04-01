package proyecto.ia;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by idhrenniel on 1/04/17.
 */
public class SimulatedAnnealing {

    private double temp = 10000.0;
    private double coolingRate = 0.003;

    private double acceptanceProbability(double stateH, double newStateH){
        if(newStateH < stateH) return 1.0;
        return Math.exp((stateH - newStateH) / this.temp);
    }

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

    public void setTemp(double newTemp) {
        this.temp = newTemp;
    }

    public void setCoolingRate(double newCoolingRate){
        this.coolingRate = newCoolingRate;
    }

}