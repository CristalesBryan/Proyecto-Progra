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
import javafx.scene.input.MouseEvent;

public class FxPrestamosController implements Initializable{

    @FXML
    private TextField txtisbn;
    @FXML
    private TextField txttitulo;
    @FXML
    private TextField txtautor;
    @FXML
    private TextField txteanio_publicacion;
    @FXML
    private TextField txteditorial;
    @FXML
    private TextField txtcantidad_disponible;
    @FXML
    private DatePicker datefecha_prestamo;
    @FXML
    private TextField txtidentificacion;
    @FXML
    private TableView<Object[]> tblibrosPrestamo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Clases.CPrestamos objetoPrestamo = new Clases.CPrestamos();
        objetoPrestamo.MostrarLibrosPrestamos(tblibrosPrestamo);
    }
    
    
    
    @FXML
    public void showFxMenu(ActionEvent event) throws IOException {
        App.setRoot("FxMenu");
    }

    @FXML
    public void prestarLibro(ActionEvent event) {
        
        Clases.CPrestamos objetoPrestamos = new Clases.CPrestamos();
        objetoPrestamos.RealizarPrestamo(txtisbn, txtidentificacion, datefecha_prestamo);
        tblibrosPrestamo.getColumns().clear();
        tblibrosPrestamo.getItems().clear();
        objetoPrestamos.MostrarLibrosPrestamos(tblibrosPrestamo);        
    }
    
    @FXML
    public void seleccionarLibros(MouseEvent event){
        
        Clases.CLibros objetoLibros = new Clases.CLibros();
        objetoLibros.SeleccionarLibros(tblibrosPrestamo, txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);
   }
}

