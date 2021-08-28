package bullscows;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public
class Game {
    private static int counter = 1;
    private Scanner scanner;
    private Random random;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void start(){
        System.out.println("Input the length of the secret code: ");
        boolean test = scanner.hasNextInt();
        int inputLength = 0;
        if(test) {
            inputLength = scanner.nextInt();
        }else{
            String input = scanner.nextLine();
            System.out.println("Error: \""+input+"\" isn't a valid number.");
            System.exit(0);
        }

        if(inputLength <= 0) {
            System.out.println("Error: Please type a number from 0-9");
            System.exit(0);
        }
        if(inputLength > 36) {
            System.out.println("Error: can't generate a secret number with a length of 36 because there aren't enough unique digits.");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");
        int inputSymbols =scanner.nextInt();
        if(inputSymbols < inputLength) {
            System.out.println("Error: it's not possible to generate a code with a length of "+ inputLength +" with "+inputSymbols+" unique symbols");
            System.exit(0);
        }
        if(inputSymbols > 36) {
            System.out.println("Error: can't generate a secret number with a length of 36 because there aren't enough unique digits.");
            System.exit(0);
        }


        System.out.print("The secret is prepared: ");
        for (int i = 0; i <inputLength ; i++) {
            System.out.print("*");
        }
        if(inputSymbols <= 10){
            System.out.print(" ("+0+"-"+(inputSymbols - 1)+").");
        } else {
            System.out.print(" (" + 0 + "-" + 9 +", "+"a-"+(char)('a'+inputSymbols-11)+").");
        }
        System.out.println("Okay, let's start a game!");
        System.out.println("Turn " + counter + ":");
        StringBuilder randomCode = RandomCode(inputLength,inputSymbols);

        boolean res  = notRandomCode(randomCode);
        while (!res) {
            counter++;
            System.out.println("Turn " + counter + ":");
            res  = notRandomCode(randomCode);
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }


    public StringBuilder RandomCode(int secretNumberLength,int possibilities ){
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        boolean flagChecker;

        while (!flag) {
            flagChecker = true;
            result = new StringBuilder();
            int [] checkerNumbers = new int[10];
            char [] checkerLetters = new char[26];
            if(possibilities > 10) {
                for (int i = 0; i < secretNumberLength; i++) {
                    if (Math.random() > 0.5 ) {
                        int c = (int)(Math.random()*10);
                        result.append(c);
                        if (checkerNumbers[c] == 0)
                            checkerNumbers[c]++;
                        else {
                            flagChecker = false;
                            break;
                        }
                    } else {
                        int c = random.nextInt(possibilities - 11 ) + 97;
                        result.append((char)c);
                            if (checkerLetters[Character.getNumericValue(c) - 10] == 0) {
                                checkerLetters[Character.getNumericValue(c) - 10]++;
                            } else {
                                flagChecker = false;
                                break;
                            }
                    }
                }
            }else {
                for (int i = 0; i < secretNumberLength; i++) {
                        int c = (int)(Math.random()*10);
                        result.append(c);
                        if (checkerNumbers[c] == 0)
                            checkerNumbers[c]++;
                        else {
                            flagChecker = false;
                            break;
                    }
                }
            }
            if(secretNumberLength == result.length() && flagChecker)
                flag = true;
        }
    return result;
}

    public boolean notRandomCode(StringBuilder output){
        boolean flag = false;
        String [] numberTab = new String[output.length()];
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        for (int i = 0; i < output.length(); i++) {
            numberTab[i] = (number.substring(i,i+1));
        }
        String [] secretNumber = new String[output.length()];
        for (int i = 0; i < output.length(); i++) {
            secretNumber[i] = output.substring(i,i+1);
        }
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < output.length() ; i++) {
            for (int j = 0; j < output.length() ; j++) {
                if (Objects.equals(secretNumber[i], numberTab[j])){
                    if (i==j){
                        bulls++;
                    }else{
                        cows++;
                    }
                }
            }
        }
        StringBuilder message = new StringBuilder("Grade: ");
        if (cows == 0 && bulls ==0) {
            message.insert(7,"None");
        }else if (cows != 0 && bulls == 0){
            message.insert(7,cows+ " cow(s)");
        }else if(cows == 0) {
            message.insert(7, bulls + " bull(s)");
            flag = true;
        }else {
            message.insert(7, bulls + " bull(s) and "+cows+" cow(s)");
        }
        System.out.println(message);
        return flag;
    }
}
