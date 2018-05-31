package opus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Opus.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Opus");

        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);


        primaryStage.setScene(new Scene(root, controller.getFrameWidth(), controller.getFrameHeight()));
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}