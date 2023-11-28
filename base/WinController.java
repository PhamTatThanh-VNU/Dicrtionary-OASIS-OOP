import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;

public class WinController {
    @FXML
    private Label guessedLettersLabel;

    private String answer;

    public void setAnswer(String answer) {
        this.answer = answer;
        updateGuessedLettersLabel();
    }

    private void updateGuessedLettersLabel() {
        // Use the answer string to update the label or perform any other logic you need
        guessedLettersLabel.setText("CORRECT LETTERS: " + answer.toUpperCase());
    }

    public void backGame(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/game.fxml"));
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setTitle("Hangman Game");
        window.setScene(new Scene(parent, 720, 480));
        window.show();
    }

    public void exitTab(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/hello.fxml"));
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setTitle("Dictionary");
        window.setScene(new Scene(parent, 720, 480));
        window.show();
    }
}
