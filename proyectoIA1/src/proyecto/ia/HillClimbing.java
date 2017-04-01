package proyecto.ia;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by idhrenniel on 1/04/17.
 */
public class HillClimbing {

    public EstadoProblema getBestSolution(EstadoProblema estadoProblema){
        SuccesorFunction sf = new SuccesorFunction();

        while(true) {
            List retval = sf.getSuccessors(estadoProblema);
            EstadoProblema epSolution = getBestSolutionPartial(((ArrayList) retval), estadoProblema);
            if(estadoProblema == epSolution) return epSolution;
            estadoProblema = epSolution;
        }
    }

    public EstadoProblema getBestSolutionPartial(ArrayList retval, EstadoProblema ep){
        ArrayList<EstadoProblema> arraySolutions = ((ArrayList<EstadoProblema>) retval);
        //double bestCost = ep.getHeuristic();
        int bestSolution = -1;

        for(int i = 0; i<arraySolutions.size(); ++i){
            /*if(arraySolutions.get(i).getHeuristic() < bestCost){
                bestSolution = i;
                bestCost = arraySolutions.get(i).getHeuristic();
            }*/
        }

        if(bestSolution == -1) return ep;
        else return arraySolutions.get(bestSolution);
    }

}