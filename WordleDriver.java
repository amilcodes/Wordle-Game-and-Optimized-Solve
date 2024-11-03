public class WordleDriver {
    public static void main(String[] args) {
        System.out.println("Guess a 5-letter word!");
        Wordle w = new Wordle();
        w.initialize();
        w.guess();
    }
}