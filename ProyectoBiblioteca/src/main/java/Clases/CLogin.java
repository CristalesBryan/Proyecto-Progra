/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


/**
 *
 * @author Crist
 */
public class CLogin {
    
    public void inicioSesion(TextField user, TextField pass){
        CConexion objetCConexion = new CConexion();
        
        String sql = "select * from datos where usuario=? and pass=?;";
        try (Connection conn = objetCConexion.establecerConexion()){
            conn.setAutoCommit(false);
            try {
            PreparedStatement psLogin = conn.prepareStatement(sql);
            psLogin.setString(1, user.getText());
            psLogin.setString(2, pass.getText());
            ResultSet rs = psLogin.executeQuery();
            
                if (rs.next()) {
                    showAlert("Informacion", "Sesion Iniciada Correctamente");
                } else {
                    showAlert("Informacion", "Ha ocurrido un error el usuario o la contrasenia son incorrectas");
                }
            
        } catch (Exception e) {
            showAlert("Informacion", "Sesion Iniciada Correctamente");
        } finally {
            objetCConexion.cerrarConexio();
        }
            
        } catch (Exception e) {
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
