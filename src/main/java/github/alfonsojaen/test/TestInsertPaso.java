package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.sql.SQLException;


public class TestInsertPaso {
    public static void main(String[] args) {
        // Crear una instancia de PasoDAO
        PasoDAO pasoDAO = new PasoDAO();

        // Crear un objeto Paso para guardar
        Paso paso = new Paso();
        paso.setBrotherhood("Test7 ");
        paso.setCapacity(10);
        Cuadrilla cuadrilla = new Cuadrilla();

        try {
            // Guardar la cuadrilla
            Paso savedPaso = pasoDAO.save(paso);

            // Verificar que se ha guardado correctamente buscando la cuadrilla por su nombre
            Paso foundPaso = pasoDAO.findById(savedPaso.getId());

            // Imprimir información sobre la cuadrilla encontrada
            System.out.println("Cuadrilla encontrada: " + foundPaso);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }
