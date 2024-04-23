package github.alfonsojaen.test;

import github.alfonsojaen.model.connection.ConnectionProperties;
import github.alfonsojaen.utils.XMLManager;

public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","library","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
