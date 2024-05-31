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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class FxDevolucionesController implements Initializable {
    
    @FXML
    private TextField txtisbn;
    @FXML
    private TextField txtidentificacion;
    @FXML
    private DatePicker datefecha_Devolucion;
    @FXML
    private TableView<Object[]> tblibrosDevolucion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Clases.CDevoluciones objetoDevolucion = new Clases.CDevoluciones();
        objetoDevolucion.MostrarDevoluciones(tblibrosDevolucion);
    }    
    
    @FXML
    public void showFxMenu(ActionEvent event) throws IOException {
        App.setRoot("FxMenu");
    }
    
    @FXML
    public void devolverLibro(ActionEvent event){
        
        Clases.CDevoluciones objtetoDevoluciones = new Clases.CDevoluciones();
        objtetoDevoluciones.RegistrarDevolucionV2(txtisbn, txtidentificacion, datefecha_Devolucion);
        tblibrosDevolucion.getColumns().clear();
        tblibrosDevolucion.getItems().clear();
        objtetoDevoluciones.MostrarDevoluciones(tblibrosDevolucion);
        
    }
}
