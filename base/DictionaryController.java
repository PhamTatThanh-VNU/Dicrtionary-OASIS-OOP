import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
public class DictionaryController implements Initializable {

    public static String temp;
    public static String definition;
    public static String Mode = "Truy vấn bằng hậu tố";
    public static String Mode2 = "Limit 10";
    public static boolean b;
    ResultSet rset;
    Synthesizer synthesizer = null;
    String test;
    @FXML
    private Button st;
    @FXML
    private Button google;
    @FXML
    private Button add;
    @FXML
    private Button his;
    @FXML
    private Button star;
    @FXML
    private Button game;
    @FXML
    private WebView webView;
    @FXML
    private ListView listView;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private Button mark;
    @FXML
    private boolean m = false;
    @FXML
    private Button delete;
    @FXML
    private Button edit;
    @FXML
    private Button speaker;
    @FXML
    private ImageView mark_img;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            synthesizer = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();

            // Allocate synthesizer
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        speaker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(temp == null)) {
                    try {
                        synthesizer.resume();
                        synthesizer.speakPlainText(
                                temp, null);
                        synthesizer.waitEngineState(
                                Synthesizer.QUEUE_EMPTY);
                    } catch (Exception e) {
                    }
                }
            }
        });
        Connection con;
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!listView.getItems().isEmpty()) {
                    temp = listView.getSelectionModel().getSelectedItems().get(0).toString();
                    webView.getEngine().loadContent("");
                    temp = temp.toLowerCase();
                    test = "select * from starred where target = \"" + temp + "\";";
                    try {
                        rset = con.createStatement().executeQuery(test);
                        if (rset.next()) {
                            m = true;
                            mark_img.setImage(new Image(".\\image\\star3.png"));
                        } else {
                            m = false;
                            mark_img.setImage(new Image(".\\image\\star2.png"));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    test = "select definition from dictionary where target = \"" + temp + "\";";
                    try {
                        definition = null;
                        rset = con.createStatement().executeQuery(test);
                        while (rset.next()) {   // Repeatedly process each row
                            definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                            webView.getEngine().loadContent(definition);
                        }
                        definition = definition.replace("\"", "\\\"");
                        test = "insert into history values(\"" + temp + "\",\"" + definition + "\",current_timestamp());";
                        con.createStatement().executeUpdate(test);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                temp = searchBar.getText();
                if (temp != null) {
                    webView.getEngine().loadContent("");
                    searchBar.clear();
                    temp = temp.toLowerCase();
                    test = "select * from starred where target = \"" + temp + "\";";
                    try {
                        rset = con.createStatement().executeQuery(test);
                        if (rset.next()) {
                            m = true;
                            mark_img.setImage(new Image(".\\image\\star3.png"));
                        } else {
                            m = false;
                            mark_img.setImage(new Image(".\\image\\star2.png"));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    test = "select definition from dictionary where target = \"" + temp + "\";";
                    try {
                        definition = null;
                        rset = con.createStatement().executeQuery(test);
                        while (rset.next()) {   // Repeatedly process each row
                            definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                            webView.getEngine().loadContent(definition);
                            speaker.setVisible(true);
                        }
                        if(definition != null){
                            definition = definition.replace("\"", "\\\"");
                            test = "insert into history values(\"" + temp + "\",\"" + definition + "\",current_timestamp());";
                            con.createStatement().executeUpdate(test);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        mark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(temp!= null) {
                    if (!m) {
                        test = "insert into starred values (\"" + temp + "\");";
                        try {
                            con.createStatement().executeUpdate(test);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        mark_img.setImage(new Image(".\\image\\star3.png"));
                        m = true;
                    } else {
                        test = "delete from starred where target = \"" + temp + "\";";
                        try {
                            con.createStatement().executeUpdate(test);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        mark_img.setImage(new Image(".\\image\\star2.png"));
                        m = false;
                    }
                }
            }
        });
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                b = true;
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
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String word;
                word = "delete from dictionary where target = \"" + temp +"\";";
                try {
                    con.createStatement().executeUpdate(word);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                listView.getItems().remove(temp);
            }
        });
        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    temp = searchBar.getText();
                    webView.getEngine().loadContent("");
                    searchBar.clear();
                    temp = temp.toLowerCase();
                    test = "select * from starred where target = \"" + temp + "\";";
                    try {
                        rset = con.createStatement().executeQuery(test);
                        if(rset.next()){
                            m =true;
                            mark_img.setImage(new Image(".\\image\\star3.png"));
                        } else {
                            m = false;
                            mark_img.setImage(new Image(".\\image\\star2.png"));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    test ="select definition from dictionary where target = \""+ temp + "\";";
                    try {
                        definition = null;
                        rset = con.createStatement().executeQuery(test);
                        while (rset.next()) {   // Repeatedly process each row
                            definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                            webView.getEngine().loadContent(definition);
                        }
                        test = "insert into history values(\"" + temp +"\",\""+ definition +"\",current_timestamp());";
                        //System.out.println(test);
                        con.createStatement().executeUpdate(test);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if( event.getCode().isLetterKey() || event.getCode().isDigitKey() || (searchBar.getText() != null && event.getCode() == KeyCode.BACK_SPACE)){
                    listView.getItems().clear();
                    String keyword = searchBar.getText() + event.getText();
                    keyword = keyword.toLowerCase();
                    if(!keyword.isEmpty()) {
                        if (Mode.equals("Chứa từ khóa cần tìm")) {
                            test = "select target from dictionary where target like \"%" + keyword + "%\" "  + "order by target asc " + (Mode2.equals("No limit")?"":Mode2) + ";";
                        } else if (Mode.equals("Truy vấn bằng hậu tố")) {
                            test = "select target from dictionary where target like \"" + keyword + "%\" "  + "order by target asc " + (Mode2.equals("No limit")?"":Mode2) + ";";
                        } else {
                            test = "select target from dictionary where target like \"%" + keyword + "\" "   + "order by target asc " + (Mode2.equals("No limit")?"":Mode2) + ";";
                        }
                        try {
                            rset = con.createStatement().executeQuery(test);
                            while (rset.next()) {   // Repeatedly process each row
                                String target = rset.getString("target");
                                listView.getItems().add(target);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
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