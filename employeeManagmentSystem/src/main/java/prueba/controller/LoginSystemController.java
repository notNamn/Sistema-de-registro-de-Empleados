package prueba.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import prueba.Conexion.DatabaseConnection;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class LoginSystemController implements Initializable {
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button close;
    @FXML
    private Button loginBtn;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private TextField passwordText;  // Campo para mostrar la contraseña en texto plano
    @FXML
    private CheckBox showPassword;

    private DatabaseConnection conectar = new DatabaseConnection();  // Inicializa la conexión
    private String nombreTabla = "user";  // Nombre de la tabla en la base de datos
    private String nombreBD = "login_system";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sincroniza el contenido del PasswordField y el TextField para que siempre tengan el mismo valor.
        passwordText.textProperty().bindBidirectional(password.textProperty());
    } 
    
    // Limpiar los campos
    public void close() {
        // Limpiar los campos de texto
        username.clear();
        password.clear();
        passwordText.clear();
        // System.exit(0); // Si necesitas cerrar la aplicación, descomenta esta línea
    }
    
    // Verificar que los campos no estén vacíos
    private boolean verificarEntrada(){
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            showAlert("Por favor introduce un usuario y/o contraseña válido");
            return false;
        }
        return true;
    }
    
    // Acción para cambiar de escena después del login exitoso
    private void accionLogearse(){
        try {
            String ruta = "/fxml/menu.fxml"; // Cambia a la ruta correcta de tu siguiente escena
            Stage stage = (Stage)loginBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException ex) {
            showAlert("Ocurrió un error al logearse: " + ex.getMessage());
        }
    }
    
    // Mostrar alerta con un mensaje específico
    public void showAlert(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Verificación de las credenciales de usuario en la base de datos
    public void verificarCredenciales(){
        if (!verificarEntrada()) {
            return; // Si la entrada no es válida, detener el proceso
        }

        String user = username.getText();
        String pass = password.getText();  // Usamos passwordText y password ya que están sincronizados
        
        String sql = "SELECT * FROM " + nombreTabla + " WHERE nameUser = ? AND passUser = ?";
        try (Connection conn = conectar.conectar(nombreBD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            
            mostrarConsola(user, pass);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Usuario encontrado, proceder con el login
                    accionLogearse();
                } else {
                    // Usuario no encontrado o credenciales incorrectas
                    showAlert("Usuario o contraseña incorrectos.");
                }
            } catch (Exception e) {
                showAlert("Ocurrió un error al procesar la consulta.");
            }
            
        } catch (Exception e) {
            showAlert("Ocurrió un error al consultar con la base de datos.");
        } finally {
            conectar.desconectar();
        }
    }
    
    // Método que se llama cuando se presiona el botón de login
    @FXML
    public void handleLoginButtonAction() {
        verificarCredenciales();
    }
    
    //// Mostrar en consola
    private void mostrarConsola(String user, String pass){
        System.out.println("Bienvenido! -> Usuario: " + user + " Contraseña: " + pass);
    }
    
    // Alternar visibilidad de la contraseña
    @FXML
    public void togglePasswordVisibility() {
        if (showPassword.isSelected()) {
            passwordText.setVisible(true);
            password.setVisible(false);
        } else {
            passwordText.setVisible(false);
            password.setVisible(true);
        }
    }
}
