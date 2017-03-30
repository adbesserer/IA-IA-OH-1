package proyecto.ia;

import IA.Red.Sensor;

//ESTA CLASE NO EXTIENDE SENSOR PORQUE SINÓ DARÁ PROBLEMAS CON LA HERENCIA
public class SensorData {
    private Sensor sensor = new Sensor(0, 0, 0);
    private Integer volumen = 0;
    private Integer key = 0; //valor que nos indica cual es en el array

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
}
