//Welcome to my hangman game
//The methods are explained in the pdf file, so I left out the comments on the code for most of these

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    //all variables start out either empty or as 'temporary' so they can be reset after each round of the game
    static boolean game_running = true;
    static int health = 7;
    static List<String> game_word = new ArrayList<>();
    static List<String> secret_word = new ArrayList<>();
    static String display_word = "temporary";
    static String new_checker = "temporary";
    static String original_checker = "temporary";
    static String guess = "temporary";
    static List<String> wrong_guesses = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        // a while loop allows the game to be restarted or quit after each round
        while (game_running) {
            reset();
            display_rules();

            //gets a random word, and creates an array of its letters
            for (char ch : random_word().toCharArray()) {
                game_word.add(String.valueOf(ch));
            }
            //a string of the word is created to check against the user's guesses later
            original_checker = String.join("", game_word);

            //a list with underscores of the same length as the word is created to store the user's guesses
            for (String ad : game_word) {
                secret_word.add("_");
            }
            //the same list is created, except with spaces between the underscores to print an aesthetic mystery word for the user
            display_word = String.join(" ", secret_word);
            System.out.println("Let's start, your word looks like this: " + display_word);

            //the user is prompted to guess a letter as long as the game hasn't ended
            do {
                Scanner ask = new Scanner(System.in);
                System.out.println("Guess a letter");
                guess = ask.nextLine().toUpperCase();
                //this method (amongst other things) checks if the user's guess is in the word and adjusts the variables according to what the user has guessed
                checker(guess);
            } while (health != 0 & !Objects.equals(new_checker, original_checker));

            //asks if the user wants to play again or quit the program once the game has ended
            Scanner game = new Scanner(System.in);
            System.out.println("If you want to stop playing, enter 'p', or press any other key to play another round");
            String choice = game.nextLine();
            if (choice.equals("p")) {
                game_running = false;
                System.out.println("Thanks for playing, see you soon!");
            }
        }
    }

    public static void reset() {
        health = 7;
        game_word = new ArrayList<>();
        secret_word = new ArrayList<>();
        display_word = "temporary";
        new_checker = "temporary";
        original_checker = "temporary";
        guess = "temporary";
        wrong_guesses = new ArrayList<>();
    }

    public static void display_rules() {
        Scanner rules = new Scanner(System.in);
        System.out.println("\nWelcome to Hangman! \nPress 'r' to see rules, or press any other key to start playing right away");
        String game_options = rules.nextLine();
        if (Objects.equals(game_options, "r")) {
            System.out.println(" \n 1. The user has 7 tries to guess the letters of the mystery word\n 2. Guesses must only include single letters from a - z (non case-sensitive)\n 3. An element of the hangman drawing is added with every wrong guess, once the hangman is complete, the game is lost \n 4. If the user guesses all the letters in the word in the given number of tries, the game is won \n \n Have fun! \n ");
        }

    }
    public static String random_word() throws FileNotFoundException {
        File dictionary = new File("src/words.txt");
        Scanner reader = new Scanner(dictionary);
        ArrayList<String> word_list = new ArrayList<>();
        while (reader.hasNext()) {
            word_list.add(reader.nextLine());
        }
        int randomizer = (int)(Math.random() * word_list.size());
        return word_list.get(randomizer);
    }

    public static void checker(String guess) {
        if (game_word.contains(guess)) {

            secret_word.remove(game_word.indexOf(guess));
            secret_word.add(game_word.indexOf(guess), guess);

            game_word.add(game_word.indexOf(guess)+1, "*");
            game_word.remove(guess);

            display_word = String.join(" ", secret_word);
            new_checker = String.join("", secret_word);

            System.out.println("Nice: " + display_word);

            if (Objects.equals(new_checker, original_checker)) {
                System.out.println("You won!, great job!");
            }

        }
        else if (wrong_guesses.contains(guess)){
            System.out.println("This letter was already guessed, please try again");
        }
        else if (!guess.matches("^[A-Z]*$")) {
            System.out.println("Your guess is not a letter in the alphabet");
        }
        else {
            health = health -1;
            System.out.println("Not in the word!");
            hangman_drawing(health);
            wrong_guesses.add(guess);
            System.out.println("These are your wrong guesses: " + wrong_guesses);
        }
    }

    public static void hangman_drawing(int health) {
        if (health == 6) {
            System.out.println("  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========");
        }
        else if (health ==5) {
            System.out.println("  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========");
        }
        else if (health ==4) {
            System.out.println("  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========");
        }
        else if (health ==3) {
            System.out.println("  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========");
        }
        else if (health ==2) {
            System.out.println("  +---+\n  |   |\n  O   |\n /||  |\n      |\n      |\n=========");
        }
        else if (health ==1) {
            System.out.println("  +---+\n  |   |\n  O   |\n /||  |\n /    |\n      |\n=========");
        }
        else {
            System.out.println("  +---+\n  |   |\n  O   |\n /||  |\n / |  |\n      |\n=========");
            System.out.println("You Lost!, your word was: "+ original_checker);
        }
    }
}