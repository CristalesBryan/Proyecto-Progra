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
public class FxLibrosController implements Initializable {

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
    private TableView<Object[]> tblibros;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Clases.CLibros objetoLibro = new Clases.CLibros();
        objetoLibro.MostrarLibros(tblibros);
    }    
  
          
    @FXML
    public void guardarLibros(ActionEvent event){
        
        Clases.CLibros objetoLibros = new Clases.CLibros();
        objetoLibros.AgregarUsuarios(txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);
        tblibros.getColumns().clear();
        tblibros.getItems().clear();
        objetoLibros.MostrarLibros(tblibros);
        objetoLibros.LimpiarCampos(txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);
        
    }
    
    @FXML
    public void seleccionarLibros(MouseEvent event){
        
        Clases.CLibros objetoLibros = new Clases.CLibros();
        objetoLibros.SeleccionarLibros(tblibros, txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);
   }
    
    @FXML
    private void modificarLibros(ActionEvent event){
        
       Clases.CLibros objetoLibros = new Clases.CLibros();
       objetoLibros.ModificarLibros(txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);
       tblibros.getColumns().clear();
       tblibros.getItems().clear();
       objetoLibros.MostrarLibros(tblibros);
       objetoLibros.LimpiarCampos(txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);

    }
    
    @FXML
    private void eliminarLibros(ActionEvent event){
        
        Clases.CLibros objetoLibros = new Clases.CLibros();
        objetoLibros.EliminarUsuarios(txtisbn);
        tblibros.getColumns().clear();
        tblibros.getItems().clear();
        objetoLibros.MostrarLibros(tblibros);
        objetoLibros.LimpiarCampos(txtisbn, txttitulo, txtautor, txteanio_publicacion, txteditorial, txtcantidad_disponible);
    }
    
    @FXML
    void showFxMenu(ActionEvent event) throws IOException {
        App.setRoot("FxMenu");
    }
}
