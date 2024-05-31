package com.mycompany.proyectobiblioteca;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class FxLoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private VBox containerLeft;

    @FXML
    private VBox containerRight;

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtUser;

    @FXML
    void eventKey(KeyEvent event) {

    }

    @FXML
    void showFxMenu(ActionEvent event) throws IOException {
        //App.setRoot("FxMenu");
    }
    
    @FXML
    public void iniciarSesion(ActionEvent event) throws IOException{
        Clases.CLogin objetoLogin = new Clases.CLogin();
        objetoLogin.inicioSesion(txtUser, txtPass);
        App.setRoot("FxMenu");
    }

}
