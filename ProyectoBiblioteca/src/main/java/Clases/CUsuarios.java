/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Crist
 */
public class CUsuarios {
    
    public void AgregarUsuarios(TextField identificacion, TextField nombre, TextField apellidos, TextField direccion, TextField telefono){
        CConexion objetCConexion = new CConexion();
        
        String consulta = "insert into usuarios (identificacion, nombre, apellidos, direccion, telefono) values (?,?,?,?,?);";
        
        try {
            CallableStatement cs = objetCConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, identificacion.getText());
            cs.setString(2, nombre.getText());
            cs.setString(3, apellidos.getText());
            cs.setString(4, direccion.getText());
            cs.setString(5, telefono.getText());
            cs.execute();
            
            showAlert("Informacion", "Se guardo correctamente");
        } catch (Exception e) {
            showAlert("Informacion", "Error al guardar "+ e.toString());
        } finally {
            objetCConexion.cerrarConexio();
        }  
    }
    
    public void mostrarUsuarios(TableView<Object[]> TablaTotalUsuarios){
        
        Clases.CConexion objeCConexion = new Clases.CConexion();
        
        TableColumn<Object[],String> identificacionColumn = new TableColumn<>("identidicacion");
        TableColumn<Object[],String> nombreColumn = new TableColumn<>("nombres");
        TableColumn<Object[],String> apellidosColumn = new TableColumn<>("apellidos");
        TableColumn<Object[],String> direccionColumn = new TableColumn<>("direccion");
        TableColumn<Object[],String> telefonoColumn = new TableColumn<>("telefono");
        
        
        identificacionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        apellidosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        direccionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        telefonoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        
        TablaTotalUsuarios.getColumns().addAll(identificacionColumn, nombreColumn, apellidosColumn, direccionColumn, telefonoColumn);
        
        String sql = "select * from usuarios;";
        
        try {
            Statement st = objeCConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {                
                Object[] rowData = {
                    rs.getString("identificacion"),
                  rs.getString("nombre"),
                  rs.getString("apellidos"),
                  rs.getString("direccion"),
                  rs.getString("telefono")                 
                };
                
                TablaTotalUsuarios.getItems().add(rowData);
            }
        } catch (Exception e) {
            showAlert("Error", "Error al guardar"+e.toString());
        } finally {
            objeCConexion.cerrarConexio();
        }
        
    }
    
    
    public void SeleccionarUsuarios(TableView<Object[]> TablaTotalUsuarios,TextField Identificacion, TextField Nombre, TextField Apellidos, TextField Direccion, TextField Telefono){
        
        int fila = TablaTotalUsuarios.getSelectionModel().getSelectedIndex();
        
        if (fila>=0) {
            Object[] filaSeleccionada = TablaTotalUsuarios.getItems().get(fila);
            
            Identificacion.setText(filaSeleccionada[0].toString());
            Nombre.setText(filaSeleccionada[1].toString());
            Apellidos.setText(filaSeleccionada[2].toString());
            Direccion.setText(filaSeleccionada[3].toString());
            Telefono.setText(filaSeleccionada[4].toString());
            
        }
    }
    
    
    public void ModificarUsuarios(TextField identificacion, TextField nombre, TextField apellidos, TextField direccion, TextField telefono){
        
      CConexion objCConexion = new CConexion();
        String consulta = "UPDATE usuarios SET identificacion=?, nombre=?, apellidos=?, direccion=?, telefono=? WHERE identificacion=?";
        
        
        try {
            
            CallableStatement cs = objCConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, identificacion.getText());            
            cs.setString(2, nombre.getText());
            cs.setString(3, apellidos.getText());
            cs.setString(4, direccion.getText());
            cs.setString(5, telefono.getText());
            cs.setString(6, identificacion.getText());
            cs.execute();
            
            showAlert("Informacion", "Se modifico");
        } catch (Exception e) {
            showAlert("Informacion", "No se modifico:"+e.toString());
        } finally {
            objCConexion.cerrarConexio();
        }
        
    }
    
    
    
    public void EliminarUsuarios(TextField Identificacion){
        CConexion objCConexion = new CConexion();
        String consulta = "DELETE FROM usuarios WHERE usuarios.identificacion=?";
        
        try {
            CallableStatement cs = objCConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, Identificacion.getText());
            cs.execute();
            
            showAlert("Informacion", "Se elimino Correctamente");
        } catch (Exception e) {
            showAlert("Informacion", "No se elimino Correctamente"+e.toString());
        } finally {
            objCConexion.cerrarConexio();
        }
    }
    
    
    public void LimpiarCampos(TextField Nombre, TextField Apellidos, TextField Direccion, TextField Telefono, TextField Identificacion){
        Nombre.setText("");
        Apellidos.setText("");
        Direccion.setText("");
        Telefono.setText("");
        Identificacion.setText("");
    }
    
    
    
    private void showAlert(String Title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
