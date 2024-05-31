/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectobiblioteca;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FxMenuController {

    @FXML
    void showFxDevoluciones(ActionEvent event) throws IOException {
        App.setRoot("FxDevoluciones");
    }

    @FXML
    void showFxLibros(ActionEvent event) throws IOException {
        App.setRoot("FxLibros");
    }

    @FXML
    void showFxPrestamos(ActionEvent event) throws IOException {
        App.setRoot("FxPrestamos");
    }

    @FXML
    void showFxUsuarios(ActionEvent event) throws IOException {
        App.setRoot("FxUsuarios");
    }

}

