package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuadrillaDAO implements DAO<Cuadrilla> {
    private final static String FINDALL ="SELECT  c.int,c.name,c.overseer,c.description,c.costaleroId,c.costalero FROM cuadrilla AS c";
    private final static String INSERT = "INSERT INTO cuadrilla (id,name,overseer,description) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE cuadrilla SET name=?, overseer=?, description=? WHERE id=?";

    private Connection conn;
    public CuadrillaDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Cuadrilla save(Cuadrilla entity) throws SQLException{
        Cuadrilla result = entity;
        if(entity!=null ){
            Cuadrilla c = findById(entity.getId());
            if(c == null) {
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getOverseer());
                    pst.setString(4, entity.getDescription(result.getDescription("description")));
                    pst.executeUpdate();


                    if (entity.getCostaleros() != null) {
                        for (Costalero costalero : entity.getCostaleros()) {
                            CostaleroDAO.build().save(costalero);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try(PreparedStatement pst= ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                    pst.setInt(1,entity.getId());
                    pst.setString(2,entity.getName());
                    pst.setString(3,entity.getOverseer());
                    pst.setString(4,entity.getDescription(result.getDescription("description")));
                    pst.executeUpdate();
                }
            }
        }
        return result;
    }

    @Override
    public Cuadrilla delete(Cuadrilla entity) throws SQLException {
        return null;
    }

    @Override
    public Cuadrilla findById(int key) {
        Cuadrilla result = new CuadrillaLazy();
        return null;
    }

    @Override
    public List<Cuadrilla> findAll() {
        List<Cuadrilla> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {


            ResultSet res = pst.executeQuery();
            while (res.next()){
                Cuadrilla c = new CuadrillaLazy();
                c.setId(res.getInt(0));
                c.setName(res.getString("name"));
                c.setOverseer(res.getString("overseer"));
                c.getDescription(res.getString("description"));
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
    public static CostaleroDAO build(){
        return new CostaleroDAO();
    }
    class CuadrillaLazy extends Costalero {
        public List<Costalero> getCostaleros() {
            if (super.getCuadrilla() == null) {
                //setCostaleros(CostaleroDAO.build().findByCostalero(this));
            }
            return super.getCuadrilla();
        }
    }

}
