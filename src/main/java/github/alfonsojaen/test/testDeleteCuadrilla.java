package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;

import java.sql.SQLException;

public class testDeleteCuadrilla {
    public static void main(String[] args) {
        CuadrillaDAO cuadrillaDAO = new CuadrillaDAO();
        Cuadrilla cuadrillaToDelete = new Cuadrilla(); // Creamos una instancia de Cuadrilla
        cuadrillaToDelete.setId(24); // Establecemos el ID de la cuadrilla que deseas eliminar
        try {
            cuadrillaDAO.delete(cuadrillaToDelete); // Llamamos al método delete con la cuadrilla que deseamos eliminar
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
