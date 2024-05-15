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

    // Consultas SQL
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
     * Constructor que inicializa la conexión a la base de datos.
     */
    public CuadrillaDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Guarda una cuadrilla en la base de datos.
     * @param cuadrilla La cuadrilla que se va a guardar.
     * @return La cuadrilla guardada, o null si ocurrió un error.
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
     * Actualiza la información de una cuadrilla en la base de datos.
     * @param cuadrilla La cuadrilla con la información actualizada.
     */
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
     * Establece los pasos asociados a una cuadrilla en la base de datos.
     * @param cuadrilla La cuadrilla a la que se le asignarán los pasos.
     * @throws SQLException Si ocurre un error al ejecutar la operación en la base de datos.
     */
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
     * Elimina una cuadrilla de la base de datos.
     * @param cuadrilla La cuadrilla que se va a eliminar.
     * @return La cuadrilla eliminada, o null si ocurrió un error.
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
     * Busca una cuadrilla por su ID en la base de datos.
     * @param key El ID de la cuadrilla a buscar.
     * @return La cuadrilla encontrada, o una nueva cuadrilla vacía si no se encuentra.
     */
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

    /**
     * Busca una cuadrilla por su nombre en la base de datos.
     * @param name El nombre de la cuadrilla a buscar.
     * @return La cuadrilla encontrada, o una nueva cuadrilla vacía si no se encuentra.
     */
    public Cuadrilla findByName(String name) {
        Cuadrilla result = new Cuadrilla();
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
     * Obtiene todas las cuadrillas almacenadas en la base de datos.
     * @return Una lista de todas las cuadrillas almacenadas en la base de datos.
     */
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

/**
 * Busca las cuadrillas asociadas a un costalero específico.
 * @param co El costalero del que se quieren obtener las cuadrillas.
 * @return Una lista de cuadrillas asociadas al costalero
 */
    public List<Cuadrilla> findByCostalero(Costalero co){
        List<Cuadrilla> result = new ArrayList<>();
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYCOSTALERO)) {
            pst.setInt(1, co.getId());
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

    /**
     * Método estático que crea una instancia de CuadrillaDAO.
     * @return Una instancia de CuadrillaDAO.
     */
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
