/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectobiblioteca;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class FxUsuariosController implements Initializable {
    
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtIdentificacion;    
    @FXML
    private TableView<Object[]> tbUsuarios;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Clases.CConexion objetoConexion = new Clases.CConexion();
        objetoConexion.establecerConexion();*/
        
        Clases.CUsuarios objetoUsuarios = new Clases.CUsuarios();
        objetoUsuarios.mostrarUsuarios(tbUsuarios); 
    } 
    
    @FXML
    private void guardarUsuarios(ActionEvent event){
        Clases.CUsuarios objetoUsuarios = new Clases.CUsuarios();
        objetoUsuarios.AgregarUsuarios(txtIdentificacion, txtNombre, txtApellidos, txtDireccion, txtTelefono);
        tbUsuarios.getColumns().clear();
        tbUsuarios.getItems().clear();
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        objetoUsuarios.LimpiarCampos(txtIdentificacion, txtNombre, txtApellidos, txtDireccion, txtTelefono);
    }
    
    
    @FXML
    private void SeleccionarUsuarios(MouseEvent event){
        Clases.CUsuarios objetoUsuarios = new Clases.CUsuarios();
        objetoUsuarios.SeleccionarUsuarios(tbUsuarios, txtIdentificacion, txtNombre, txtApellidos, txtDireccion, txtTelefono);
    }
    
    @FXML
    private void ModificarUsuarios(ActionEvent event){
        Clases.CUsuarios objetoUsuarios = new Clases.CUsuarios();
        objetoUsuarios.ModificarUsuarios(txtIdentificacion, txtNombre, txtApellidos, txtDireccion, txtTelefono);
        tbUsuarios.getColumns().clear();
        tbUsuarios.getItems().clear();
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        objetoUsuarios.LimpiarCampos(txtIdentificacion, txtNombre, txtApellidos, txtDireccion, txtTelefono);
    }
    
    
    @FXML
    private void EliminarUsuarios(ActionEvent event){
        Clases.CUsuarios objetoUsuarios = new Clases.CUsuarios();
        objetoUsuarios.EliminarUsuarios(txtIdentificacion);
        tbUsuarios.getColumns().clear();
        tbUsuarios.getItems().clear();
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        objetoUsuarios.LimpiarCampos(txtIdentificacion, txtNombre, txtApellidos, txtDireccion, txtTelefono);
    }
    
    @FXML
    void showFxHistorial(ActionEvent event) throws IOException {
        App.setRoot("FxHistorialUsuarios");
    }
    
    @FXML
    void showFxMenu(ActionEvent event) throws IOException {
        App.setRoot("FxMenu");
    }
}
