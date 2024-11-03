import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {
    private static String word;
    ArrayList<String> words = new ArrayList<>();
    static ArrayList<Character> alphabet = new ArrayList<>();
    static char[] correctLetters = {'?','?','?','?','?'};
    static int count = 0;

    /**
     * gets alphabet array list
     * @return alphabet array list
     */
    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    /**
     * initializes alphabet and creates object attribute
     * @param alphabet
     */
    public void setAlphabet(ArrayList<Character> alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * sets alphabet list to all capital letters using ascii values
     */
    public void initialize(){
        for(int i = 65; i <= 90; i++)  {
            alphabet.add((char)i);
        }
    }

    /**
     * generates random word that user has to guess, reading 5 letter words from dictionary
     * @return
     */
    public String generatePuzzle() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("C:\\Users\\amila\\OneDrive - Tredyffrin Easttown School District\\JavaWorkspace\\Turtle Graphics\\dictionary.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNext()) {
            String word = sc.next();
            if (word.length() == 5) {
                words.add(word);
            }
        }
        return words.get((int) (Math.random()*words.size()));

    }

    /**
     * reads and keeps track of user's guess, as well as giving instructions and word count at the end
     */
    public void guess() {
        System.out.println("==============================================================================");
        System.out.println("Welcome to Wordle. Uppercase letters are unused and lowercase letters are in the word,");
        System.out.println("but not found yet! Any letters with a # in place of them are not in the word, and !");
        System.out.println("means that the letter is in the correct position, which is displayed below the letters.");
        word = generatePuzzle();
        int count = 0;
        while (count  < 6) {
            Scanner sc = new Scanner(System.in);
            System.out.println("==============================================================================");
            System.out.println("Available Letters: " + alphabet);
            String guess = sc.next();
            guess = guess.toLowerCase();
            if(guess.length()!=5 || !validWord(guess))
                System.out.println("Invalid word, guess again!");
            else {
                if (readGuess(guess)){
                    System.out.println("You guessed the correct word in " + (count+1) + "/6 guesses!");
                    count=6;
                }
                else{
                    count++;
                }
            }
        }
        System.out.println("Thanks for playing!");
        System.out.println("==============================================================================");
    }

    public void guessStarter(){
        word = generatePuzzle();
    }

    public void guessAuto(String SolverGuess) {
            System.out.println("==============================================================================");
            System.out.println("Available Letters: " + alphabet);
            String guess = SolverGuess;
            guess = guess.toLowerCase();
            if(guess.length()!=5 || !validWord(guess))
                System.out.println("Invalid word, guess again!");
            else {
                if (readGuess(guess)){
                    System.out.println("You guessed the correct word in " + (count+1) + "/6 guesses!");
                    count=7;
                    System.exit(0);

                }
                else{
                    count++;
                }
            }
            if (count >6){
                System.out.println("The bot did not guess the word in 6 guesses");
                System.exit(0);
            }
    }

    /**
     * scans if user's guess is in the dictionary, to make sure it is a valid guess
     * @param s - guess being checked
     * @return whether or not the guess is valid
     */
    public boolean validWord(String s) {
        for (int i = 0; i <words.size();i++){
            if (s.equalsIgnoreCase(words.get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * returns int value from 0-25 to correlate to alphabet list
     * @param c - character to find int value for
     * @return
     */
    public static int returnAlphValue(char c){
        char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l',
                'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for (int i = 0; i < 26; i++){
            if (letters[i] == Character.toLowerCase(c))
                return i;
        }
        return -3;
    }

    /**
     * Reads user's guess and returns list of letters showing what is unused, misplaced, incorrect, or correct
     * @param guess - user's guess
     * @return
     */
    public static boolean readGuess(String guess){
        char[] wordLetters = word.toCharArray();
        char[] guessLetters = guess.toCharArray();

        if (guess.equalsIgnoreCase(word))
            return true;

        for (int i = 0; i <5; i++){
            if (wordLetters[i] == guessLetters[i])
                correctLetters[i] = Character.toUpperCase(wordLetters[i]);
        }
        //CORRECT LETTERS
        for (char c : correctLetters){
            if (c != '?'){
                alphabet.set(returnAlphValue(c),'!');
            }
        }

        //INCORRECT LETTERS
        ArrayList<Character> wordList = new ArrayList<>();
        for (char c : wordLetters){
            wordList.add(c);
        }
        int count = 0;
        for (char c : guessLetters){
            if (!wordList.contains(c) && wordLetters[count] != c){
                alphabet.set(returnAlphValue(c), '#');
            }
            count++;
        }

        //MISPLACED LETTERS
        for (int i = 0; i <5; i++){
            if (wordList.contains(guessLetters[i]) && wordLetters[i] != guessLetters[i]){
                alphabet.set(returnAlphValue(guessLetters[i]), Character.toLowerCase(guessLetters[i]));
            }
        }
        System.out.println(correctLetters);
        return false;
    }
}