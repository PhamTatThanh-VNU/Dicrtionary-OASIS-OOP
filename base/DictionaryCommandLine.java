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
    public void DictionaryBasic(){
        DictionaryManagement management = new DictionaryManagement(dictionary);
        management.InsertCommandLine();
        showAllWords();
    }
    public void displayAdvance() throws IOException {
        Scanner sc = new Scanner(System.in);
        DictionaryManagement management = new DictionaryManagement(dictionary);
        management.InsertFromFile("G:\\MY UET JOURNEY\\OOP-UET\\Dictionary_OOP\\dictionary.txt");
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
                    word = word.trim().replaceAll("\\s+","");
                    word = word.substring(0,1) + word.substring(1);
                    dictionary.removeWord(word);
                    break;
                case 3:
                    System.out.print("Enter the index of the word to update: ");
                    int indexToUpdate = sc.nextInt();
                    sc.nextLine(); // Consume the newline character
                    System.out.print("English: ");
                    String updatedWordTarget = sc.nextLine();
                    System.out.print("Vietnamese: ");
                    String updatedWordExplain = sc.nextLine();
                    Word updatedWord = new Word(updatedWordTarget, updatedWordExplain);
                    //dictionary.updateWord(indexToUpdate - 1, updatedWord);
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    System.out.print("Enter a word to lookup: ");
                    String lookupWord = sc.nextLine();
                    lookupWord = lookupWord.trim().replaceAll("\\s+", "");
                    lookupWord = lookupWord.substring(0,1).toUpperCase() + lookupWord.substring(1);
                    System.out.println(management.DictionaryLookUp(lookupWord));
                    break;
                case 6:
                    System.out.print("Enter a prefix to search: ");
                    String searchPrefix = sc.nextLine();
                    // management.dictionarySearcher(searchPrefix);
                    break;
                case 7:
                    // Add your game logic here (trivia game, flashcards, etc.)
                    System.out.println("This feature is under development.");
                    break;
                case 8:
                    /*System.out.print("Enter the file name to import from: ");
                    String importFileName = sc.nextLine();
                    management.InsertFromFile(dictionary,importFileName);*/
                    break;
                case 9:
                 /*   System.out.print("Enter the file name to export to: ");
                    String exportFileName = sc.nextLine();
                    management.DictionaryExportToFile(dictionary,exportFileName);*/
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
