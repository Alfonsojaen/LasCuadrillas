package github.alfonsojaen.model.interfaces;

import github.alfonsojaen.model.entity.Costalero;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceCuadrillaDAO <T> extends DAO<T>{
    void setPaso(T entity) throws SQLException;
    void update(T entity);
    T findByName(String name);
    List<T> findByCostalero(Costalero costalero);
}
