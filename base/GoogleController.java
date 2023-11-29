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
public class GoogleController extends  Controller implements Initializable {
    @FXML
    private Button sql;
    @FXML
    private TextArea textArea1;
    @FXML
    private TextArea textArea2;
    @FXML
    private Button trans;
    @FXML
    private Button change;
    @FXML
    private Label e;
    @FXML
    private Label v;
    boolean vie = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                double a = e.getLayoutX();
                double b = e.getLayoutY();
                e.setLayoutX(v.getLayoutX());
                e.setLayoutY(v.getLayoutY());
                v.setLayoutY(b);
                v.setLayoutX(a);
                vie = !vie;
            }
        });
        his.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("fxml/history.fxml"));
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
        trans.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                try {
                    if(!vie)
                    textArea2.setText(Translator.translate("en","vi",textArea1.getText()));
                    else textArea2.setText(Translator.translate("vi","en",textArea1.getText()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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