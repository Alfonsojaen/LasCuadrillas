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
    private final static String INSERTCUADRILLA = "INSERT INTO cuadrilla (name,overseer,description) VALUES (?,?,?)";
    private final static String UPDATE = "UPDATE cuadrilla SET name=?, overseer=?,description=? WHERE id=?";
    private final static String FINDALL ="SELECT  a.id,a.name,a.overseer,a.description FROM cuadrilla AS a";
    private final static String FINDBYID="SELECT a.id,a.name,a.overseer,a.description FROM cuadrilla AS a WHERE a.id=?";
    private final static String DELETE="DELETE FROM cuadrilla  WHERE id=?";
    private final static String FINDBYNAME="SELECT a.id,a.name,a.overseer,a.description FROM cuadrilla AS a WHERE a.name=?";
    private final static String INSERTPASO = "INSERT INTO paso (cuadrillaId) VALUES (?)";
    private final static String DELETEIDPASO = "DELETE FROM paso WHERE cuadrillaId=?";

    private Connection conn;
    public CuadrillaDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Cuadrilla save(Cuadrilla cuadrilla)  {
        if (cuadrilla != null ) {
            Cuadrilla c = findById(cuadrilla.getId());
            if (c.getId() == 0) {
                //INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERTCUADRILLA, Statement.RETURN_GENERATED_KEYS)) {
                    pst.setString(1, cuadrilla.getName());
                    pst.setString(2, cuadrilla.getOverseer());
                    pst.setString(3, cuadrilla.getDescription());
                    pst.executeUpdate();


                } catch (SQLException e) {
                    e.printStackTrace();
                    cuadrilla = null;
                }
                }
            }
        return cuadrilla;

    }
    public void update(Cuadrilla cuadrilla) {
        try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
            if (cuadrilla != null) {
                    pst.setString(1, cuadrilla.getName());

                    pst.setString(2, cuadrilla.getOverseer());

                    pst.setString(3, cuadrilla.getDescription());
                    pst.setInt(4, cuadrilla.getId());
                pst.executeUpdate();
            } else {
                throw new IllegalArgumentException("El objeto Cuadrilla no puede ser nulo");
            }
        } catch (SQLException e) {
            // Manejar la excepción de SQLException aquí
            e.printStackTrace();
        }
    }
    public void setPaso(Cuadrilla cuadrilla) throws SQLException{
        try (PreparedStatement pst = this.conn.prepareStatement(DELETEIDPASO)) {
            pst.setInt(1, cuadrilla.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
        }
        for(Paso paso : cuadrilla.getPaso()){
            try (PreparedStatement pst = this.conn.prepareStatement(INSERTPASO)) {
                pst.setInt(1, cuadrilla.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

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
        Cuadrilla result = new Cuadrilla();
        if (key != 0) {
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
        }
            return result;
        }


    public Cuadrilla findByName(String name) {
        Cuadrilla result = new CuadrillaLazy();
        if (name == null || name.isEmpty()) return result;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
            pst.setString(1, name);
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
                Cuadrilla c = new Cuadrilla();//LAZY
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
               // setCostaleros(CostaleroDAO.build().findByCuadrilla(this));
            }
            return super.getCostaleros();
        }
    }

}
