package github.alfonsojaen.model.entity;

import java.util.List;

public class Paso {
    private int id;
    private String brotherhood;
    private int capacity;
    private List<Cuadrilla> cuadrilla;

    public Paso(int id,String brotherhood, int capacity, List<Cuadrilla> cuadrilla) {
        this.id = id;
        this.brotherhood = brotherhood;
        this.capacity = capacity;
        this.cuadrilla = cuadrilla;
    }

    public Paso(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrotherhood() {
        return brotherhood;
    }

    public void setBrotherhood(String brotherhood) {
        this.brotherhood = brotherhood;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Cuadrilla> getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(List<Cuadrilla> cuadrilla) {
        this.cuadrilla = cuadrilla;
    }
}
