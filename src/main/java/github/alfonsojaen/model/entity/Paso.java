package github.alfonsojaen.model.entity;

public class Paso {
    private String hermandad;
    private int capacity;
    private Cuadrilla cuadrilla;

    public Paso(String hermandad, int capacity, Cuadrilla cuadrilla) {
        this.hermandad = hermandad;
        this.capacity = capacity;
        this.cuadrilla = cuadrilla;
    }

    public Paso(){

    }

    public String getHermandad() {
        return hermandad;
    }

    public void setHermandad(String hermandad) {
        this.hermandad = hermandad;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
    }
}
