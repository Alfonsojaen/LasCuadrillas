package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class UserDAO implements DAO<User> {

    private final static String INSERT = "INSERT INTO user (nameUser,password,gmail,name) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE user SET  password=?, gmail=?, name=? WHERE nameUser=?";
    private final static String DELETE = "DELETE FROM user WHERE nameUser=?";
    private final static String FINDBYUSERNAME = "SELECT a.nameUser,a.password,a.gmail,a.name FROM user AS a WHERE a.userName=?";
    private final static String QUERY = "SELECT userName FROM user WHERE gmail=? AND password=?";

    private Connection conn;

    public UserDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public User save(User entity) throws SQLException {
        User result = entity;
        if (entity == null ) return result;
        User u = findByUserName(entity.getNameUser());
        if (u.getNameUser() == null) {
            //INSERT
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, entity.getNameUser());
                pst.setString(2, entity.getPassword());
                pst.setString(3, entity.getGmail());
                pst.setString(4, entity.getName());
                pst.executeUpdate();
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String generatedNameUser = generatedKeys.getString("nameUser");
                        entity.setNameUser(generatedNameUser);
                        result = entity;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            }else {
                //UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getPassword());
                    pst.setString(2, entity.getGmail());
                    pst.setString(3, entity.getName());
                    pst.setString(4, entity.getNameUser());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

                    return result;
                }

    @Override
    public User delete(User entity) throws SQLException {
        if (entity == null || entity.getNameUser() == null) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1, entity.getNameUser());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public User findById(int key) {
        return null;
    }

    public User findByUserName(String userName)  {
        User result = new User();
        if (userName ==null) return result;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
            pst.setString(1, userName);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                String nameUser = res.getString("nameUser");
                String password = res.getString("password");
                String gmail = res.getString("gmail");
                String name = res.getString("name");
        }
            res.close();
        }catch (SQLException e) {
        e.printStackTrace();
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
