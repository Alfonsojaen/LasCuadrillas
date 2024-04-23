package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CostaleroDAO implements DAO<Costalero> {

    private static final String FINDBYAUTHOR ="SELECT b.id,b.nickname,b.height,b.age,b.cuadrillaId FROM costalero AS c WHERE b.cuadrillaId=?";

    private Connection conn;
    public CostaleroDAO(){
        conn = ConnectionMariaDB.getConnection();
    }
    @Override
    public Costalero save(Costalero entity) throws SQLException {
        return null;
    }

    @Override
    public Costalero delete(Costalero entity) throws SQLException {
        return null;
    }

    @Override
    public Costalero findById(int key) {
        return null;
    }

    public List<Costalero> findByCostalero(Costalero c){
        return null;
    }
    @Override
    public List<Costalero> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
    public static CostaleroDAO build(){
        return new CostaleroDAO();
    }
}
