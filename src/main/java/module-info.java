module github.alfonsojaen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens github.alfonsojaen to javafx.fxml;
    opens github.alfonsojaen.model.connection to java.xml.bind;

    exports github.alfonsojaen;

}
