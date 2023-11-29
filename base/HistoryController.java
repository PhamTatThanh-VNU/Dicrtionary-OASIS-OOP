import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController extends Controller implements Initializable {
    private String test;
    @FXML
    private Button sql;
    @FXML
    private WebView webView;
    @FXML
    private ListView listView;
    @FXML
    private Button delete;
    private String temp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection con;
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        test = "select target, lichsu from history order by lichsu desc";
        try {
            ResultSet rset = con.createStatement().executeQuery(test);
            while (rset.next()) {   // Repeatedly process each row
                String target = rset.getString("target");
                target = target +" "+ rset.getString("lichsu");
                listView.getItems().add(target);
            }
        } catch (Exception e){

        }
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String word;
                word = "delete from history;";
                try {
                    con.createStatement().executeUpdate(word);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                listView.getItems().clear();
                webView.getEngine().loadContent("");
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!listView.getItems().isEmpty()) {
                    temp = listView.getSelectionModel().getSelectedItems().get(0).toString();
                    for (int i = 0; i < temp.length(); i++) {
                        if (temp.charAt(i) == '2' && temp.charAt(i + 1) == '0') {
                            temp = temp.substring(0, i - 1);
                            break;
                        }
                    }
                    webView.getEngine().loadContent("");
                    temp = temp.toLowerCase();
                    test = "select definition from history where target = \"" + temp + "\";";
                    try {
                        String definition;
                        ResultSet rset = con.createStatement().executeQuery(test);
                        while (rset.next()) {   // Repeatedly process each row
                            definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                            webView.getEngine().loadContent(definition);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        google.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/gg.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage =(Stage) st.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Dictionary");
                stage.setScene(scene);
                stage.show();
            }
        });
        st.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/setting.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage =(Stage) st.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Dictionary");
                stage.setScene(scene);
                stage.show();
            }
        });
        sql.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/hello.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage =(Stage) sql.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Dictionary");
                stage.setScene(scene);
                stage.show();
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/add.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage =(Stage) add.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Dictionary");
                stage.setScene(scene);
                stage.show();
            }
        });
        star.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/star.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage =(Stage) star.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Dictionary");
                stage.setScene(scene);
                stage.show();
            }
        });
        game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/menu.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 720, 480);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) game.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("Hangman Game");
                stage.setScene(scene);
                stage.show();
            }
        });
    }
}