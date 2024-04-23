package github.alfonsojaen.test;

import github.alfonsojaen.model.dao.PasoDAO;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.util.ArrayList;

public class test1insert {
    public static void main(String[] args) {
        Cuadrilla a = new Cuadrilla();
        Paso b = new Paso();
        b.setId(2);
        b.setBrotherhood("Nazareno");
        b.setCapacity(25);
        b.setCuadrilla(a);
        //En JAVA tengo el libro listo
        PasoDAO bDAO=new PasoDAO();
        bDAO.save(b);
    }
}

