package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasoDAO implements DAO<Paso>{

    // Consultas SQL
    private static final String FINDALL ="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a";
    private static final String FINDBYID ="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a WHERE a.id=?";
    private static final String INSERT ="INSERT INTO paso (brotherhood,capacity) VALUES (?,?)";
    private static final String UPDATE ="UPDATE paso SET brotherhood=?,capacity=? WHERE id=?";
    private static final String DELETE ="DELETE FROM paso WHERE id=?";
    private final static String FINDBYNAME="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a WHERE a.brotherhood=?";
    private final static String FINDBYCUADRILLA="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a, esta AS b WHERE b.pasoId=a.id AND b.cuadrillaId=?";


    private Connection conn;
    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public PasoDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Guarda un paso en la base de datos.
     * @param paso El paso que se va a guardar.
     * @return El paso guardado, con su ID actualizado si se generó automáticamente, o null si ocurrió un error.
     * @throws SQLException Si ocurre un error al ejecutar la operación en la base de datos.
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
     * Actualiza la información de un paso en la base de datos.
     * @param paso El paso con la información actualizada.
     */
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
     * Elimina un paso de la base de datos.
     * @param paso El paso que se va a eliminar.
     * @return El paso eliminado, o null si ocurrió un error o el paso no existe.
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
     * Busca los pasos asociados a una cuadrilla específica.
     * @param cu La cuadrilla de la que se quieren obtener los pasos.
     * @return Una lista de pasos asociados a la cuadrilla especificada.
     */
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
     * Busca un paso por su nombre en la base de datos.
     * @param name El nombre del paso a buscar.
     * @return El paso encontrado, o un objeto Paso vacío si no se encuentra.
     */
    public Paso findByName(String name) {
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
     * Busca un paso por su ID en la base de datos.
     * @param key El ID del paso a buscar.
     * @return El paso encontrado, o un objeto Paso vacío si no se encuentra.
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
     * Obtiene todos los pasos almacenados en la base de datos.
     * @return Una lista de pasos almacenados en la base de datos.
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
     * Método estático para construir una instancia de PasoDAO.
     * @return Una nueva instancia de PasoDAO.
     */
    public static PasoDAO build(){
        return new PasoDAO();
    }
}
