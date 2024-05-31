module com.mycompany.proyectobiblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.proyectobiblioteca to javafx.fxml;
    exports com.mycompany.proyectobiblioteca;
}
