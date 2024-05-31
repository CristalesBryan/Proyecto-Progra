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
public class CDevoluciones {

    public void RegistrarDevolucionV2(TextField isbn, TextField identificacion, DatePicker fecha_devolucion) {
    Clases.CConexion objCConexion = new Clases.CConexion();

    try (Connection conn = objCConexion.establecerConexion()) {
        conn.setAutoCommit(false); // Inicia Transacción

        if (tienePrestamo(conn, isbn.getText(), identificacion.getText())) {
            if (existeDevolucion(conn, isbn.getText(), identificacion.getText())) {
                showAlert("Información", "Este libro ya ha sido devuelto por el usuario.");
            } else {
                try {
                    registrarDevolucion(conn, isbn.getText(), identificacion.getText(), fecha_devolucion.getValue());
                    actualizarLibro(conn, isbn.getText());
                    
                    conn.commit();
                    showAlert("Información", "Devolución registrada correctamente.");
                } catch (SQLException e) {
                    conn.rollback(); // Revertir transacción en caso de error
                    showAlert("Error", "Error al registrar la devolución: " + e.getMessage());
                }
            }
        } else {
            showAlert("Información", "El identificador no tiene este libro prestado.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(CDevoluciones.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private boolean tienePrestamo(Connection conn, String isbn, String identificacion) throws SQLException {
    String consultaPrestamo = "SELECT COUNT(*) FROM prestamos WHERE identificacion = ? AND isbn = ?";
    try (PreparedStatement psVerificacion = conn.prepareStatement(consultaPrestamo)) {
        psVerificacion.setString(1, identificacion);
        psVerificacion.setString(2, isbn);
        ResultSet rs = psVerificacion.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}

private boolean existeDevolucion(Connection conn, String isbn, String identificacion) throws SQLException {
    String consultaDevolucion = "SELECT COUNT(*) FROM devoluciones WHERE identificacion = ? AND isbn = ?";
    try (PreparedStatement psVerificacion = conn.prepareStatement(consultaDevolucion)) {
        psVerificacion.setString(1, identificacion);
        psVerificacion.setString(2, isbn);
        ResultSet rs = psVerificacion.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}

private void registrarDevolucion(Connection conn, String isbn, String identificacion, LocalDate fechaDevolucion) throws SQLException {
    String sqlInsertDevolucion = "INSERT INTO devoluciones (isbn, identificacion, fecha_devolucion) VALUES (?, ?, ?)";
    try (PreparedStatement psInsertarDevolucion = conn.prepareStatement(sqlInsertDevolucion)) {
        Date fechaSQLDevolucion = Date.valueOf(fechaDevolucion);
        psInsertarDevolucion.setString(1, isbn);
        psInsertarDevolucion.setString(2, identificacion);
        psInsertarDevolucion.setDate(3, fechaSQLDevolucion);
        psInsertarDevolucion.executeUpdate();
    }
}

private void actualizarLibro(Connection conn, String isbn) throws SQLException {
    String sqlUpdateLibro = "UPDATE libros SET cantidad_disponible = cantidad_disponible + 1 WHERE isbn = ?";
    try (PreparedStatement psActualizarLibro = conn.prepareStatement(sqlUpdateLibro)) {
        psActualizarLibro.setString(1, isbn);
        psActualizarLibro.executeUpdate();
    }
}



public void MostrarDevoluciones(TableView<Object[]> TablaTotalLibrosDevolucion){
    Clases.CConexion objCConexion = new Clases.CConexion();
        
        TableColumn<Object[],String> isbnColum = new TableColumn<>("Isbn");
        TableColumn<Object[],String> identificacionColumn = new TableColumn<>("Identificacion");
        TableColumn<Object[],String> fecha_devolucionColumn = new TableColumn<>("Fecha Devolucion");
        
        isbnColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        identificacionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        fecha_devolucionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        
        TablaTotalLibrosDevolucion.getColumns().addAll(isbnColum,identificacionColumn,fecha_devolucionColumn);
        
        String sql = "select * from devoluciones;";
        
        try {
            Statement st = objCConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                Object[]rowData ={
                 rs.getString("isbn"),
                 rs.getString("identificacion"),
                 rs.getString("fecha_devolucion")
                };
                
                TablaTotalLibrosDevolucion.getItems().add(rowData);
                
            }
            
        } catch (Exception e) {
            showAlert("Error", "Error al mostrar las devoluciones:"+e.toString());
        } finally {
            objCConexion.cerrarConexio();
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

