package github.alfonsojaen.model.entity;

public class Paso {
    private int id;
    private String brotherhood;
    private int capacity;
    private Cuadrilla cuadrilla;

    public Paso(int id,String brotherhood, int capacity, Cuadrilla cuadrilla) {
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

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
    }
}
