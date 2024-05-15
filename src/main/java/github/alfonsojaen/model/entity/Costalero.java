package github.alfonsojaen.model.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class Costalero {
    private int id;
    private String nickname;
    private int height;
    private int age;
    private List<Cuadrilla> cuadrillas;

    public Costalero(int id, String nickname, int height, int age, List<Cuadrilla> cuadrilla) {
        this.id = id;
        this.nickname = nickname;
        this.height = height;
        this.age = age;
        this.cuadrillas = cuadrilla;
    }

    public Costalero() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Cuadrilla> getCuadrilla() {
        return cuadrillas;
    }

    public void setCuadrillas(List<Cuadrilla> cuadrilla) {
        this.cuadrillas= cuadrilla;
    }
    public void addCuadrilla(Cuadrilla cuadrilla){
        if(cuadrillas==null){
            cuadrillas = new ArrayList<>();
        }
        if(!cuadrillas.contains(cuadrilla)){
            cuadrillas.add(cuadrilla);
        }
    }
    public void removeCuadrilla(Cuadrilla cuadrilla){
        if(cuadrillas!=null){
            cuadrillas.remove(cuadrilla);
        }
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Costalero costalero = (Costalero) object;
        return id == costalero.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Costalero{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", height=" + height +
                ", age=" + age +
                ", cuadrillas=" + cuadrillas +
                '}';
    }

}

