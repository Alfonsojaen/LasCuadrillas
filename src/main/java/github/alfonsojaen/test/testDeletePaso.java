package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Paso;

public class testDeletePaso {
    public static void main(String[] args) {
        // Crear una instancia de PasoDAO
        PasoDAO pasoDAO = new PasoDAO();

// ID del paso que deseas eliminar
        int pasoId = 3;

// Obtener el paso que deseas eliminar
        Paso paso = pasoDAO.findById(pasoId);

// Verificar si el paso existe antes de intentar eliminarlo
        if (paso != null) {
            // Eliminar el paso
            pasoDAO.delete(paso);
            System.out.println("Paso eliminado correctamente.");
        } else {
            System.out.println("No se encontró el paso con el ID especificado.");
        }

        }
    }


