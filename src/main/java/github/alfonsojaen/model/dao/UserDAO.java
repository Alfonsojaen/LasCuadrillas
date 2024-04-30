package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class UserDAO implements DAO<User> {

    private final static String INSERT = "INSERT INTO user (username,password,email,name) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE user SET  password=?, email=?, name=? WHERE username=?";
    private final static String DELETE = "DELETE FROM user WHERE username=?";
    private final static String FINDBYUSERNAME = "SELECT a.username,a.password,a.email,a.name FROM user AS a WHERE a.username=?";
    private final static String QUERY = "SELECT username FROM user WHERE email=? AND password=?";

    private Connection conn;

    public UserDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

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



    public String checkLogin(String gmail, String password) throws SQLException {
    try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(QUERY)) {
        pst.setString(1, gmail);
        pst.setString(2, password);
        ResultSet res = pst.executeQuery();
        if (res.next()) {
            return res.getString("userName");
        }
    }
    return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
