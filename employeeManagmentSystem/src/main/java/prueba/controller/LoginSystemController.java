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

    private DatabaseConnection conectar = new DatabaseConnection(); 
    private String nombreTabla = "user";  
    private String nombreBD = "login_system";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        passwordText.textProperty().bindBidirectional(password.textProperty());
    } 
    

    public void close() {
    
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
    
  
    private void accionLogearse(){
        try {
            String ruta = "/fxml/menu.fxml"; 
            Stage stage = (Stage)loginBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException ex) {
            showAlert("Ocurrió un error al logearse: " + ex.getMessage());
        }
    }
    
    public void showAlert(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    

    public void verificarCredenciales(){
        if (!verificarEntrada()) {
            return; 
        }

        String user = username.getText();
        String pass = password.getText();  
        
        String sql = "SELECT * FROM " + nombreTabla + " WHERE nameUser = ? AND passUser = ?";
        try (Connection conn = conectar.conectar(nombreBD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            
            mostrarConsola(user, pass);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                   
                    accionLogearse();
                } else {
                    
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
    
    
    @FXML
    public void handleLoginButtonAction() {
        verificarCredenciales();
    }
    
  
    private void mostrarConsola(String user, String pass){
        System.out.println("Bienvenido! -> Usuario: " + user + " Contraseña: " + pass);
    }
    
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
