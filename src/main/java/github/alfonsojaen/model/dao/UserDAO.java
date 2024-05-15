package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class UserDAO implements DAO<User> {

    // Consultas SQL
    private final static String INSERT = "INSERT INTO user (username,password,email,name) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE user SET  password=?, email=?, name=? WHERE username=?";
    private final static String DELETE = "DELETE FROM user WHERE username=?";
    private final static String FINDBYUSERNAME = "SELECT a.username,a.password,a.email,a.name FROM user AS a WHERE a.username=?";
    private final static String QUERY = "SELECT username FROM user WHERE email=? AND password=?";

    private Connection conn;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public UserDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Guarda un usuario en la base de datos.
     * @param user El usuario que se va a guardar.
     * @return El usuario guardado, o null si ocurrió un error.
     * @throws SQLException Si ocurre un error al ejecutar la operación en la base de datos.
     */
    @Override
    public User save(User user) throws SQLException {
        User result = new User();
        User u = findByUserName(user.getUsername());
        if (u == null) {
            //INSERT
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getName());
                ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
    } else {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getName());
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        return user;

}

    /**
     * Elimina un usuario de la base de datos.
     * @param entity El usuario que se va a eliminar.
     * @return El usuario eliminado, o null si ocurrió un error o el usuario no existe.
     * @throws SQLException Si ocurre un error al ejecutar la operación en la base de datos.
     */
    @Override
    public User delete(User entity) throws SQLException {
        if (entity == null || entity.getUsername() == null) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1, entity.getUsername());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public User findById(int key) {
        return null;
    }

    /**
     * Busca un usuario por su nombre de usuario en la base de datos.
     * @param username El nombre de usuario del usuario a buscar.
     * @return El usuario encontrado, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la operación en la base de datos.
     */
    public User findByUserName(String username) throws SQLException {
            User result = null;
            if (username != null) {
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
                    pst.setString(1, username);
                    ResultSet res = pst.executeQuery();
                    if (res.next()) {
                        result = new User();
                        result.setUsername(res.getString("username"));
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
     * Verifica las credenciales de inicio de sesión de un usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return El nombre de usuario si las credenciales son válidas, o null si no lo son.
     * @throws SQLException Si ocurre un error al ejecutar la operación en la base de datos.
     */
    public String checkLogin(String email, String password) throws SQLException {
    try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(QUERY)) {
        pst.setString(1, email);
        pst.setString(2, password);
        ResultSet res = pst.executeQuery();
        if (res.next()) {
            return res.getString("userName");
        }
    }
    return null;
    }

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     * @return Una lista de todos los usuarios almacenados en la base de datos.
     */
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
