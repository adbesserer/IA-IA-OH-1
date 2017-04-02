package proyecto.ia;


import java.util.*;

/**
 * La clase SimulatedAnnealing, itera por los sucesores posibles hasta
 * que una probabilidad obliga a elegir al actual sucesor con el que trabajar
 * como mejor solución. Esta probabilidad disminuye iteración a iteración.
 * Siempre que se encuentre una solución mejor, se escoge directamente.
 * @author Adrià Munuera
 * @version 1.0
 */
public class SuccesorFunction implements aima.search.framework.SuccessorFunction{
    public ArrayList retval = new ArrayList<>();

    /**
     * Pre: El estado problema puede tener un ciclo después del cambio de cable.
     * Post: devuelve TRUE si existe ahora un ciclo, FALSE si no hay ninguno.
     * @param i nodo del grafo desde donde se ha cambiado un cable.
     * @param j nodo del grafo que ahora tiene como entrada el nodo i
     * @param ep Estado actual del grafo, incluido con el cambio.
     * @return Boolean que indica si existe algún ciclo a partir del nodo i
     */
    private Boolean theresCycle(Integer i, Integer j, EstadoProblema ep){
        Integer size = (Integer) ep.sds.size();
        while(j != i && j < size){
            j = ep.getConnectionsMap().get(j);
            if(j == i) return true;
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
     * @return Devuelve el conjunto de sucesores del estado parametro state.
     */
    public void getSuccessorsAux(Object stateP) {
        EstadoProblema state = (EstadoProblema) stateP;
        HashMap<Integer,Integer> conections = state.getConnectionsMap();
        for(Integer i: conections.keySet()) {
            for(Integer j: conections.keySet()) {
                if(i != j) {
                    state.changecable(i, j);
                    //ver si se crea un ciclo
                    if(!theresCycle(i, j, state)) retval.add(state);
                    state = (EstadoProblema) stateP;
                    /* el otro operando */
                    state.switchcables(i, j);
                    retval.add(state);
                    state = (EstadoProblema) stateP;
                }
            }
        }
    }

}
