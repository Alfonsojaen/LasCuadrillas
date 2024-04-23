package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuadrillaDAO implements DAO<Cuadrilla> {
    private final static String INSERT = "INSERT INTO cuadrilla (id,name,overseer,description) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE cuadrilla SET name=?, overseer=?,description=? WHERE id=?";
    private final static String FINDALL ="SELECT  a.id,a.name,a.overseer,a.description FROM cuadrilla AS a";
    private final static String FINDBYID="SELECT a.id,a.name,a.overseer,a.description FROM author AS a WHERE a.id=?";
    private final static String DELETE="DELETE FROM cuadrilla AS a WHERE a.id=?";

    private Connection conn;
    public CuadrillaDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Cuadrilla save(Cuadrilla entity) throws SQLException {
        Cuadrilla result = entity;
        if (entity != null || entity.getId() == 0) return result;
        Cuadrilla c = findById(entity.getId());
        if (c.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getOverseer());
                pst.setString(4, entity.getDescription());
                pst.executeUpdate();

                if (entity.getCostaleros() != null) {
                    for (Costalero costalero : entity.getCostaleros()) {
                        CostaleroDAO.build().save(costalero);
                    }
                }

                if (entity.getPasos() != null) {
                    for (Paso paso : entity.getPasos()) {
                        PasoDAO.build().save(paso);
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getOverseer());
                pst.setString(4, entity.getDescription());
                pst.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return result;

    }
    @Override
    public Cuadrilla delete(Cuadrilla entity) throws SQLException {
        if (entity == null || entity.getId() == 0) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)){
            pst.setInt(1,entity.getId());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Cuadrilla findById(int key) {
        Cuadrilla result = new CuadrillaLazy();
        if (key == 0) return result;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setId(res.getInt(1));
                result.setName(res.getString("name"));
                result.setOverseer(res.getString("overseer"));
                result.setDescription(res.getString("description"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

        @Override
    public List<Cuadrilla> findAll() {
        List<Cuadrilla> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while (res.next()){
                Cuadrilla c = new CuadrillaLazy();
                c.setId(res.getInt(1));
                c.setName(res.getString("name"));
                c.setOverseer(res.getString("overseer"));
                c.setDescription(res.getString("description"));
                result.add(c);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {


    }
    public static CuadrillaDAO build(){
        return new CuadrillaDAO();
    }
    class CuadrillaLazy extends Cuadrilla {
        @Override
        public List<Costalero> getCostaleros() {
            if (super.getCostaleros() == null) {
                setCostaleros(CostaleroDAO.build().findByCuadrilla(this));
            }
            return super.getCostaleros();
        }
    }

}
