package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostaleroDAO implements DAO<Costalero> {
    private final static String INSERT = "INSERT INTO costalero (id,nickname,height,age) VALUES (?,?,?,?)";
    private final static String INSERTCUADRILLA = "INSERT INTO pertenece (cuadrillaId,costaleroId) VALUES (?,?)";
    private final static String DELETEIDCUADRILLA = "DELETE FROM pertenece WHERE costaleroId=?";
    private final static String UPDATE = "UPDATE costalero SET nickname=?, height=?, age=? WHERE id=?";
    private final static String FINDBYID = "SELECT a.id,a.nickname,a.height,a.age FROM costalero AS a WHERE a.id=?";
    private final static String DELETE = "DELETE FROM costalero WHERE id=?";


    private Connection conn;

    public CostaleroDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Costalero save(Costalero entity)  throws SQLException{
        Costalero result = entity;
        if (entity == null ) return result;
        Costalero c = findById(entity.getId());
        if (c.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getNickname());
                pst.setInt(3, entity.getHeight());
                pst.setInt(4, entity.getAge());
                pst.executeUpdate();
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        entity.setId(generatedId);
                        result = entity;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try(PreparedStatement pst = this.conn.prepareStatement(DELETEIDCUADRILLA)){
                pst.setInt(1, entity.getId());
                pst.executeUpdate();
            }
            for(Cuadrilla cuadrilla : entity.getCuadrilla()){
                try (PreparedStatement pst = this.conn.prepareStatement(INSERTCUADRILLA)) {
                    pst.setInt(1, cuadrilla.getId());
                    pst.setInt(2, entity.getId());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getNickname());
                pst.setInt(3, entity.getHeight());
                pst.setInt(4, entity.getAge());
                pst.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return result;

    }
    @Override
    public Costalero delete(Costalero entity) throws SQLException {
        if (entity == null || entity.getId() == 0) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Costalero findById(int key) {
        Costalero result = new Costalero();
        if (key == 0) return result;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setId(res.getInt(1));
                result.setNickname(res.getString("nikename"));
                result.setHeight(res.getInt(1));
                result.setAge(res.getInt(1));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
        @Override
    public List<Costalero> findAll() {

        return null;
    }

    @Override
    public void close() throws IOException {

    }

    public static CostaleroDAO build() {
        return new CostaleroDAO();
    }

}
