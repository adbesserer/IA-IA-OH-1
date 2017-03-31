package proyecto.ia;

import IA.Red.Centro;

public class CenterData {
    private Centro centro = new Centro(0, 0);
    private Integer key = 0;
    private Integer volumen = 0;
    private Integer nConnexions = 0;

    CenterData(Centro c, Integer key) {
        this.setCentro(c);
        this.setKey(key);
    }

    public Centro getCentro() {
        return centro;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public void setVolumen(Integer volumen) {
        this.volumen = volumen;
    }

    public void setnConnexions(Integer nConnexions) {
        this.nConnexions = nConnexions;
    }

    public int	getCoordX(){
        return this.centro.getCoordX();
    }

    public int	getCoordY(){
        return this.centro.getCoordY();
    }

    public Integer getVolumen() {
        return volumen;
    }

    public Integer getnConnexions() {
        return nConnexions;
    }
}
