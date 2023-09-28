import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    //constructor insert from command or insert from file
    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public DictionaryManagement() {
        this.InsertCommandLine();
    }

    // function
    public void updateWord(Dictionary dictionary, int index, String meaning, String path) {
        try {
            dictionary.getWords().get(index).setWord_explain(meaning);
            DictionaryExportToFile(dictionary, path);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    public void DictionaryExportToFile(Dictionary dictionary, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : dictionary.getWords()) {
                bufferedWriter.write("|" + word.getWord_target() + "\n" + word.getWord_explain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    //add and remove
    public void deleteWord(Dictionary dictionary, int index, String path) {
        try {
            dictionary.getWords().remove(index);
            DictionaryExportToFile(dictionary, path);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    public void addWord(Word word, String path) {
        try (FileWriter fileWriter = new FileWriter(path, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("|" + word.getWord_target() + "\n" + word.getWord_explain());
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("IOException.");
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    //1.Insert
    public void InsertCommandLine() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number words you need: ");
        int word_size = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < word_size; ++i) {
            System.out.print((i + 1) + ". English: ");
            String word_target = sc.nextLine();
            System.out.print("   Vietnamese: ");
            String word_explain = sc.nextLine();
            Word _word = new Word(word_target, word_explain);
            dictionary.addWords(_word);
        }
    }

    public void InsertFromFile(Dictionary dictionary, String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String englishWord = bufferedReader.readLine();
            englishWord = englishWord.replace("|", "");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Word word = new Word();
                word.setWord_target(englishWord.trim());
                String meaning = line + "\n";
                while ((line = bufferedReader.readLine()) != null)
                    if (!line.startsWith("|")) meaning += line + "\n";
                    else {
                        englishWord = line.replace("|", "");
                        break;
                    }
                word.setWord_explain(meaning.trim());
                dictionary.addWords(word);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    //LockUP
    public int DictionaryLookUp(Dictionary dictionary, String keyWord) {
        try {
            dictionary.SortWord();
            int left = 0;
            int right = dictionary.getWords().size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int ans = dictionary.getWords().get(mid).getWord_target().compareTo(keyWord);
                if (ans == 0) return mid;
                if (ans <= 0) left = mid + 1;
                else right = mid - 1;
            }
        } catch (NullPointerException e) {
            System.out.println("Null exception");
        }
        return -1;
    }
}
