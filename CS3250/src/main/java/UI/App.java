package UI;

import javafx.application.Application;
import javafx.event.EventHandler;	
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    // Double vars for dragging window
    private double Xoff = 0;
    private double Yoff = 0;

    // Start method opens the sign in window
    @Override
    public void start(Stage primaryStage) throws Exception{
      Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root, 750, 600);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

    //Gets and sets current screen x/y values
    root.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Xoff = event.getSceneX();
            Yoff = event.getSceneY();
        }
    });

    //Allows the window to be dragged
    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            primaryStage.setX(event.getScreenX() - Xoff);
            primaryStage.setY(event.getScreenY() - Yoff);
        }
    });

    
        //Displays the stage(window)
        primaryStage.show();
        //showNextStage("DBScreen.fxml");
    }





    //Launches the program
    public static void main(String[] args) {
        launch(args);
    }
}
