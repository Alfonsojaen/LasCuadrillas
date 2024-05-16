package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.UserDAO;
import github.alfonsojaen.model.entity.User;

import java.sql.SQLException;

public class TestinsertUser {
    public static void main(String[] args) throws SQLException {
       User u = new User("fran4","fran14","fran14","fran14");
       UserDAO userDAO = new UserDAO();
       userDAO.save(u);
        //List<Cuadrilla> cuadrillas = new ArrayList<>();
       // cuadrillas.add(new Cuadrilla(19, "cuadrilla 1", "overseer 1", "description 1"));
       // costalero.setCuadrillas(cuadrillas);

    }
}
