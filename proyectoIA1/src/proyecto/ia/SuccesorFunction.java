package proyecto.ia;

import org.omg.CORBA.INTERNAL;
import sun.management.snmp.util.SnmpTableCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;

public class SuccesorFunction {
    public ArrayList retval;


    private Boolean theresCycle(Integer i, Integer j, EstadoProblema ep){
        Integer size = (Integer) ep.sds.size();
        while(j != i && j < size){
            j = ep.getConnectionsMap().get(j);
            if(j == i) return true;
        }
        return false;
    }

    public List getSuccessors(Object state) {
        retval.clear();
        getSuccessorsAux(state);
        return retval;
    }

    public void getSuccessorsAux(Object stateP) {
        EstadoProblema state = (EstadoProblema) stateP;
        HashMap<Integer,Integer> conections = state.getConnectionsMap();
        for(Integer i: conections.keySet()) {
            for(Integer j: conections.keySet()) {
                if(i != j) {
                    Integer z = conections.get(i);
                    state.changecable(i, j);
                    //ver si se crea un ciclo
                    if(!theresCycle(i, j, state)) retval.add(state);
                    state.changecable(i, z);
                    /* el otro operando */
                    state.switchcables(i, j);
                    retval.add(state);
                    state.switchcables(i, j);
                }
            }
        }
    }

    public EstadoProblema getSuccessorSA(Object stateP){
        EstadoProblema state = (EstadoProblema) stateP;
        HashMap<Integer, Integer> conections = state.getConnectionsMap();

        double temp = 10000;
        double coolingRate = 0.003;

        while(temp > 1){
            ArrayList newStates = (ArrayList) getSuccessors(state); // !!!

        }


    }

    public void simulatingAnnealing(Object State){
        double temp = 10000;
        double coolingRate = 0.003;

        EstadoProblema bestState = (EstadoProblema) State;

        while(temp > 1){
            EstadoProblema newState = new EstadoProblema();


            if(acceptanceProbability(currentH, neighbourH, temp) > Math.random()){
                State = newState;
            }

            if(State.getHeuristic() < bestState.getHeuristic()){
                bestState = (EstadoProblema) State;
            }
            temp *= 1-coolingRate;
        }

    }

    public static double acceptanceProbability(int currentH, int neighbourH, double temp){
        if(neighbourH < currentH) return 1.0;
        return Math.exp((currentH - neighbourH) / temp);
    }

}
