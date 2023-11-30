import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game implements GameBase {
    private static Game g = null;
    private Game() {
    }
    public static synchronized Game getInstance()
    {
        if (g == null)
            g = new Game();

        return g;
    }

    public Word randomWord() {
        Random rand = new Random();
        ArrayList<Word> words = Dictionary.getInstance().getWords();
        return words.get(rand.nextInt(words.size()));
    }

    // hinh anh hangman
    private static void printHangedMan(int wrongCount) {
        if (wrongCount == 0) {
            System.out.println("--------");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }
        if (wrongCount == 1) {
            System.out.println("--------");
            System.out.println("|    |  ");
            System.out.println("|    0  ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }

        if (wrongCount == 2) {
            System.out.println("--------");
            System.out.println("|    |  ");
            System.out.println("|    0  ");
            System.out.println("|    |  ");
            System.out.println("|    |  ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }

        if (wrongCount == 3) {
            System.out.println("--------");
            System.out.println("|    |  ");
            System.out.println("|    0  ");
            System.out.println("|   /|  ");
            System.out.println("|    |  ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }

        if (wrongCount == 4) {
            System.out.println("--------");
            System.out.println("|    |  ");
            System.out.println("|    0  ");
            System.out.println("|   /|\\  ");
            System.out.println("|    |  ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }

        if (wrongCount == 5) {
            System.out.println("--------");
            System.out.println("|    |  ");
            System.out.println("|    0  ");
            System.out.println("|   /|\\  ");
            System.out.println("|    |  ");
            System.out.println("|   /   ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }

        if (wrongCount == 6) {
            System.out.println("--------");
            System.out.println("|    |  ");
            System.out.println("|    0  ");
            System.out.println("|   /|\\  ");
            System.out.println("|    |  ");
            System.out.println("|   / \\ ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("|       ");
            System.out.println("========");
        }
    }

    // guess letter and check letter appear
    private static boolean getPlayerGuess(Scanner keyboard, Word word, List<Character> playerGuesses) {
        System.out.print("Please enter a letter: ");
        String letterGuess = keyboard.nextLine();
        playerGuesses.add(letterGuess.charAt(0));

        String wordTargetLowerCase = word.getWord_target().toLowerCase();

        return wordTargetLowerCase.contains(letterGuess.toLowerCase());
    }


    // display and check equal word
    private static boolean printWordState(Word word, List<Character> playerGuesses) {
        int correctCount = 0;
        System.out.print("the word current: ");
        for (int i = 0; i < word.getWord_target().length(); i++) {
            char guessedChar = word.getWord_target().charAt(i);
            char guessedCharLowerCase = Character.toLowerCase(guessedChar);
            if (playerGuesses.contains(guessedCharLowerCase)) {
                System.out.print(guessedChar);
                correctCount++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();

        return (word.getWord_target().length() == correctCount);
    }

    public void HangMan() {
        System.out.println("------------------WELCOME TO  HANGMAN_UET------------------");
        Scanner keyboard = new Scanner(System.in);
        Word keyWord = randomWord();
        List<Character> playerGuesses = new ArrayList<>();
        int wrongCount = 0;
        int guessTimes = 1;
        System.out.println("Key word in Vietnamese mean: " + keyWord.getWord_explain());
        // System.out.println("Key word in Vietnamese mean: " + keyWord.getWord_target());
        System.out.println("Key word has : " + keyWord.getWord_target().length() + " letters");

        while (true) {
            System.out.println("-----------------------guessTimes: " + guessTimes + "-----------------------"); guessTimes++;
            printHangedMan(wrongCount);

            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The word was: " + keyWord.getWord_target());
                System.out.println("---------------GOOD BYE!  FROM UET WITH LOVE---------------");
                break;
            }

            printWordState(keyWord, playerGuesses);

            if (!getPlayerGuess(keyboard, keyWord, playerGuesses)) {
                wrongCount++;
            }else {
                printWordState(keyWord, playerGuesses);
                System.out.print("Please enter your guess for the word:");
                String guessWord = keyboard.nextLine();
                guessWord = DictionaryManagement.StandardizedWord(guessWord);
                if (guessWord.equals(keyWord.getWord_target())) {
                    System.out.println("You win!");
                    System.out.println("---------------WINNER WINNER  CHICKEN DINNER---------------");
                    break;
                } else {
                    System.out.println("Guessed the correct letter but not the word");
                }
            }

            if (printWordState(keyWord, playerGuesses)) {
                System.out.println("You win!");
                System.out.println("---------------WINNER WINNER  CHICKEN DINNER---------------");
                break;
            }

        }
    }
}
