package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class testInsertCostalero {
    public static void main(String[] args) throws SQLException {

        CostaleroDAO costaleroDAO = new CostaleroDAO();
        Costalero costalero = new Costalero();

        costalero.setNickname("fran14");
        costalero.setHeight(180);
        costalero.setAge(20);
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        cuadrillas.add(new Cuadrilla(19, "cuadrilla 1", "overseer 1", "description 1"));
        costalero.setCuadrillas(cuadrillas);
        try {
            Costalero savedCostalero = costaleroDAO.save(costalero);
            System.out.println("Costalero guardado: " + savedCostalero);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

