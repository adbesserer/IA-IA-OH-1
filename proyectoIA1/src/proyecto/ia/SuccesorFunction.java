package proyecto.ia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;

public class SuccesorFunction {
    public ArrayList retval;
    
    public List getSuccessors(Object state) {
        retval.clear();
        getSuccessorsAux(state);
        return retval;
    }
    
    public List getSuccessorsAux(Object stateP) {
        EstadoProblema state = (EstadoProblema) stateP;
        HashMap<Integer,Integer> conections = state.getConnectionsMap();
        for(Integer i: conections.keySet()) {
            for(Integer j: connections.keySet()) {
                if(i != j) {
                    state.cambiarCable(i, j);
                    retval.add(state);
                    state.cambiarCable(i, j);
                }
            }
        }
    }
}
