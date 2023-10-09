import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    //constructor insert from command or insert from file
    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean validWord(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isLetter(s.charAt(i))) return false;
        }
        return true;
    }

    public DictionaryManagement() {
        this.InsertFromFile("G:\\MY UET JOURNEY\\OOP-UET\\Dictionary_OOP\\dictionary.txt");
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
        try (
                FileWriter fileWriter = new FileWriter(path, true);
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
            word_target = word_target.trim().replaceAll("\\s+", "");
            word_target = word_target.substring(0, 1).toUpperCase() + word_target.substring(1);
            System.out.print("   Vietnamese: ");
            String word_explain = sc.nextLine();
            Word _word = new Word(word_target, word_explain);
            dictionary.addWords(_word);
        }
    }

    public void InsertFromFile(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                String lineWord = bufferedReader.readLine();
                String[] parts = lineWord.split("\t");
                if (parts.length == 2) {
                    if (!validWord(parts[0])) {
                        System.out.println(parts[0] + " is not English Word" + ". So that, cannot import word to dictionary");
                    } else {
                        dictionary.addWords(new Word(parts[0], parts[1]));
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    //LockUP
    public String DictionaryLookUp(String English) throws IOException {
        return dictionary.searchWord(English);
    }

    public void removeWord(String English) {
        dictionary.removeWord(English);
    }
}
