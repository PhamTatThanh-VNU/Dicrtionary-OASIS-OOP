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

public class DictionaryController implements Initializable {
    @FXML
    private WebView textField;
    @FXML
    private ListView listView;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private Button text;
    @FXML
    private Button sql;
    @FXML
    private Button gg;

    int mode;// mode 0 la txt, mode 1 la sql, mode 2 google dich
    ResultSet rset;
    String test;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mode = 0;
        Connection con;
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        test = "select * from dictionary limit 100;";
        DictionaryManagement.getInstance().InsertFromFile("D:\\Dictionary-OASIS-OOP\\dictionary.txt");
        text.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0; -fx-background-color: #dfe6e9;");
        sql.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        gg.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(mode == 0) {
                    String tmp = listView.getSelectionModel().getSelectedItems().get(0).toString();
                    textField.getEngine().loadContent("");
                    tmp = DictionaryManagement.getInstance().StandardizedWord(tmp);
                    try {
                        textField.getEngine().loadContent(DictionaryManagement.getInstance().DictionaryLookUp(tmp));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (mode == 1) {
                    String tmp = listView.getSelectionModel().getSelectedItems().get(0).toString();
                    textField.getEngine().loadContent("");
                    tmp = tmp.toLowerCase();
                    test ="select * from dictionary where target = \""+ tmp + "\";";
                    try {
                        rset = con.createStatement().executeQuery(test);
                        while (rset.next()) {   // Repeatedly process each row
                            String id = rset.getString("id");  // retrieve a 'String'-cell in the row
                            String target = rset.getString("target");  // retrieve a 'double'-cell in the row
                            String definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                            textField.getEngine().loadContent(definition);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {

                }
            }
        });
        text.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(mode!=0) {
                    mode = 0;
                    textField.getEngine().load("");
                    listView.getItems().clear();
                    searchBar.clear();
                    text.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0; -fx-background-color: #dfe6e9;");
                    sql.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
                    gg.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
                }
            }
        });
        sql.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(mode!=1) {
                    mode = 1;
                    textField.getEngine().load("");
                    listView.getItems().clear();
                    searchBar.clear();
                    text.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
                    sql.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0; -fx-background-color: #dfe6e9;");
                    gg.setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
                }
            }
        });
        gg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(mode == 0) {
                    String tmp = searchBar.getText();
                    textField.getEngine().loadContent("");
                    searchBar.clear();
                    tmp = DictionaryManagement.getInstance().StandardizedWord(tmp);
                    try {
                        textField.getEngine().loadContent(DictionaryManagement.getInstance().DictionaryLookUp(tmp));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (mode == 1) {
                    String tmp = searchBar.getText();
                    textField.getEngine().loadContent("");
                    searchBar.clear();
                    tmp = tmp.toLowerCase();
                    test ="select * from dictionary where target = \""+ tmp + "\";";
                    try {
                        rset = con.createStatement().executeQuery(test);
                        while (rset.next()) {   // Repeatedly process each row
                            String id = rset.getString("id");  // retrieve a 'String'-cell in the row
                            String target = rset.getString("target");  // retrieve a 'double'-cell in the row
                            String definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                            textField.getEngine().loadContent(definition);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {

                }
            }
        });
        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(mode == 0) {
                    if (event.getCode() == KeyCode.ENTER) {
                        // Handle the Enter key press here
                        String tmp = searchBar.getText();
                        textField.getEngine().loadContent("");
                        searchBar.clear();
                        tmp = DictionaryManagement.getInstance().StandardizedWord(tmp);
                        try {
                            textField.getEngine().loadContent(DictionaryManagement.getInstance().DictionaryLookUp(tmp));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        listView.getItems().clear();
                        String keyword = searchBar.getText() + event.getText();
                        keyword = DictionaryManagement.getInstance().StandardizedWord(keyword);
                        for (Word word : Dictionary.getInstance().getWords()) {
                            if (word.getWord_target().startsWith(keyword) || word.getWord_target().toLowerCase().contains(keyword)) {
                                listView.getItems().add(word.getWord_target());
                            }
                        }
                    }
                } else if (mode == 1) {
                    if(event.getCode() == KeyCode.ENTER) {
                        String tmp = searchBar.getText();
                        textField.getEngine().loadContent("");
                        searchBar.clear();
                        tmp = tmp.toLowerCase();
                        test ="select * from dictionary where target = \""+ tmp + "\";";
                        try {
                            rset = con.createStatement().executeQuery(test);
                            while (rset.next()) {   // Repeatedly process each row
                                String id = rset.getString("id");  // retrieve a 'String'-cell in the row
                                String target = rset.getString("target");  // retrieve a 'double'-cell in the row
                                String definition = rset.getString("definition");       // retrieve a 'int'-cell in the row
                                textField.getEngine().loadContent(definition);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        listView.getItems().clear();
                        String keyword = searchBar.getText() + event.getText();
                        keyword = keyword.toLowerCase();
                        test = "select target from dictionary where target like \"%"+keyword+"%\";" ;
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
                } else {

                }
            }
        });
    }
}
