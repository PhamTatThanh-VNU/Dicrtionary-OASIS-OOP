import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private Button bt;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private Label warning;
    private String line;
    private String line2;
    private String line3;
    @FXML
    private Button apply;
    @FXML
    private ListView<String> listView;
    private String[] list = {"star.jpg", "star.gif"};
    //private String[] choice2 = {"Truy vấn bằng hậu tố", "Truy vấn bằng tiền tố", "Chứa từ khóa cần tìm"};
    ObservableList<String> choice2 = FXCollections.observableArrayList("Truy vấn bằng hậu tố", "Truy vấn bằng tiền tố", "Chứa từ khóa cần tìm");
    ObservableList<String> choice1 = FXCollections.observableArrayList("Limit 10", "Limit 100", "Limit 1000", "No limit");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(list);
        comboBox.setItems(choice2);
        comboBox.setValue(DictionaryController.Mode);
        comboBox1.setItems(choice1);
        comboBox1.setValue(DictionaryController.Mode2);
        line2 = DictionaryController.Mode;
        line3 = DictionaryController.Mode2;
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                line = listView.getSelectionModel().getSelectedItems().get(0).toString();
            }
        });
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                line2 = comboBox.getValue();
            }
        });
        comboBox1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                line3 = comboBox1.getValue();
            }
        });
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/hello.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage =(Stage) bt.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Dictionary");
                stage.setScene(scene);
                stage.show();
            }
        });
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DictionaryController.Mode = line2;
                DictionaryController.Mode2 = line3;
                if (!(line == null)) {
                    warning.setText("Khởi động lại để sử dụng hình nền mới");
                    String filePath = "css/test.css";
                    try {
                        FileReader fileReader = new FileReader(filePath);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        StringBuilder cssContent = new StringBuilder();
                        String linee;
                        while ((linee = bufferedReader.readLine()) != null) {
                            if (linee.length() > 42 && linee.substring(0, 41).equals("    -fx-background-image : url('../image/")) {
                                linee = "    -fx-background-image : url('../image/" + line + "');";
                            }
                            cssContent.append(linee).append("\n");
                        }
                        bufferedReader.close();
                        fileReader.close();
                        FileWriter fileWriter = new FileWriter(filePath);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(cssContent.toString());
                        bufferedWriter.close();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}