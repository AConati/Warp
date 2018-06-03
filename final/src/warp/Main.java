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
    public void init() {
        System.out.println("Hello");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loaderInitial = new FXMLLoader((getClass().getResource("StartScreenView.fxml")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Opus");

        GameController gameController = loader.getController();
        root.setOnKeyPressed(gameController);
        root.setOnKeyReleased(gameController);

        Paint background = BLUE;
        primaryStage.setScene(new Scene(root, gameController.getFrameWidth(), gameController.getFrameHeight(), background));
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