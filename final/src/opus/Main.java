package opus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Opus.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Opus");

        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);


        primaryStage.setScene(new Scene(root, controller.getFrameWidth(), controller.getFrameHeight()));
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