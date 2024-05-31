/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectobiblioteca;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class FxHistorialUsuarioController implements Initializable {
    @FXML
    private TableView<Object[]> tbHistorialUsuarios;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Clases.CHistorialUsuario objetoHistUsuario = new Clases.CHistorialUsuario();
        objetoHistUsuario.historialUsuarios(tbHistorialUsuarios);
    }
    
    @FXML
    public void mostrarhistorial(ActionEvent event){
        
        Clases.CHistorialUsuario objetoHitUsuario = new Clases.CHistorialUsuario();
        objetoHitUsuario.historialUsuarios(tbHistorialUsuarios);
        tbHistorialUsuarios.getColumns();
        tbHistorialUsuarios.getItems();
    }
}
