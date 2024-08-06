package prueba;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * JavaFX App
 */
public class App extends Application {
    private double x = 0, y=0;
    
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/loginSystem.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            
            //trasparencia de la ventana login  
            scene.setOnMousePressed((MouseEvent mouseEvent) -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });
            
            scene.setOnMouseDragged((MouseEvent event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
                stage.setOpacity(.8);
            });
            
            scene.setOnMouseReleased((MouseEvent mouseevent)->{
                stage.setOpacity(1);
            });
            
             
            // lo correcto es usar un solo tipo de estilo pero funciona XD
            stage.initStyle(StageStyle.TRANSPARENT); // trasnparencia 
            
            stage.initStyle(StageStyle.DECORATED); // botonnes de accion
            
            stage.setTitle("Employee System"); // titulo de la ventana
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ocurrio un error al cargar la clase App.java");
        }
    }

    public static void main(String[] args) {
        launch();
    }

}