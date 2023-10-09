import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary extends Word {
    private ArrayList<Word> words = new ArrayList<Word>();
    private Trie trieToStore;

    public Trie getTrieToStore() {
        return trieToStore;
    }

    public void setTrieToStore(Trie trieToStore) {
        this.trieToStore = trieToStore;
    }

    //initialize
    public Dictionary() {
        words = new ArrayList<>();
        trieToStore = new Trie();
    }

    //func
    public void addWords(Word word) {
        words.add(word);
        if (trieToStore.search(word).equals("Cannot find word in Dictionary.")) {
            trieToStore.insert(word);
        } else {
            System.out.println("Vocab has existed!");
            return;
        }
    }

    public void removeWord(String English) {
        trieToStore.remove(English);
        for (int i = 0; i < words.size(); ++i){
            if(words.get(i).getWord_target().equals(English)){
                words.remove(i);
                System.out.println("Remove successfully");
                return;
            }
        }
        System.out.println("Word not found , no word remove");
    }

    public String searchWord(String English) {
        for (Word result : words) {
            if (result.getWord_target().equals(English)) {
                return trieToStore.search(result);
            }
        }
        return "Cannot find word.Please try again!";
    }

    public void DisplayAllWord() {
        System.out.println("+------+----------------------+---------------------------+");
        System.out.println("| NO   | English              | Vietnamese                |");
        System.out.println("+------+----------------------+---------------------------+");
        for (int i = 0; i < words.size(); ++i) {
            String format = String.format("| %-4d | %-20s | %-25s |",
                    (i+1),words.get(i).getWord_target(), words.get(i).getWord_explain());
            String line = String.format("%4s", "").replace(' ', '-');
            String line1 = String.format("%20s", "").replace(' ', '-');
            String line2  = String.format("%25s", "").replace(' ', '-');
            String formatDown = "+-"+line+"-+-"+line1+"-+-"+line2+"-+";
            System.out.println(format);
            System.out.println(formatDown);
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
