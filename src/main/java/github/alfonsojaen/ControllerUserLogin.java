package github.alfonsojaen;

import github.alfonsojaen.model.dao.UserDAO;
import github.alfonsojaen.model.singleton.UserSession;
import github.alfonsojaen.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerUserLogin {

    @FXML
    private TextField tfGmail;
    @FXML
    private PasswordField tfPass;


    @FXML
    private void login() throws SQLException, IOException {

        String gmail = tfGmail.getText().trim();
        String password = tfPass.getText().trim();
        password = Utils.encryptSHA256(password);


        if(gmail.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Falta algun campo por introducir");
            alert.showAndWait();
        }else {
            UserDAO mDAO = new UserDAO();
            String nameUser;
            if((nameUser=mDAO.checkLogin(gmail, password))!=null) {
                UserSession.login(gmail, password);
                //Logged
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Login");
                alerta.setHeaderText("Login exitoso");
                alerta.setContentText("Se ha logeago el Matchmaker correctamente.");
                alerta.showAndWait();
                switchToUserPage();
            }else {
                UserSession.logout();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Login");
                alerta.setHeaderText("No se ha podido logear");
                alerta.setContentText("Intentelo de nuevo.");
                alerta.showAndWait();
            }

        }

    }



    @FXML
    private void switchToUserPage() throws IOException {
        App.setRoot("menu");
    }
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }
}


