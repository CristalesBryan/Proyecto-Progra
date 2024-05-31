/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Crist
 */
public class CLibros {
    
    public void AgregarUsuarios(TextField isbn, TextField titulo, TextField autor, TextField anio_publicacion, TextField editorial, TextField cantidad_disponible){
        CConexion objetCConexion = new CConexion();
        
        String consulta = "insert into libros (isbn, titulo, autor, anio_publicacion, editorial, cantidad_disponible) values (?,?,?,?,?,?);";
        
        try {
            CallableStatement cs = objetCConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, isbn.getText());
            cs.setString(2, titulo.getText());
            cs.setString(3, autor.getText());
            cs.setInt(4, Integer.parseInt(anio_publicacion.getText()));
            cs.setString(5, editorial.getText());
            cs.setInt(6, Integer.parseInt(cantidad_disponible.getText()));
            cs.execute();
            
            showAlert("Informacion", "Se guardo correctamente");
        } catch (Exception e) {
            showAlert("Informacion", "Error al guardar "+ e.toString());
        } finally {
            objetCConexion.cerrarConexio();
        }  
    }
    
    public void MostrarLibros(TableView<Object[]> TablaTotalLibros){
        
        Clases.CConexion objCConexion = new Clases.CConexion();
        
        TableColumn<Object[],String> isbnColum = new TableColumn<>("Isbn");
        TableColumn<Object[],String> tituloColum = new TableColumn<>("Titulo");
        TableColumn<Object[],String> autorColum = new TableColumn<>("Autor");
        TableColumn<Object[],String> anio_publicacionColum = new TableColumn<>("Año de Publicacion");
        TableColumn<Object[],String> editorialColum = new TableColumn<>("Editorial");
        TableColumn<Object[],String> cantidad_disponibleColum = new TableColumn<>("Cantidad Disponible");
        
        isbnColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        tituloColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        autorColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        anio_publicacionColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        editorialColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        cantidad_disponibleColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5].toString()));
        
        TablaTotalLibros.getColumns().addAll(isbnColum,tituloColum,autorColum,anio_publicacionColum,editorialColum,cantidad_disponibleColum);
        
        String sql = "select * from libros;";
        
        try {
            Statement st = objCConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                Object[]rowData ={
                 rs.getString("isbn"),
                 rs.getString("titulo"),
                 rs.getString("autor"),
                 rs.getInt("anio_publicacion"),
                 rs.getString("editorial"),
                 rs.getString("cantidad_disponible")
                };
                
                TablaTotalLibros.getItems().add(rowData);
                
            }
            
        } catch (Exception e) {
            showAlert("Error", "Error al guardar:"+e.toString());
        } finally {
            objCConexion.cerrarConexio();
        }  
    }
    
    
    public void SeleccionarLibros(TableView<Object[]> TablaTotalLibros,TextField isbn, TextField titulo, TextField autor, TextField anio_publicacion, TextField editorial, TextField cantidad_disponible){
        
      int fila = TablaTotalLibros.getSelectionModel().getSelectedIndex();
      
      if(fila>=0){
          
          Object[] filaSeleccionada = TablaTotalLibros.getItems().get(fila);
          
          isbn.setText(filaSeleccionada[0].toString());
          titulo.setText(filaSeleccionada[1].toString());
          autor.setText(filaSeleccionada[2].toString());
          anio_publicacion.setText(filaSeleccionada[3].toString());
          editorial.setText(filaSeleccionada[4].toString());
          cantidad_disponible.setText(filaSeleccionada[5].toString()); 
      }  
    }
    
    
    public void ModificarLibros(TextField isbn, TextField titulo, TextField autor, TextField anio_publicacion, TextField editorial, TextField cantidad_disponible){
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "UPDATE libros SET isbn=?, titulo=?, autor=?, anio_publicacion=?, editorial=?, cantidad_disponible=? WHERE isbn=?";
        
        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, isbn.getText());
            cs.setString(2, titulo.getText());
            cs.setString(3, autor.getText());
            cs.setInt(4, Integer.parseInt(anio_publicacion.getText()));
            cs.setString(5, editorial.getText());
            cs.setInt(6, Integer.parseInt(cantidad_disponible.getText()));     
            cs.setString(7, isbn.getText());
            cs.execute();
            
            showAlert("Informacion", "Se modifico correctamente");
            
            
        } catch (Exception e) {
            showAlert("Informacion", "No se modifico correctamente"+e.toString());
        } finally {
            objetoConexion.cerrarConexio();
        }
    }
    
    
    public void EliminarUsuarios(TextField isbn){
        CConexion objetoCConexion = new CConexion();
        String consulta = "DELETE FROM libros WHERE libros.isbn=?";
        
        try {
            CallableStatement cs = objetoCConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, isbn.getText());
            cs.execute();
            
            showAlert("Informacion", "Se elimino Correctamente");
        } catch (Exception e) {
            showAlert("Informacion", "No se elimino Correctamente"+e.toString());
        } finally {
            objetoCConexion.cerrarConexio();
        }
    }
    
    public void LimpiarCampos(TextField isbn, TextField titulo, TextField autor, TextField anio_publicacion, TextField editorial, TextField cantidad_disponible){
       
        isbn.setText("");
        titulo.setText("");
        autor.setText("");
        anio_publicacion.setText("");
        editorial.setText("");
        cantidad_disponible.setText("");
    }
    
    
    
    
    
    private void showAlert(String Title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
