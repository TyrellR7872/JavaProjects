import acm.io.IOConsole;

import java.util.*;

public class HangMan extends IOConsole {
    private HangmanCanvas canvas;

    public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
    }



    private Map<Character, ArrayList<Integer>> initializeWord(String word){
        Map<Character, ArrayList<Integer>> charLocs = new HashMap<>();
        for(int i= 0; i < word.length();i++){
            Character ch = word.charAt(i);
            if (!charLocs.containsKey(ch)) {
                charLocs.put(ch, new ArrayList<>(Arrays.asList(i)));
            } else {
                charLocs.get(ch).add(i);
            }
        }
        return charLocs;
    }

    public void run(){
        canvas.reset();
        HangmanLexicon words = new HangmanLexicon();
        Random r = new Random();
        String word = words.getWord(r.nextInt(10));
        int guesses = 8;
        Scanner sc = new Scanner(System.in);
        Map<Character,ArrayList<Integer>> charLocs = initializeWord(word.toUpperCase());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length();i++){
            sb.append('_');
            sb.append(' ');
        }
        char[] solveWord = sb.toString().toCharArray();
        ArrayList<Character> guessedChars = new ArrayList<>();

        System.out.println("Welcome to Hangman!");
        System.out.println("Your word looks like this: "+new String(solveWord));
        System.out.println("You have "+guesses+" guess(es) left.");

        while (guesses > 0){
            System.out.print("What's the next word? ");
            String input = sc.next();
            if (input.length() > 1 || input.length() == 0){
                System.out.println("Please enter a single character.");
            } else{
                Character guess = input.toUpperCase().charAt(0);
                if (guessedChars.contains(guess)){
                    System.out.println("You already guessed that word");
                    continue;
                } else if (charLocs.containsKey(guess)){
                    System.out.println("That guess is correct!");
                    for (int idx: charLocs.get(guess)){
                        solveWord[idx*2] = guess;
                    }
                    charLocs.remove(guess);
                    guessedChars.add(guess);
                    if (charLocs.isEmpty()){
                        System.out.println("You guessed the word: "+word);
                        System.out.println("You win!");
                        return;
                    }
                } else{
                    System.out.println("There are no "+guess+"'s in the word");

                    if (--guesses == 0){
                        break;
                    }
                }
                System.out.println("Your word looks like this: "+new String(solveWord));
                System.out.println("You have "+guesses+" guess(es) left.");

            }
        }
        System.out.println("You're completely hung.");
        System.out.println("The word was: "+word);
        System.out.println("You lose.");
    }
}
