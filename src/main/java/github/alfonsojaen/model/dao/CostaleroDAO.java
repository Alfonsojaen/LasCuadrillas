package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.interfaces.InterfaceCostaleroDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostaleroDAO implements InterfaceCostaleroDAO<Costalero> {

    // SQL Queries
    private final static String INSERT = "INSERT INTO costalero (nickname,height,age) VALUES (?,?,?)";
    private final static String INSERTCUADRILLA = "INSERT INTO pertenece (cuadrillaId,costaleroId) VALUES (?,?)";
    private final static String DELETEIDCUADRILLA = "DELETE FROM pertenece WHERE costaleroId=?";
    private final static String UPDATE = "UPDATE costalero SET nickname=?, height=?, age=? WHERE id=?";
    private final static String FINDBYID = "SELECT a.id,a.nickname,a.height,a.age FROM costalero AS a WHERE a.id=?";
    private final static String DELETE = "DELETE FROM costalero WHERE id=?";
    private final static String FINDALL ="SELECT  a.id,a.nickname,a.height,a.age FROM costalero AS a";
    private final static String FINDBYNAME="SELECT a.id,a.nickname,a.height,a.age FROM costalero AS a WHERE a.nickname=?";

    private Connection conn;

    /**
     * Constructor that initializes the connection to the database.
     */
    public CostaleroDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Saves a costalero in the database.
     * @param costalero The costalero to be saved.
     * @return The saved costalero, with its ID updated if generated automatically, or null if an error occurred.
     * @throws SQLException If an error occurs while executing the operation in the database.
     */
    @Override
    public Costalero save(Costalero costalero) throws SQLException {
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
        }
        return costalero;
    }

    /**
     * Finds a costalero by its name.
     * @param name The name of the costalero to search for.
     * @return The found costalero or an empty Costalero object if not found.
     */
    @Override
    public Costalero findByNickname(String name) {
        Costalero result = new CostaleroLazy();
        if(name != null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
                pst.setString(1, name);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result.setId(res.getInt(1));
                    result.setNickname(res.getString("nickname"));
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Updates the information of a costalero in the database.
     * @param costalero The costalero with the updated information.
     */
    @Override
    public void update(Costalero costalero) {
        try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
            if (costalero != null) {
                pst.setString(1, costalero.getNickname());
                pst.setInt(2, costalero.getHeight());
                pst.setInt(3, costalero.getAge());
                pst.setInt(4, costalero.getId());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns a cuadrilla to the costalero in the database.
     * @param costalero The costalero to whom the cuadrilla is assigned.
     * @throws SQLException If an error occurs while executing the operation in the database.
     */
    @Override
    public void setCuadrilla(Costalero costalero) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(DELETEIDCUADRILLA)) {
            pst.setInt(1, costalero.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
        }
        for(Cuadrilla cuadrilla : costalero.getCuadrilla()) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERTCUADRILLA)) {
                pst.setInt(1, cuadrilla.getId());
                pst.setInt(2, costalero.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes a costalero from the database.
     * @param costalero The costalero to be deleted.
     * @return The deleted costalero, or null if an error occurred or the costalero does not exist.
     */
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

    /**
     * Finds a costalero by its ID in the database.
     * @param key The ID of the costalero to search for.
     * @return The found costalero, or an empty Costalero object if not found.
     */
    @Override
    public Costalero findById(int key) {
        Costalero result = new CostaleroLazy();
        if (key != 0) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
                pst.setInt(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result.setId(res.getInt(1));
                    result.setNickname(res.getString("nickname"));
                    result.setHeight(res.getInt(3));
                    result.setAge(res.getInt(4));
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Retrieves all costaleros stored in the database.
     * @return A list of costaleros stored in the database.
     */
    @Override
    public List<Costalero> findAll() {
        List<Costalero> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Costalero c = new CostaleroLazy();
                c.setId(res.getInt(1));
                c.setNickname(res.getString("nickname"));
                c.setHeight(res.getInt(3));
                c.setAge(res.getInt(4));
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
     * Constructs a new instance of CostaleroDAO.
     * @return A new instance of CostaleroDAO.
     */

    public static CostaleroDAO build() {
        return new CostaleroDAO();
    }
    class CostaleroLazy extends Costalero{
        @Override
        public List<Cuadrilla> getCuadrilla(){
            if(super.getCuadrilla()== null){
                setCuadrillas(CuadrillaDAO.build().findByCostalero(this));
            }
            return super.getCuadrilla();
        }

        }
    }


