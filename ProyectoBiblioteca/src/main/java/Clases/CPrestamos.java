/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CPrestamos {
    
    public void MostrarLibrosPrestamos(TableView<Object[]> TablaTotalLibrosPrestamo){
        
        Clases.CConexion objCConexion = new Clases.CConexion();
        
        TableColumn<Object[],String> isbnColum = new TableColumn<>("isbn");
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
        
        TablaTotalLibrosPrestamo.getColumns().addAll(isbnColum,tituloColum,autorColum,anio_publicacionColum,editorialColum,cantidad_disponibleColum);
        
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
                
                TablaTotalLibrosPrestamo.getItems().add(rowData);
                
            }
            
        } catch (Exception e) {
            showAlert("Error", "Error al mostrar:"+e.toString());
        } finally {
            objCConexion.cerrarConexio();
        }  
    }
    
    
    public void RealizarPrestamo(TextField isbn, TextField identificacion, DatePicker fecha_prestamo) {
        Clases.CConexion objCConexion = new Clases.CConexion();
        
        String sqlCheck = "SELECT COUNT(*) FROM prestamos WHERE identificacion = ?";
        String consultaVerificarLibro = "SELECT cantidad_disponible FROM libros WHERE isbn = ?";
        String sqlInsertPrestamo = "INSERT INTO prestamos (isbn, identificacion, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        String sqlUpdateLibro = "UPDATE libros SET cantidad_disponible = cantidad_disponible - 1 WHERE isbn = ?";
        
        try (Connection conn = objCConexion.establecerConexion()) {
            conn.setAutoCommit(false); // Iniciar transacción
            
            try {
                // Comprobar si el identificador ya está en la base de datos
        PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
        psCheck.setString(1, identificacion.getText());
        ResultSet rs = psCheck.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        if (count > 0) {
            showAlert("Información", "El identificador ya está agregado.");
        } else {
            // Registrar el préstamo
            try (PreparedStatement psInsertPrestamo = conn.prepareStatement(sqlInsertPrestamo);
                 PreparedStatement psUpdateLibro = conn.prepareStatement(sqlUpdateLibro)) {
                 
                LocalDate fechaPrestamo = fecha_prestamo.getValue();
                Date fechaSQLPrestamo = Date.valueOf(fechaPrestamo);
                
                LocalDate fechaDevolucion = fechaPrestamo.plusDays(14); // Plazo de devolución de 14 días
                Date fechaSQLDevolucion = Date.valueOf(fechaDevolucion);
                
                psInsertPrestamo.setString(1, isbn.getText());
                psInsertPrestamo.setString(2, identificacion.getText());
                psInsertPrestamo.setDate(3, fechaSQLPrestamo);
                psInsertPrestamo.setDate(4, fechaSQLDevolucion);
                
                psInsertPrestamo.executeUpdate();
                
                // Actualizar la cantidad disponible del libro
                psUpdateLibro.setString(1, isbn.getText());
                psUpdateLibro.executeUpdate();
                
                conn.commit(); // Confirmar transacción
                showAlert("Información", "Préstamo realizado correctamente.");
            } catch (SQLException e) {
                conn.rollback(); // Revertir transacción en caso de error
                throw e;
            }
        }   
            // Verificar si el libro existe y tiene copias disponibles
            try (PreparedStatement psVerificarLibro = conn.prepareStatement(consultaVerificarLibro)) {
                psVerificarLibro.setString(1, isbn.getText());
                ResultSet rsLibro = psVerificarLibro.executeQuery();
                
                if (rsLibro.next()) {
                    int cantidadDisponible = rsLibro.getInt("cantidad_disponible");
                    if (cantidadDisponible <= 0) {
                        showAlert("Información", "No hay copias disponibles para el libro con ISBN: " + isbn.getText());
                        return;
                    }
                } else {
                    showAlert("Información", "El libro con ISBN: " + isbn.getText() + " no existe.");
                    return;
                }
            }
        } catch (SQLException e) {
            showAlert("Información", "Error al realizar el préstamo");
        }
    }   catch (SQLException ex) {
            Logger.getLogger(CPrestamos.class.getName()).log(Level.SEVERE, null, ex);
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
            
    private void showAlert(String Title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
