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
public class GoogleController implements Initializable {
    @FXML
    private Button st;
    @FXML
    private Button sql;
    @FXML
    private Button add;
    @FXML
    private Button star;
    @FXML
    private Button his;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    }
}