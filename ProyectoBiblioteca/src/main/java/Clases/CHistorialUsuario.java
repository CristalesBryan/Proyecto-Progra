/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 *
 * @author Crist
 */
public class CHistorialUsuario {
    public void historialUsuarios(TableView<Object[]> TablaTotalHistorialUsuarios) {
        Clases.CConexion objeCConexion = new Clases.CConexion();
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

                TablaTotalHistorialUsuarios.getItems().add(rowData);
            }
        } catch (Exception e) {
            showAlert("Error", "Error al guardar: " + e.toString());
        } finally {
            objeCConexion.cerrarConexio();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
