package github.alfonsojaen.model.interfaces;

import github.alfonsojaen.model.entity.Cuadrilla;

import java.util.List;

public interface InterfacePasoDAO <T> extends DAO<T>{
    void update(T entity);
    List<T> findByCuadrilla(Cuadrilla cuadrilla);
    T findByBrotherhood(String name);

}
