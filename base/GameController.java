import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GameController implements GameBase {
    private static GameController gC = null;

    ArrayList<Word> words = Dictionary.getInstance().getWords();
    public static synchronized GameController getInstance()
    {
        if (gC == null)
            gC = new GameController();

        return gC;
    }

    // lay random ket qua
    public Word randomWord() {
        Random rand = new Random();
        ArrayList<Word> words1 = Dictionary.getInstance().getWords();
        Word selectWord;
        do {
            selectWord = words1.get(rand.nextInt(words1.size()));
        } while (selectWord.getWord_target().length() > 8);

        return selectWord;
    }

    @FXML
    ImageView img;
    Image image2 = new Image(getClass().getResourceAsStream("image/2.png"));
    Image image3 = new Image(getClass().getResourceAsStream("image/3.png"));
    Image image4 = new Image(getClass().getResourceAsStream("image/4.png"));
    Image image5 = new Image(getClass().getResourceAsStream("image/5.png"));
    Image image6 = new Image(getClass().getResourceAsStream("image/6.png"));
    Image image7 = new Image(getClass().getResourceAsStream("image/7.png"));
    Image image8 = new Image(getClass().getResourceAsStream("image/8.png"));

    @FXML
    TextField tf1;
    @FXML
    TextField tf2;
    @FXML
    TextField tf3;
    @FXML
    TextField tf4;
    @FXML
    TextField tf5;
    @FXML
    TextField tf6;
    @FXML
    TextField tf7;
    @FXML
    TextField tf8;
    @FXML
    TextField input;
    @FXML
    Label hint;
    @FXML
    Label letter_count;
    @FXML
    Label hint_label;

    TextField textField = new TextField();
    Button checkButton = new Button("Check");

    public boolean validWord(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isLetter(s.charAt(i))) return false;
        }
        return true;
    }


    public static void sortArrayList(ArrayList<Word> arrayList) {
        Collections.sort(arrayList, new Comparator<Word>() {
            public int compare(Word word1, Word word2) {
                return word1.getWord_target().compareTo(word2.getWord_target());
            }
        });
    }

    Word rand = randomWord();
    String answer = rand.getWord_target();
    String word_hint = rand.getWord_target() + " " + rand.getWord_explain();
    String[] split = word_hint.split(" ", 2);
    String word = split[0];
    String hint_str = split[1];
    int letter_size = word.length();


    public void initialize() throws Exception {
        setHint();
    }

    public void setHint(){
        hint.setText(hint_str);
        letter_count.setText(letter_size+" Letters");

        if(letter_size==7){
            tf8.setVisible(false);
        }
        if(letter_size==6){
            tf7.setVisible(false);
            tf8.setVisible(false);
        }
        if(letter_size==5){
            tf6.setVisible(false);
            tf7.setVisible(false);
            tf8.setVisible(false);
        }
        if(letter_size==4){
            tf5.setVisible(false);
            tf6.setVisible(false);
            tf7.setVisible(false);
            tf8.setVisible(false);
        }
    }


    public void CheckInput() throws Exception {
        String str = input.getText().toUpperCase();
        if (word.toUpperCase().contains(str)) {
            int index = 0;
            for(int i=0; i<word.length(); i++) {
                char c = word.charAt(i);
                if (String.valueOf(c).equalsIgnoreCase(str)) {
                    setLetter(index, Character.toString(c));
                }
                index++;
            }
        }
        else {
            setImage();
        }
    }

    // đếm số lượng từ mình đã đoán
    private int guessedCharacterCount = 0;

    public void setLetter(int index,String str){
        str = str.toUpperCase();
        if(index==0)
            tf1.setText(str);
        else if(index==1)
            tf2.setText(str);
        else if(index==2)
            tf3.setText(str);
        else if(index==3)
            tf4.setText(str);
        else if(index==4)
            tf5.setText(str);
        else if(index==5)
            tf6.setText(str);
        else if(index==6)
            tf7.setText(str);
        else if(index==7)
            tf8.setText(str);
        guessedCharacterCount++;

        if (guessedCharacterCount == letter_size && life > 1) {
            showWin();
        }
    }

    public void showWin() {
        try {
            // Đóng cửa sổ hiện tại
            Stage currentStage = (Stage) img.getScene().getWindow();
            currentStage.close();

            // Mở cửa sổ chúc mừng khi trò chơi đã thắng
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/win.fxml"));
            Parent root = loader.load();

            WinController winController = loader.getController();
            winController.setAnswer(answer);

            Stage congratulationsStage = new Stage();
            congratulationsStage.setTitle("Hangman Game");
            congratulationsStage.setScene(new Scene(root, 720, 480));
            congratulationsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int life=7;
    public void setImage(){
        if (life >= 1 && life <= 7) {
            if (life == 7)
                img.setImage(image2);
            else if (life == 6)
                img.setImage(image3);
            else if (life == 5)
                img.setImage(image4);
            else if (life == 4)
                img.setImage(image5);
            else if (life == 3)
                img.setImage(image6);
            else if (life == 2)
                img.setImage(image7);
            else if (life == 1) {
                img.setImage(image8);
                for (int i = 0; i < word.length(); i++) {
                    setLetter(i, Character.toString(word.charAt(i)));
                }
            }

            life--;
        }
    }


    public void changeScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/game.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Hangman Game");
        window.setScene(new Scene(parent, 720, 480));
        window.show();
    }

    @FXML
    void setOnEnterPressed(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CheckInput();
            } catch (Exception event1) {
                event1.printStackTrace();
            } finally {
                input.clear();
            }
        }
    }

    public void returnMenu(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/menu.fxml"));
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setTitle("Hangman Game");
        window.setScene(new Scene(parent, 720, 480));
        window.show();
    }
}
