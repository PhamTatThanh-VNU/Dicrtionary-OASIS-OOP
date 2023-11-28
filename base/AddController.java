import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    boolean check = false;
    @FXML
    private Button st;
    @FXML
    private Button sql;
    @FXML
    private Button his;
    @FXML
    private Button star;
    @FXML
    private Button google;
    @FXML
    private TextField add;
    @FXML
    private Button game;
    @FXML
    private TextField phonetic;
    @FXML
    private TextField type;
    @FXML
    private TextArea mean;
    @FXML
    private Button apply;
    @FXML
    private Label warning;
    @FXML
    private Label warning2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection con;
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(DictionaryController.b == true) {
            add.setText(DictionaryController.temp);
            String s = DictionaryController.definition;
            s = s.substring(s.indexOf('/') + 1);
            phonetic.setText(s.substring(0,s.indexOf('/')));
            s = s.substring(s.indexOf('*')+3);
            type.setText(s.substring(0,s.indexOf('<')));
            String tmpp = "";
            while(s.length()>5){
                s = s.substring(s.indexOf('>') + 1);
                tmpp += s.substring(0,s.indexOf('<')) + '\n';
            }
            tmpp = tmpp.substring(0,tmpp.length()-2);
            mean.setText(tmpp);
            DictionaryController.b = false;
            String word;
            word = "delete from dictionary where target = \"" + add.getText() +"\";";
            try {
                con.createStatement().executeUpdate(word);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            check = true;
        }
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (add.getText().isEmpty() || mean.getText().isEmpty() || phonetic.getText().isEmpty() || type.getText().isEmpty()) {
                    warning2.setText("Bạn hãy điền đủ các trường còn trống");
                } else if(check == false){
                    warning2.setText("Không thể thêm từ đã có sẵn trong cơ sở dữ liệu");
                }else {
                    warning2.setText("");
                    String word;
                    String meaning;
                    meaning = mean.getText();
                    word = "<I><Q>@" + add.getText() + " /" + phonetic.getText() +"/<br />*  " + type.getText() +"<br />";
                    int s = meaning.length();
                    for(int i = 0; i<s;i++){
                        word += (meaning.charAt(i)!='\n'?meaning.charAt(i):"<br />");
                    }
                    word += "</Q></I>";
                    word = "insert into dictionary (target, definition) values (\"" + add.getText() + "\", \"" + word + "\");";
                    try {
                        con.createStatement().executeUpdate(word);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    add.clear();
                    phonetic.clear();
                    type.clear();
                    mean.clear();
                }
                }
        });
        add.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                warning2.setText("");
                String keyword = add.getText() + event.getText();
                keyword = keyword.toLowerCase();
                if(!keyword.isEmpty()) {
                    keyword = "select target from dictionary where target = \""+ keyword +"\";";
                    try {
                        ResultSet rset = con.createStatement().executeQuery(keyword);
                        if (rset.next()){
                            warning.setText("Đã có từ này trong cơ sở dữ liệu");
                            check = false;
                        } else {
                            warning.setText("");
                            check = true;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        phonetic.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                warning2.setText("");
            }
        });
        mean.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                warning2.setText("");
            }
        });
        type.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                warning2.setText("");
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
                Stage stage =(Stage) his.getScene().getWindow();
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