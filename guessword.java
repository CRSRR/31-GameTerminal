import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Hangman game with randomly selected animal names from a file.
 * 
 * @Author Clayton
 * @Version 1.0
 */
public class guessword {
    private static final Random RANDOM = new Random();
    private static Scanner scanner = new Scanner(System.in);
    private static List<String> animals = new ArrayList<>();

    public static void main(String[] args) {
        loadAnimalsFromFile("animals.txt");
        
        System.out.println("Welcome to Hangman Animal Edition");

        while (true) {
            System.out.println("1. Start Game");
            System.out.println("2. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    System.out.println("Thanks for playing!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
                    break;
            }
        }
    }

    public static void startGame() {
        System.out.println("Select Difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        int difficulty = scanner.nextInt();
        scanner.nextLine();

        int attempts;

        switch (difficulty) {
            case 1:
                attempts = 10;
                break;
            case 2:
                attempts = 5;
                break;
            case 3:
                attempts = 3;
                break;
            default:
                System.out.println("Invalid choice, defaulting to Easy");
                attempts = 5;
                break;
        }

        String word = generateRandomAnimal();
        char[] guessedWord = new char[word.length()];
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }

        while (attempts > 0 && new String(guessedWord).indexOf('_') != -1) {
            System.out.println("Word: " + new String(guessedWord));
            System.out.println("Attempts left: " + attempts);
            System.out.print("Guess a letter: ");
            char guess = scanner.nextLine().toUpperCase().charAt(0);
            boolean correctGuess = false;

            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    guessedWord[i] = guess;
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                attempts--;
                System.out.println("Incorrect guess!");
            }
        }

        if (new String(guessedWord).indexOf('_') == -1) {
            System.out.println("You guessed the word: " + word);
        } else {
            System.out.println("Game Over. The word was: " + word);
        }
    }

    private static void loadAnimalsFromFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            while (fileScanner.hasNextLine()) {
                String animal = fileScanner.nextLine().toUpperCase().trim();
                if (!animal.isEmpty()) {
                    animals.add(animal);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Animal list file not found. Please ensure 'animals.txt' is in the correct location.");
            System.exit(1);
        }
    }

    private static String generateRandomAnimal() {
        int index = RANDOM.nextInt(animals.size());
        return animals.get(index);
    }
}
