package proyecto.ia;


import java.util.*;
import java.util.ArrayList;

/**
 * La clase SimulatedAnnealing, itera por los sucesores posibles hasta
 * que una probabilidad obliga a elegir al actual sucesor con el que trabajar
 * como mejor solución. Esta probabilidad disminuye iteración a iteración.
 * Siempre que se encuentre una solución mejor, se escoge directamente.
 * @author Adrià Munuera
 * @version 1.0
 */
public class SuccesorFunction implements aima.search.framework.SuccessorFunction{
    public ArrayList retval = new ArrayList();

    SuccesorFunction(){}

    /**
     * Pre: El estado problema puede tener un ciclo después del cambio de cable.
     * Post: devuelve TRUE si existe ahora un ciclo, FALSE si no hay ninguno.
     * @param i nodo del grafo desde donde se ha cambiado un cable.
     * @param ep Estado actual del grafo, incluido con el cambio.
     * @return Boolean que indica si existe algún ciclo a partir del nodo i
     */
    private boolean theresCycle(Integer i, EstadoProblema ep){
        Integer node = i;
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(node); node = ep.getConnectionsMap().get(node);
        while(node < ep.sds.size()){
            if(node == i) return true;
            node = ep.getConnectionsMap().get(node);
        }
        return false;
    }

    /**
     * Pre: ---
     * Post: Dado un estado, creará los sucesores correspondientes y las colocará en un array.
     * @param state Parámetro que representa el estado actual con el que trabajar.
     * @return Devuelve el conjunto de sucesores del estado parametro state.
     */
    public List getSuccessors(Object state) {
        retval.clear();
        getSuccessorsAux(state);
        return retval;
    }

    /**
     * Pre: ---
     * Post: Dado el estado parámetro y usando los operadores, devolverá todos los estados
     * sucesores posibles.
     * @param stateP Parámetro que representa el estado actual con el que trabajar.
     */
    private void getSuccessorsAux(Object stateP) {
        EstadoProblema state = (EstadoProblema) stateP;
        HashMap<Integer,Integer> conections = state.getConnectionsMap();
        for(Integer i: conections.keySet()) {
            for(Integer j: conections.keySet()) {
                if(i != j) {
                    System.out.println("Switching:\n"+i+" "+conections.get(i)+"\n"+j+" "+conections.get(j));
                    /* switch cable */
                    state.switchcables(i, j);
                    if(!theresCycle(i, state) && !theresCycle(j, state)) {

                        state.compute_volumes();
                        state.showconnections();
                        System.out.println("AQUIIIIII 4: "+state.coste_total());
                        EstadoProblema newState = new EstadoProblema(state);
                        retval.add(newState);
                    }
                    else System.out.println("HAY CICLO");
                    state.switchcables(i, j);
                }
            }
            for(Integer j = 0; j < (state.sds.size() + state.cds.size()); j++) {
                /* change cable */
                if(i != j) {
                    System.out.println("Changing:\n"+i+" "+conections.get(i)+" "+j);
                    Integer zz = conections.get(i);
                    if(j >= state.sds.size()) { //destino es un centro
                        if(state.cds.get(j - state.sds.size()).getnConnexions() < 25) {
                            int z = state.getConnectionsMap().get(i);
                            if(z >= state.sds.size()) {
                                state.cds.get(z - state.sds.size()).setnConnexions(state.cds.get(z - state.sds.size()).getnConnexions() - 1);
                            } else {
                                state.sds.get(z).setnConnexions(state.sds.get(z).getnConnexions() - 1);
                            }

                            state.cds.get(j - state.sds.size()).setnConnexions(state.cds.get(j - state.sds.size()).getnConnexions() + 1);
                            if(!theresCycle(i, state)) {
                                state.changecable(i, j);
                                state.compute_volumes();
                                EstadoProblema newState = new EstadoProblema(state);
                                retval.add(newState);
                                state.showconnections();
                            }
                        }
                    } else { //destino es un sensor
                        if(state.sds.get(j).getnConnexions() < 3) {
                            int z = state.getConnectionsMap().get(i);
                            if(z >= state.sds.size()) {
                                state.cds.get(z - state.sds.size()).setnConnexions(state.cds.get(z - state.sds.size()).getnConnexions() - 1);
                            } else {
                                state.sds.get(z).setnConnexions(state.sds.get(z).getnConnexions() - 1);
                            }

                            state.sds.get(j).setnConnexions(state.sds.get(j).getnConnexions() + 1);
                            if(!theresCycle(i, state)) {
                                state.changecable(i, j);
                                state.compute_volumes();
                                EstadoProblema newState = new EstadoProblema(state);
                                retval.add(newState);
                                state.showconnections();
                            }
                        }
                    }
                    state.changecable(i, zz);
                }
            }
        }
    }

}
