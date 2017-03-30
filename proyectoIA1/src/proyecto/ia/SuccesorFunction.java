package proyecto.ia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;

public class SuccesorFunction {
    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        EstadoProblema ep = (EstadoProblema) state;

        //generar un estado sucesor
        //recorrer hashmap intercambiando conexiones entre sensores
        HashMap<Integer,Integer> conections = ep.getConnectionsMap();
        //aqui va una iteración del hashmap generando sucesores (retval.add(succ))
        for(Integer k: conections.keySet()) {

        }
        return retval;
    }
}
