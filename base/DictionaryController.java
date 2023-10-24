import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {

    @FXML
    private Button searchButton;
    @FXML
    private TextField inputText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DictionaryManagement.getInstance().InsertFromFile("D:\\Dictionary-OASIS-OOP\\dictionary.txt");
        ImageView addIcon = new ImageView("add.png");
        if(addIcon == null) System.out.println("OK");
        else System.out.println("Okk");
        addIcon.setFitHeight(18);
        addIcon.setFitWidth(18);
        searchButton.setGraphic(addIcon);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = inputText.getText();
                s = DictionaryManagement.getInstance().StandardizedWord(s);
                try {
                    System.out.println(DictionaryManagement.getInstance().DictionaryLookUp(s));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                inputText.clear();
            }
        });
    }
}