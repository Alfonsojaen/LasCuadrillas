package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostaleroDAO implements DAO<Costalero> {
    private final static String INSERT = "INSERT INTO costalero (nickname,height,age) VALUES (?,?,?)";
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
    public Costalero save(Costalero costalero)  throws SQLException{
        if (costalero != null ) {
        Costalero c = findById(costalero.getId());
        if (c.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, costalero.getNickname());
                pst.setInt(2, costalero.getHeight());
                pst.setInt(3, costalero.getAge());
                pst.executeUpdate();
                ResultSet r = pst.getGeneratedKeys();
                if (r.next()) {
                    int generatedId = r.getInt(1);
                    costalero.setId(generatedId);
                }


                } catch (SQLException e) {
                    e.printStackTrace();
                    costalero = null;
                }
            }

        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setInt(1, costalero.getId());
                pst.setString(2, costalero.getNickname());
                pst.setInt(3, costalero.getHeight());
                pst.setInt(4, costalero.getAge());
                pst.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return costalero;

    }

    public void update(Costalero costalero) throws SQLException{
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setInt(1, costalero.getId());
            pst.setString(2, costalero.getNickname());
            pst.setInt(3, costalero.getHeight());
            pst.setInt(4, costalero.getAge());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCuadrilla(Costalero costalero) throws SQLException{
        try (PreparedStatement pst = this.conn.prepareStatement(DELETEIDCUADRILLA)) {
            pst.setInt(1, costalero.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
        }
        for(Cuadrilla cuadrilla : costalero.getCuadrilla()){
            try (PreparedStatement pst = this.conn.prepareStatement(INSERTCUADRILLA)) {
                pst.setInt(1, cuadrilla.getId());
                pst.setInt(2, costalero.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Costalero delete(Costalero costalero) {
        if (costalero != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, costalero.getId());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                costalero = null;
            }
        }
        return costalero;
    }

    @Override
    public Costalero findById(int key) {
        Costalero result = new Costalero();
        if (key != 0) {

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
