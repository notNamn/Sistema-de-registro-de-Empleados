module prueba {
    // Requiere el módulo de controles de JavaFX, que 
    // proporciona componentes de UI como botones, cuadros de texto, etc.
    requires javafx.controls;

    // Requiere el módulo base de Java, que incluye las bibliotecas 
    // estándar fundamentales como java.lang, java.util, etc.
    requires java.base;

    // Requiere el módulo de FXML de JavaFX, que permite cargar 
    // interfaces de usuario definidas en archivos FXML.
    requires javafx.fxml;

    // Requiere el módulo de SQL, que permite trabajar con bases de datos mediante JDBC.
    requires java.sql;

    // Exporta el paquete "prueba" para que otras partes del 
    // código fuera de este módulo puedan acceder a él.
    exports prueba;

    // Exporta el paquete "prueba.controller" para que otras partes del 
    // código fuera de este módulo puedan acceder a los controladores.
    exports prueba.controller;

    // Exporta el paquete "prueba.Conexion" para que otras partes del
    // código fuera de este módulo puedan acceder a la clase DatabaseConnection.
    exports prueba.Conexion;

    // Permite que el paquete "prueba.controller" sea accesible para el módulo
    // javafx.fxml, lo que permite inyectar controladores en las interfaces FXML.
    opens prueba.controller to javafx.fxml;
}
