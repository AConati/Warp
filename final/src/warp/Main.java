package warp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static javafx.scene.paint.Color.BLUE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("StartScreenView.fxml")));
        Parent root = loader.load();
        primaryStage.setTitle("Warp");

        StartScreenController startScreenController = loader.getController();
        root.setOnKeyPressed(startScreenController);
        root.setOnKeyReleased(startScreenController);

        primaryStage.setScene(new Scene(root, startScreenController.getFrameWidth(), startScreenController.getFrameHeight()));
        primaryStage.show();
        root.requestFocus();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}