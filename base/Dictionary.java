import java.util.ArrayList;
import java.util.Collections;

public class Dictionary extends Word {
    private ArrayList<Word> words = new ArrayList<Word>();

    //initialize
    public Dictionary(){
        words = new ArrayList<>();
    }
    //func
    public void addWords(Word word) {
        words.add(word);
    }
    public void SortWord(){
        ArrayList<Word> wordList = new ArrayList<>(words);
        Collections.sort(wordList, (word1, word2) -> word1.getWord_target().compareToIgnoreCase(word2.getWord_target()));
        words = new ArrayList<>(wordList);
    }
    public void DisplayAllWord() {
        System.out.println("No  |  English  |  Vietnamese");
        for (int i = 0; i < words.size(); ++i) {
            System.out.println((i+1) +"   |  "+words.get(i).getWord_target()+"    |  "+words.get(i).getWord_explain());
        }
    }

    //encapsulation
    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }
}
