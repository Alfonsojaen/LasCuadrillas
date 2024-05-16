package github.alfonsojaen.model.interfaces;

import java.sql.SQLException;

public interface InterfaceCostaleroDAO<T> extends DAO<T> {
    void setCuadrilla(T entity) throws SQLException;
    T findByNickname(String name);
    void update(T entity);
}
