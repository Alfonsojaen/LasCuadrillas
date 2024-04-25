package github.alfonsojaen.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cuadrilla {
    private int id;
    private String name;
    private String overseer;
    private String description;
    private List<Costalero> costaleros;
    private List<Paso> pasos;

    public Cuadrilla( int id,String name, String overseer,String description, List<Costalero> costaleros, List<Paso> pasos) {
        this.id = id;
        this.name = name;
        this.overseer = overseer;
        this.description = description;
        this.costaleros = costaleros;
        this.pasos = pasos;
    }

    public Cuadrilla( int id,String name, String overseer,String description) {
        this.id = id;
        this.name = name;
        this.overseer = overseer;
        this.description = description;
    }

    public Cuadrilla(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverseer() {
        return overseer;
    }

    public void setOverseer(String overseer) {
        this.overseer = overseer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Costalero> getCostaleros() {
        return costaleros;
    }

    public void setCostaleros(List<Costalero> costaleros) {
        this.costaleros = costaleros;
    }

    public List<Paso> getPasos() {
        return pasos;
    }

    public void setPasos(List<Paso> pasos) {
        this.pasos = pasos;
    }

    public void addCostalero(Costalero costalero){
    if(costaleros==null){
        costaleros = new ArrayList<>();
    }
    if(!costaleros.contains(costalero)){
        costaleros.add(costalero);
        }
    }

    public void removeCostalero( Costalero costalero){
        if(costaleros!=null){
            costaleros.remove(costalero);
        }
    }
    public Costalero getCostalero(Costalero costalero){
        Costalero result=null;
        if(costaleros!=null){
            int i=costaleros.indexOf(costalero);
            result = costaleros.get(i);
        }
        return result;
    }
    public void addPaso(Paso paso){
        if(pasos==null){
            pasos = new ArrayList<>();
        }
        if(!pasos.contains(paso)){
            pasos.add(paso);
        }
    }

    public void removePaso( Paso paso){
        if(pasos!=null){
            pasos.remove(paso);
        }
    }
    public Paso getPaso(Paso paso){
        Paso result=null;
        if(pasos!=null){
            int i=pasos.indexOf(paso);
            result = pasos.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuadrilla cuadrilla = (Cuadrilla) o;
        return id == cuadrilla.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cuadrilla{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", overseer='" + overseer + '\'' +
                ", description='" + description + '\'' +
                ", costaleros=" + costaleros +
                ", pasos=" + pasos +
                '}';
    }
}

