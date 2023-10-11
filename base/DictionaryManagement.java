import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    //constructor insert from command or insert from file
    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String StandardizedWord(String english) {
        english = english.toLowerCase();
        english = english.trim().replaceAll("\\s+", "");
        english = english.substring(0, 1).toUpperCase() + english.substring(1);
        return english;
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

    //Function
    public void UpdateWordFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the word , you want to update: ");
        String word_target = sc.nextLine();
        word_target = StandardizedWord(word_target);
        System.out.print("Enter the update word_explain: ");
        String update_word_explain = sc.nextLine();
        dictionary.UpdateWord(word_target, update_word_explain);
    }

    public void InsertCommandLine() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number words you need: ");
        int word_size = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < word_size; ++i) {
            System.out.print((i + 1) + ". English: ");
            String word_target = sc.nextLine();
            word_target = word_target.toLowerCase();
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

    public void dictionarySearcher(String keyword) {
        List<Word> searchResults = new ArrayList<>();

        for (Word word : dictionary.getWords()) {
            if (word.getWord_target().startsWith(keyword) || word.getWord_target().toLowerCase().contains(keyword)) {
                searchResults.add(word);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No matching words found.");
        } else {
            for (Word result : searchResults) {
                System.out.println(result.getWord_target() + ": " + result.getWord_explain());
            }
        }
    }

    public void dictionaryExportToFile(String filePath) {
        try {
            FileWriter file = new FileWriter(filePath);
            for (Word word : dictionary.getWords()) {
                String line = word.getWord_target() + "\t" + word.getWord_explain() + "\n";
                file.write(line);
            }
            file.close();
            System.out.println("Dictionary exported to file successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting dictionary to file: " + e.getMessage());
        }
    }
    public void dictionaryImportFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String word_target = parts[0];
                    String word_explain = parts[1];
                    Word word = new Word(word_target, word_explain);
                    dictionary.addWords(word);
                }
            }
            scanner.close();
            System.out.println("Dictionary imported from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void removeWord(String English) {
        dictionary.removeWord(English);
    }
}
