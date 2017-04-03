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
        int count = 0;
        while(true) {
            List retval = sf.getSuccessors(estadoProblema);
            epSol = getBestSolutionPartial(((ArrayList) retval), estadoProblema);
            System.out.println("AQUIIIIII: "+count); ++count;
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
        double bestCost = ep.coste_total();
        int bestSolution = -1;

        for (int i = 0; i < arraySolutions.size(); ++i) {
            System.out.println("AQUIIII2:  "+i);
            System.out.println(arraySolutions.get(i).coste_total());
            if (arraySolutions.get(i).coste_total() < bestCost) {
                bestSolution = i;
                bestCost = arraySolutions.get(i).coste_total();
            }

        }
        System.out.println("BEST SOLUTION: "+bestSolution);
        if (bestSolution == -1) {
            System.out.println("NO ENCONTRADA MEJOR SOLUCION");
            return ep;
        } else {
            System.out.println("NUEVA SOLUCION MEJOR");
            arraySolutions.get(bestSolution).showconnections();
            return arraySolutions.get(bestSolution);
        }
    }
}