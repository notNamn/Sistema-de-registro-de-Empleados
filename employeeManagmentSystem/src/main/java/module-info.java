module prueba {
    requires javafx.controls;
    requires java.base;
    requires javafx.fxml;
    requires java.sql;
    exports prueba;
    exports prueba.controller;
    exports prueba.Conexion;
    opens prueba.controller to javafx.fxml;
}
