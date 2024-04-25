package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;


public class testInsertPaso {
    public static void main(String[] args) {
        // Crear una instancia de PasoDAO
        PasoDAO pasoDAO = new PasoDAO();

        // Crear un objeto Paso para guardar
        Paso paso = new Paso();
        paso.setBrotherhood("Test5 ");
        paso.setCapacity(10);
        Cuadrilla cuadrilla = new Cuadrilla();
        paso.setCuadrilla(cuadrilla);

        // Guardar el paso
        Paso savedPaso = pasoDAO.save(paso);

        // Verificar si el paso se guardó correctamente
        if (savedPaso != null) {
            System.out.println("Paso guardado correctamente:");
            System.out.println("ID: " + savedPaso.getId());
            System.out.println("Hermandad: " + savedPaso.getBrotherhood());
            System.out.println("Capacidad: " + savedPaso.getCapacity());
            System.out.println("Cuadrilla: " + savedPaso.getCuadrilla().getName());
        } else {
            System.out.println("Error al guardar el paso.");
        }

        // Cerrar la conexión a la base de datos (opcional)
        // No se muestra en tu implementación, pero es una buena práctica cerrar la conexión después de usarla.
        // pasoDAO.close();


    }
}