import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Dictionary extends Word {
    private ArrayList<Word> words = new ArrayList<>();
    private static Dictionary d = null;

    //initialize

    private Dictionary() {
        words = new ArrayList<>();
    }
    public static synchronized Dictionary getInstance(){
        if (d == null)
            d = new Dictionary();

        return d;
    }

    public static void sortArrayList(ArrayList<Word> arrayList) {
        Collections.sort(arrayList, new Comparator<Word>() {
            public int compare(Word word1, Word word2) {
                return word1.getWord_target().compareTo(word2.getWord_target());
            }
        });
    }


    private int binarySearchWord(String English) {
        int left = 0;
        int right = words.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compareResult = words.get(mid).getWord_target().compareTo(English);
            if (compareResult == 0) {
                return mid;
            } else if (compareResult < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    //Function
    public void UpdateWord(String word_target, String update_word_explain) {
        for (Word word : words) {
            if (word.getWord_target().equals(word_target)) {
                word.setWord_explain(update_word_explain);
                System.out.println("Update successfully!");
                return;
            }
        }
        System.out.println("Word not found , update word failed!");
    }

    public void addWords(Word word) {
        int index = binarySearchWord(word.getWord_target());
        if (index < 0) {
            index = -(index + 1);
            words.add(index, word);
            sortArrayList(words);
        } else {
            System.out.println("Vocab has already existed!");
        }
    }

    public void removeWord(String English) {
        int index = binarySearchWord(English);
        if (index >= 0) {
            words.remove(index);
            System.out.println("Remove successfully!");
        } else {
            System.out.println("Word not found, no word removed.");
        }
    }

    public String searchWord(String English) {
        int index = binarySearchWord(English);
        if (index >= 0) {
            return "Meaning: "+words.get(index).getWord_explain();
        }
        return "Cannot find word. Please try again!";
    }


    public void DisplayAllWord() {
        System.out.println("+------+----------------------+---------------------------+");
        System.out.println("| NO   | English              | Vietnamese                |");
        System.out.println("+------+----------------------+---------------------------+");
        for (int i = 0; i < words.size(); ++i) {
            String format = String.format("| %-4d | %-20s | %-25s |",
                    (i + 1), words.get(i).getWord_target(), words.get(i).getWord_explain());
            String line = String.format("%4s", "").replace(' ', '-');
            String line1 = String.format("%20s", "").replace(' ', '-');
            String line2 = String.format("%25s", "").replace(' ', '-');
            String formatDown = "+-" + line + "-+-" + line1 + "-+-" + line2 + "-+";
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