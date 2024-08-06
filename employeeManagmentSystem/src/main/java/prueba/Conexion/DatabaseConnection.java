package prueba.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class DatabaseConnection {
    // Aquí va tu código para la conexión a la base de datos
    private Connection conexion;
    private final String host = "jdbc:mysql://localhost/";
    private final String user = "root";
    private final String pass = "";
    // private String nombreBaseD = "login_system";

    // Devuelve un objeto de tipo Connection
    public Connection conectar(String nombreBD) {
        try {
            conexion = DriverManager.getConnection(host + nombreBD, user, pass);
            System.out.println("Conexión Exitosa!!!");
        } catch (SQLException e) {
            System.out.println("Conexion fallida");
            System.out.println(e.getMessage());
            conexion = null; // Asegura que la conexión sea null si falla
        }
        return conexion;
    }
    
    public void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Desconexión Exitosa!!!");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No hay conexión abierta");
        }
    }
    
}
