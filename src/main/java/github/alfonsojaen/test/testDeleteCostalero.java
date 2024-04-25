package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.CostaleroDAO;
import github.alfonsojaen.model.entity.Costalero;

import java.sql.SQLException;

public class testDeleteCostalero {
    public static void main(String[] args) {
        CostaleroDAO costaleroDAO = new CostaleroDAO();
        Costalero costaleroToDelete = new Costalero();
        costaleroToDelete.setId(10);

        // Verificar si el costalero con ID 10 existe antes de intentar eliminarlo
        Costalero costaleroToCheck = costaleroDAO.findById(10);
        if (costaleroToCheck != null) {
            try {
                costaleroDAO.delete(costaleroToDelete);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El costalero con ID 10 no existe en la base de datos.");
        }
    }
}