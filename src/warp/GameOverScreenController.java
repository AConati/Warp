/**
 * GameOverScreenController.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that manipulates game over screen view to reflect key input.
 */

package warp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;


public class GameOverScreenController implements EventHandler<KeyEvent> {

    @FXML private AnchorPane gameOverScreenPane;
    @FXML private Label gameOverLabel;
    @FXML private Label gameOverText;
    @FXML private Label highScoresLabel;
    @FXML private Label newHighScoreLabel;
    @FXML private Label highScoreNameLabel;

    private Model model = null;
    private int score = 0;
    private List<Model.HighScore> highScores;
    private boolean newHighScore = false;

    public GameOverScreenController(){}

    public void initialize() {
        this.gameOverLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",100));
        this.gameOverLabel.setText("GAME\nOVER");
        this.gameOverLabel.setTextFill(Color.RED);

        this.gameOverText.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.gameOverText.setTextFill(Color.WHITE);

        this.highScoresLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.highScoresLabel.setTextFill(Color.WHITE);

        this.newHighScoreLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.newHighScoreLabel.setTextFill(Color.WHITE);

        this.highScoreNameLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.highScoreNameLabel.setTextFill(Color.WHITE);

    }

    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if(code == KeyCode.ENTER) {
            try {
                if(newHighScore) {
                    model.writeHighScore("src/warp/highScores.txt", highScoreNameLabel.getText(), model.getScore());
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreenView.fxml"));
                Parent newRootNode = loader.load();
                Scene scene = this.gameOverScreenPane.getScene();
                scene.setRoot(newRootNode);
                StartScreenController startScreenController = loader.getController();
                scene.setOnKeyPressed(startScreenController);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        else if(this.newHighScore){
            String codeText = keyEvent.getText();
            codeText = codeText.toUpperCase();
            if(codeText.length() == 1) {
                char letter = codeText.charAt(0);
                this.updateNewHighScoreLabel(letter);
            }
        }

    }

    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Updates the score label to reflect the model.
     */

    public void setScoreLabel() {
        if (model == null)
            return;
        this.score = model.getScore();
        this.gameOverText.setText(String.format("Your score: %d", this.score) +
                "\nPress ENTER to return\nto the main menu.");
    }

    /**
     * Updates the high scores listed to reflect the model.
     */

    public void setHighScoresLabel() {
        if (model == null)
            return;
        String highScoresText = "HIGH SCORES\n\n";
        for(Model.HighScore highScore : this.model.getHighScores()) {
            highScoresText = highScoresText + highScore.getName() + ": " + highScore.getScore() + "\n";
        }
        this.highScoresLabel.setText(highScoresText);
    }


    public void setIsNewHighScore(boolean isHighScore) {
        this.newHighScore = isHighScore;
        if(isHighScore)
            this.newHighScoreLabel.setText("New High Score!\nEnter your name:");
    }

    /**
     * Updates the high score name based on the next letter entered. If the
     * name exceeds three letters, it sets it to the last three letters of the name.
     * @param letter The next letter input by the user entering their name for the high score.
     */

    private void updateNewHighScoreLabel(char letter) {
        if(this.highScoreNameLabel.getText().length() >= 3) {
            highScoreNameLabel.setText(highScoreNameLabel.getText().substring(1));
        }
        this.highScoreNameLabel.setText(highScoreNameLabel.getText() + letter);
    }


}
