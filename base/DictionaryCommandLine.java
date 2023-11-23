import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandLine {
    private Dictionary dictionary;
    //constructor
    public DictionaryCommandLine(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    //Func
    public void showAllWords(){
        dictionary.DisplayAllWord();
    }
    public void hangMan () {
        Game dictionaryGame = new Game(dictionary);
        dictionaryGame.HangMan();
    }
    public void DictionaryBasic(){
        DictionaryManagement management = new DictionaryManagement(dictionary);
        management.InsertCommandLine();
        showAllWords();
    }
    public void displayAdvance() throws IOException {
        Scanner sc = new Scanner(System.in);
        DictionaryManagement management = new DictionaryManagement(dictionary);
        management.InsertFromFile("D:\\VNU\\Sophomore(23-24)\\OOP\\finalTestUET\\Dictionary_fix\\dictionary.txt");
        System.out.println("Welcome to My Application!");
        System.out.println("---------------------------------");
        System.out.println("|--Select--|-------Option-------|");
        System.out.println("|    0     |   Exit             |");
        System.out.println("|    1     |   Add              |");
        System.out.println("|    2     |   Remove           |");
        System.out.println("|    3     |   Update           |");
        System.out.println("|    4     |   Display          |");
        System.out.println("|    5     |   Lookup           |");
        System.out.println("|    6     |   Search           |");
        System.out.println("|    7     |   Game             |");
        System.out.println("|    8     |   Import form file |");
        System.out.println("|    9     |   Export to file   |");
        System.out.println("---------------------------------");
        System.out.print("Your action: ");
        while (true) {
            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (choice) {
                case 0:
                    System.out.println("Goodbye!");
                    return;
                case 1:
                    management.InsertCommandLine();
                    break;
                case 2:
                    System.out.print("Enter the word to remove: ");
                    String word = sc.nextLine();
                    word = management.StandardizedWord(word);
                    dictionary.removeWord(word);
                    break;
                case 3:
                    management.UpdateWordFromCommandLine();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    System.out.print("Enter a word to lookup: ");
                    String lookupWord = sc.nextLine();
                    lookupWord = management.StandardizedWord(lookupWord);
                    System.out.println(management.DictionaryLookUp(lookupWord));
                    break;
                case 6:
                    System.out.print("Enter a keyword you want to search: ");
                    String searchPrefix = sc.nextLine();
                    management.dictionarySearcher(searchPrefix);
                    break;
                case 7:
                    // Add your game logic here (trivia game, flashcards, etc.)
                    //System.out.println("This feature is under development.");
                    hangMan();
                    break;
                case 8:
                    System.out.print("Enter the exact the filePath you want to import: ");
                    String importFilePath = sc.nextLine();
                    management.dictionaryImportFromFile(importFilePath);
                    break;
                case 9:
                    System.out.print("Enter the exact the filePath you want to export: ");
                    String exportFilePath = sc.nextLine();
                    management.dictionaryExportToFile(exportFilePath);
                    break;
                default:
                    System.out.println("Action not supported.");
                    break;
            }
            System.out.println("---------------------------------");
            System.out.println("|--Select--|-------Option-------|");
            System.out.println("|    0     |   Exit             |");
            System.out.println("|    1     |   Add              |");
            System.out.println("|    2     |   Remove           |");
            System.out.println("|    3     |   Update           |");
            System.out.println("|    4     |   Display          |");
            System.out.println("|    5     |   Lookup           |");
            System.out.println("|    6     |   Search           |");
            System.out.println("|    7     |   Game             |");
            System.out.println("|    8     |   Import form file |");
            System.out.println("|    9     |   Export to file   |");
            System.out.println("---------------------------------");
            System.out.print("Your action: ");

        }
    }
    //set get
    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public static void main(String[] args) throws IOException {
        Dictionary dic = new Dictionary();
        DictionaryCommandLine test = new DictionaryCommandLine(dic);
        test.displayAdvance();;
    }

}
