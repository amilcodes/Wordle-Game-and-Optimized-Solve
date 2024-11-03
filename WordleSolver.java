import java.util.ArrayList;

public class WordleSolver {

    static ArrayList<String> usedWords = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("AUTOMATED SOLVING");
        Wordle w = new Wordle();
        w.guessStarter();
        w.initialize();
        w.guessAuto(guesser(w));
        w.guessAuto(guesser(w));
        w.guessAuto(guesser(w));
        w.guessAuto(guesser(w));
        w.guessAuto(guesser(w));
        w.guessAuto(guesser(w));
    }

    public static String guesser(Wordle w){
        ArrayList<String> validGuesses = new ArrayList<>();
        validGuesses = w.words;
        validGuesses.removeAll(usedWords);
        //CONDITIONS START HERE
        ArrayList<String> removedGuesses = new ArrayList<>();
        for(int i = 0; i < validGuesses.size(); i++) {
            //1. REMOVE ALL WORDS THAT DONT CONTAIN CORRECT POSITIONING OF IDENTIFIED LETTERS, READ OFF OF w.correctLetters
            for(int k = 0; k < 5; k++) {
                if(w.correctLetters[k] != '?' && validGuesses.get(i).charAt(k) != Character.toLowerCase(w.correctLetters[k])) {
                    removedGuesses.add(validGuesses.get(i));
                }
            }

            for(int j = 0; j < w.alphabet.size(); j++) {
                //2. REMOVE ALL WORDS THAT DONT CONTAIN ALL YELLOW/LOWERCASE LETTERS
                if(w.getAlphabet().get(j) != '!' && w.getAlphabet().get(j) != '#' && Character.isLowerCase(w.getAlphabet().get(j))) {
                    if(!validGuesses.get(i).contains("" + w.getAlphabet().get(j))) {
                        removedGuesses.add(validGuesses.get(i));
                    }
                }

                //3. REMOVE ALL WORDS THAT CONTAIN #ed LETTERS USING REVERSAL OF RETURNALPHVALUE TO FIND THEIR LETTER VALUE
                if(w.getAlphabet().get(j) == '#') {
                    String check = String.valueOf((char)(j+97));
                    if(validGuesses.get(i).contains(check)){
                        removedGuesses.add(validGuesses.get(i));
                    }
                }
            }
        }

        validGuesses.removeAll(removedGuesses);

        System.out.println("Possible Guesses:");
        if (validGuesses.size() < 101){
            System.out.println(validGuesses);
        }
        else{
            System.out.println("[");
            for(int i = 0; i < 100; i++){
                System.out.print(validGuesses.get(i) + ", ");
            }
            System.out.print("]");
        }
        String codeGuess = validGuesses.get((int)(Math.random() * validGuesses.size()));
        usedWords.add(codeGuess);
        return codeGuess;
    }
}