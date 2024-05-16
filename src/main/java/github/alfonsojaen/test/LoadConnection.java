package github.alfonsojaen.test;

import github.alfonsojaen.model.connection.ConnectionProperties;
import github.alfonsojaen.utils.XMLManager;

public class LoadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
