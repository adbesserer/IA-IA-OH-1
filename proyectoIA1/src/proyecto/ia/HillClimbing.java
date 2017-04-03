package proyecto.ia;

import java.util.*;

/**
 * La clase HillClimbing, recoge e itera por todos los estados vecinos
 * que podrían representar una solución desde nuestro estado inicial, y
 * devuelve el mejor estado posible.
 * @author Miguel Ángel Muñoz
 * @version 1.0
 */
public class HillClimbing {

    /**
     * Pre: estadoProblema es el estado actual del grafo de sensores y centros.
     * Post: Usando el método de Hill Climbing selecciona y devuelve el mejor
     * estado sucesor.
     * @param estadoProblema Representa el estado actual del grafo.
     * @return Devuelve el mejor estado local según la técnica de Hill Climbing.
     */
    public EstadoProblema getBestSolution(EstadoProblema estadoProblema){
        SuccesorFunction sf = new SuccesorFunction();
        EstadoProblema epSol;
        while(true) {
            List retval = sf.getSuccessors(estadoProblema);
            epSol = getBestSolutionPartial(((ArrayList) retval), estadoProblema);
            if(estadoProblema == epSol) return epSol;
            estadoProblema = epSol;
        }
    }
    public EstadoProblema getBestSolution(EstadoProblema estadoProblema, boolean switchcables, boolean changecable){
        SuccesorFunction sf = new SuccesorFunction();
        EstadoProblema epSol;
        while(true) {
            List retval = sf.getSuccessors(estadoProblema, switchcables, changecable);
            epSol = getBestSolutionPartial(((ArrayList) retval), estadoProblema);
            if(estadoProblema == epSol) return epSol;
            estadoProblema = epSol;
        }
    }

    /**
     * Pre: retval contiene los estados sucesores del estado parámetro ep.
     * Post: devuelve el mejor estado de los sucesores o si ninguno es mejor que el
     * actual, se devolverá este último.
     * @param retval Array de sucesores posibles.
     * @param ep Estado inicial de los cuales se sacan sucesores.
     * @return Devuelve el mejor estado local.
     */
    private EstadoProblema getBestSolutionPartial(ArrayList retval, EstadoProblema ep) {
        ArrayList<EstadoProblema> arraySolutions = ((ArrayList<EstadoProblema>) retval);
        HeuristicFunction hf = new HeuristicFunction();
        double bestCost = hf.getHeuristicValue(ep);
        int bestSolution = -1;

        for (int i = 0; i < arraySolutions.size(); ++i) {
            if (hf.getHeuristicValue(arraySolutions.get(i)) < bestCost) {
                bestSolution = i;
                bestCost = hf.getHeuristicValue(arraySolutions.get(i));
            }

        }
        if (bestSolution == -1) return ep;
        else {
            return arraySolutions.get(bestSolution);
        }
    }
}