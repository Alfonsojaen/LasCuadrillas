package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
import github.alfonsojaen.model.interfaces.InterfaceCuadrillaDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuadrillaDAO implements InterfaceCuadrillaDAO<Cuadrilla> {

    // SQL queries
    private final static String INSERTCUADRILLA = "INSERT INTO cuadrilla (name,overseer,description) VALUES (?,?,?)";
    private final static String UPDATE = "UPDATE cuadrilla SET name=?, overseer=?,description=? WHERE id=?";
    private final static String FINDALL ="SELECT  a.id,a.name,a.overseer,a.description FROM cuadrilla AS a";
    private final static String FINDBYID="SELECT a.id,a.name,a.overseer,a.description FROM cuadrilla AS a WHERE a.id=?";
    private final static String DELETE="DELETE FROM cuadrilla  WHERE name=?";
    private final static String FINDBYNAME="SELECT a.id,a.name,a.overseer,a.description FROM cuadrilla AS a WHERE a.name=?";
    private final static String INSERTPASO = "INSERT INTO esta (pasoId,cuadrillaId) VALUES (?,?)";
    private final static String DELETEIDPASO = "DELETE FROM esta WHERE cuadrillaId=?";
    private final static String FINDBYCOSTALERO="SELECT a.id,a.name,a.overseer,a.description FROM cuadrilla AS a, pertenece AS b WHERE b.cuadrillaId=a.id AND b.costaleroId=?";

    private Connection conn;
    /**
     * Constructor that initializes the connection to the database.
     */
    public CuadrillaDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Saves a cuadrilla in the database.
     * @param cuadrilla The cuadrilla to be saved.
     * @return The saved cuadrilla, or null if an error occurred.
     */
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

    /**
     * Updates the information of a cuadrilla in the database.
     * @param cuadrilla The cuadrilla with the updated information.
     */
    @Override
    public void update(Cuadrilla cuadrilla) {
        try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
            if (cuadrilla != null) {
                    pst.setString(1, cuadrilla.getName());
                    pst.setString(2, cuadrilla.getOverseer());
                    pst.setString(3, cuadrilla.getDescription());
                    pst.setInt(4, cuadrilla.getId());
                pst.executeUpdate();
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the steps associated with a cuadrilla in the database.
     * @param cuadrilla The cuadrilla to which steps will be assigned.
     * @throws SQLException If an error occurs while executing the operation in the database.
     */
    @Override
    public void setPaso(Cuadrilla cuadrilla) throws SQLException{
        try (PreparedStatement pst = this.conn.prepareStatement(DELETEIDPASO)) {
                    pst.setInt(1, cuadrilla.getId());
                    pst.executeUpdate();
                } catch (SQLException e) {
                }
        for(Paso paso : cuadrilla.getPaso()){
            try (PreparedStatement pst = this.conn.prepareStatement(INSERTPASO)) {
                pst.setInt(1, paso.getId());
                pst.setInt(2, cuadrilla.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Deletes a cuadrilla from the database.
     * @param cuadrilla The cuadrilla to be deleted.
     * @return The deleted cuadrilla, or null if an error occurred.
     */
    @Override
    public Cuadrilla delete(Cuadrilla cuadrilla) {
        if (cuadrilla != null ) {
        try (PreparedStatement pst = conn.prepareStatement(DELETE)){
            pst.setString(1,cuadrilla.getName());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            cuadrilla = null;
        }
    }
        return cuadrilla;

    }

    /**
     * Finds a cuadrilla by its ID in the database.
     * @param key The ID of the cuadrilla to find.
     * @return The found cuadrilla, or a new empty cuadrilla if not found.
     */
    @Override
    public Cuadrilla findById(int key) {
        Cuadrilla result = new CuadrillaLazy();
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

    /**
     * Finds a cuadrilla by its name in the database.
     * @param name The name of the cuadrilla to find.
     * @return The found cuadrilla, or a new empty cuadrilla if not found.
     */
    @Override
    public Cuadrilla findByName(String name) {
        Cuadrilla result = new CuadrillaLazy();
        if(name != null) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
            pst.setString(1, name);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setId(res.getInt(1));
                result.setName(res.getString("name"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        return result;
    }


    /**
     * Gets all cuadrillas stored in the database.
     * @return A list of all cuadrillas stored in the database.
     */
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

    /**
     * Finds cuadrillas associated with a specific costalero.
     * @param co The costalero for which to obtain the associated cuadrillas.
     * @return A list of cuadrillas associated with the costalero.
     */
    @Override
    public List<Cuadrilla> findByCostalero(Costalero co){
        List<Cuadrilla> result = new ArrayList<>();
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYCOSTALERO)) {
            pst.setInt(1, co.getId());
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

    /**
     * Method that creates an instance of CuadrillaDAO.
     * @return An instance of CuadrillaDAO.
     */
    public static CuadrillaDAO build(){
        return new CuadrillaDAO();
    }
    class CuadrillaLazy extends Cuadrilla {
        @Override
        public List<Paso> getPaso() {
            if (super.getPaso() == null) {
                setPaso(PasoDAO.build().findByCuadrilla(this));
            }
            return super.getPaso();
        }

    }
    }
