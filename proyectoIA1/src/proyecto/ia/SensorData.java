package proyecto.ia;

import IA.Red.Sensor;

/**
 * Clase que representa una encapsulación que contiene tanto un sensor, como valores
 * que lo identifican.
 */
public class SensorData {
    private Sensor sensor = new Sensor(0, 0, 0);
    private Integer volumen = 0;
    private Integer key = 0; //valor que nos indica cual es en el array
    private Integer nConnexions = 0;

    SensorData(Sensor s, Integer key) {
        this.setSensor(s);
        this.setKey(key);
        double cap = s.getCapacidad();
        this.volumen =(int) Math.floor(cap);
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setVolumen(Integer volumen) {
        this.volumen = volumen;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setnConnexions(Integer nConnexions) {
        this.nConnexions = nConnexions;
    }

    public Integer getKey() {
        return key;
    }

    public Integer getVolumen() {
        return volumen;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public double	getCapacidad(){
        return this.sensor.getCapacidad();
    }

    public int	getCoordX(){
        return this.sensor.getCoordX();
    }

    public int	getCoordY(){
        return this.sensor.getCoordY();
    }

    public Integer getnConnexions() {
        return nConnexions;
    }
}
