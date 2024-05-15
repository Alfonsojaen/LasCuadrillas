package github.alfonsojaen.model.entity;

import java.util.List;
import java.util.Objects;

public class Paso {
    private int id;
    private String brotherhood;
    private int capacity;
    private boolean selected;
    private List<Cuadrilla> cuadrilla;

    public Paso(int id,String brotherhood, int capacity, List<Cuadrilla> cuadrilla) {
        this.id = id;
        this.brotherhood = brotherhood;
        this.selected = false;
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
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Paso paso = (Paso) object;
        return id == paso.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Paso{" +
                "id=" + id +
                ", brotherhood='" + brotherhood + '\'' +
                ", capacity=" + capacity +
                ", selected=" + selected +
                ", cuadrilla=" + cuadrilla +
                '}';
    }
}
