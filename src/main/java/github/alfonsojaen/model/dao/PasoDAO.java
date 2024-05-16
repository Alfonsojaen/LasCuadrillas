package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;
import github.alfonsojaen.model.interfaces.InterfacePasoDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasoDAO implements InterfacePasoDAO<Paso> {

    // SQL Queries
    private static final String FINDALL ="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a";
    private static final String FINDBYID ="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a WHERE a.id=?";
    private static final String INSERT ="INSERT INTO paso (brotherhood,capacity) VALUES (?,?)";
    private static final String UPDATE ="UPDATE paso SET brotherhood=?,capacity=? WHERE id=?";
    private static final String DELETE ="DELETE FROM paso WHERE id=?";
    private final static String FINDBYNAME="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a WHERE a.brotherhood=?";
    private final static String FINDBYCUADRILLA="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a, esta AS b WHERE b.pasoId=a.id AND b.cuadrillaId=?";


    private Connection conn;

    /**
     * Constructor that initializes the connection to the database.
     */
    public PasoDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Saves a step in the database.
     * @param paso The step to be saved.
     * @return The saved step, with its ID updated if generated automatically, or null if an error occurred.
     * @throws SQLException If an error occurs while executing the operation in the database.
     */
    @Override
    public Paso save(Paso paso) throws SQLException{
        if (paso != null ) {
        Paso p = findById(paso.getId());
        if (p.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, paso.getBrotherhood());
                pst.setInt(2, paso.getCapacity());
                pst.executeUpdate();
                ResultSet r = pst.getGeneratedKeys();
                if (r.next()) {
                    int generatedId = r.getInt(1);
                    paso.setId(generatedId);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                paso = null;
            }
            }
        }
        return paso;
    }

    /**
     * Updates the information of a step in the database.
     * @param paso The step with the updated information.
     */
    @Override
    public void update(Paso paso){
    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
        if(paso!=null){
        pst.setString(1, paso.getBrotherhood());
        pst.setInt(2, paso.getCapacity());
        pst.setInt(3, paso.getId());
        pst.executeUpdate();
        } else {
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    /**
     * Deletes a step from the database.
     * @param paso The step to be deleted.
     * @return The deleted step, or null if an error occurred or the step does not exist.
     */
    @Override
    public Paso delete(Paso paso)  {
        if(paso!=null){
            try (PreparedStatement pst = conn.prepareStatement(DELETE)){
                pst.setInt(1,paso.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                paso=null;
            }
        }
        return paso;
    }

    /**
     * Finds the steps associated with a specific cuadrilla.
     * @param cu The cuadrilla for which to obtain the steps.
     * @return A list of steps associated with the specified cuadrilla.
     */
    @Override
    public List<Paso> findByCuadrilla(Cuadrilla cu){
        List<Paso> result = new ArrayList<>();
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYCUADRILLA)) {
            pst.setInt(1, cu.getId());
            ResultSet res = pst.executeQuery();
            while (res.next()){
                Paso p = new Paso();//LAZY
                p.setId(res.getInt(1));
                p.setBrotherhood(res.getString("brotherhood"));
                p.setCapacity(res.getInt("capacity"));
                result.add(p);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Finds a step by its name in the database.
     * @param name The name of the step to find.
     * @return The found step, or an empty Paso object if not found.
     */
    @Override
    public Paso findByBrotherhood(String name) {
        Paso result = new Paso();
        if(name != null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
                pst.setString(1, name);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result.setId(res.getInt(1));
                    result.setBrotherhood(res.getString("brotherhood"));
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Finds a step by its ID in the database.
     * @param key The ID of the step to find.
     * @return The found step, or an empty Paso object if not found.
     */
    @Override
    public Paso findById(int key) {
        Paso result = new Paso();
        if(key != 0){

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)){
            pst.setInt(1,key);
            ResultSet res = pst.executeQuery();
                if(res.next()){
                    result.setId(res.getInt(1));
                    result.setBrotherhood(res.getString("brotherhood"));
                    result.setCapacity(res.getInt(3));

                }
                res.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        return result;
    }

    /**
     * Gets all steps stored in the database.
     * @return A list of steps stored in the database.
     */
    @Override
    public List<Paso> findAll() {
        List<Paso> result = new ArrayList<>();
        try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Paso p = new Paso();
                    p.setId(res.getInt(1));
                    p.setBrotherhood(res.getString("brotherhood"));
                    p.setCapacity(res.getInt(3));
                    result.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {
    }

    /**
     * Static method to build an instance of PasoDAO.
     * @return A new instance of PasoDAO.
     */
    public static PasoDAO build(){
        return new PasoDAO();
    }
}
