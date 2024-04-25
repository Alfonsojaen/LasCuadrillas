package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.CuadrillaDAO;
import github.alfonsojaen.model.entity.Cuadrilla;

import java.sql.SQLException;


public class testInsertCuadrilla {
    public static void main(String[] args) {
        CuadrillaDAO cuadrillaDAO = new CuadrillaDAO();

        // Crear una cuadrilla para guardar
        Cuadrilla cuadrilla = new Cuadrilla();

        cuadrilla.setName("Los verdesdsss");
        cuadrilla.setOverseer("Frante");
        cuadrilla.setDescription("Cuadrilla de los verded");

        try {
            // Guardar la cuadrilla
            Cuadrilla savedCuadrilla = cuadrillaDAO.save(cuadrilla);

            // Verificar que se ha guardado correctamente buscando la cuadrilla por su ID
            Cuadrilla foundCuadrilla = cuadrillaDAO.findByName(savedCuadrilla.getName());

            // Imprimir información sobre la cuadrilla encontrada
            System.out.println("Cuadrilla encontrada: " + foundCuadrilla);

            // Si la cuadrilla encontrada no es nula y tiene el mismo ID que la guardada, entonces se ha guardado correctamente
            if (foundCuadrilla != null && foundCuadrilla.getName().equals(savedCuadrilla.getName())) {
                System.out.println("La cuadrilla se ha guardado correctamente.");
            } else {
                System.out.println("Error: La cuadrilla no se ha guardado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


