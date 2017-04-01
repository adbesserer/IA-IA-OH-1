package proyecto.ia;

/**
 * Created by idhrenniel on 1/04/17.
 */
public class SimulatedAnnealing {

    private double temp = 10000;
    private double coolingRate = 0.003;

    public EstadoProblema getBestSolution(){
        return null;
    }

    public void setTemp(double newTemp) {
        this.temp = newTemp;
    }

    public void setCoolingRate(double newCoolingRate){
        this.coolingRate = newCoolingRate;
    }

}