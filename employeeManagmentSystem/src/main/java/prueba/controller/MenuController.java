package prueba.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

//
// import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// conexion con la base de datos
import prueba.Conexion.DatabaseConnection;
/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuController implements Initializable {
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private AnchorPane homeForml;
    
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLougOut;

    @FXML
    private Button btnSalary;

    

    @FXML
    private Label textEmployeeInactive;

    @FXML
    private Label textTotalEmployee;

    @FXML
    private Label textTotalUser;

    @FXML
    private Label username;
    
    private DatabaseConnection conectar = new DatabaseConnection();  
    private String nombreTabla = "user";  
    private String nombreBD = "login_system";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        calcularTotalEmployee();
        calcularTotalEmployeeInactive();
        calcularTotalUser();
        actualizarGraficoHomeEmployee(); 
    }    
    
    // FUNCIONALIDAD DEL MENU 
    
     @FXML
    void gotoEmployee(MouseEvent event) throws IOException {
         cargarMenu("employee");
    }

    @FXML
    void gotoExit(MouseEvent event) throws IOException {
        // salirApp();  
        confrimarExit();
    }

    @FXML
    void gotoHome(MouseEvent event) {
        borderPane.setCenter(homeForml);
    }

    @FXML
    void gotoSalary(MouseEvent event) throws IOException {
        cargarMenu("employeeSlary");
    }
    
    private void cargarMenu(String pagina) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + pagina + ".fxml"));
        borderPane.setCenter(root);
    }
    
    private void salirApp() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginSystem.fxml"));
        
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
   
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root));
        
        loginStage.show();
    }
    
    private void confrimarExit() throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Verificar Salida");
        alert.setContentText("Dese salir de la app?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get().equals(ButtonType.OK)){
            salirApp(); 
        }
        
    }
    

    
    private void calcularTotalUser(){
        String sql = "SELECT COUNT(idUser) FROM user";
        try (Connection conn = conectar.conectar(nombreBD);
             PreparedStatement psmt = conn.prepareStatement(sql); 
             ResultSet rs = psmt.executeQuery()   ) {
            
            if (rs.next()) {
                int totalUser = rs.getInt(1);
                textTotalUser.setText(String.valueOf(totalUser));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conectar.desconectar();
        }
    }
    
    private void calcularTotalEmployee(){
        String sql = "SELECT COUNT(idEmployee) FROM employee";
        try (Connection conn = conectar.conectar(nombreBD);
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery(sql) ) {
            if(rs.next()){
                int totalEmployee = rs.getInt(1);
                textTotalEmployee.setText(String.valueOf(totalEmployee));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conectar.desconectar();
        }
        
    }
    
    private void calcularTotalEmployeeInactive(){
        String sql = "SELECT COUNT(idEmployee) FROM employee WHERE salary = '0.0'";
        try (Connection conn = conectar.conectar(nombreBD);
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery(sql) ) {
            if(rs.next()){
                int totalEmployeeInactive = rs.getInt(1);
                textEmployeeInactive.setText(String.valueOf(totalEmployeeInactive));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conectar.desconectar();
        }
    }
    
    // METODO DEL GRAFICO AREA CHAR 
    @FXML
    private AreaChart<String, Number> graficoHomeEmployee;

    
    private void actualizarGraficoHomeEmployee() {
        String sql = "SELECT date, SUM(salary) as total_salary FROM employee GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";

        try (Connection conn = conectar.conectar(nombreBD);
                 PreparedStatement psmt = conn.prepareStatement(sql); 
                 ResultSet rs = psmt.executeQuery()) { 

            
            XYChart.Series<String, Number> series = new XYChart.Series<>();

           
            while (rs.next()) {
                
                series.getData().add(new XYChart.Data<>(rs.getString("date"), rs.getDouble("total_salary")));
            }

           
            graficoHomeEmployee.getData().add(series);
        } catch (Exception e) {
            
            e.printStackTrace();
        } finally {
           
            conectar.desconectar();
        }
    }

    

    
}
