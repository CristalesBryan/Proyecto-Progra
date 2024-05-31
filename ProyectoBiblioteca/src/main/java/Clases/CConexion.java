/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javafx.scene.control.Alert;

/**
 *
 * @author Crist
 */
public class CConexion {
    
    Connection conectar = null;
    
    String usuario = "ktjqumeb";
    String contrasenia = "myojHC3oFe8e2sZvUffgKx4vAIiX8ERj";
    String bd = "ktjqumeb";
    String ip = "bubble.db.elephantsql.com";
    String puerto = "5432";
    
    String cadena = "jdbc:postgresql://"+ip+":"+puerto+"/"+bd;
    
    
    public Connection establecerConexion(){
        
        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            //showAlert("Mensaje", "Se conecto a la base de datos");
            
        } catch (Exception e) {
            showAlert("Mensaje", "No se conecto a la base de datos, error:"+e.toString());
        }
        return conectar;
        
    } 
    
    
    private void showAlert(String Title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void cerrarConexio(){
    
        try {
            if (conectar!=null && !conectar.isClosed()) {
                conectar.close();
                //showAlert("Mensaje", "Conexion cerrada");
            }
    
        } catch (Exception e) {
            showAlert("Mensaje", "Error al cerrar conexion:"+e.toString());
        }
        
    }
}
