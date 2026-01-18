import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class Manager {

    
    public static void playGuessingGame(Scanner scan, FileReader fileReader)
    {
        ArrayList<Word> words = fileReader.getFilteredWords();
        
        Random rand = new Random();
        int a = rand.nextInt(words.size());

        Word firstWord = words.get(a);
        fileReader.generateChainForAWord(firstWord);

        int b = rand.nextInt(firstWord.possibleChains.size());
        Word secondWord = firstWord.possibleChains.get(b);
        if(secondWord == null){return;}

        fileReader.generateChainForAWord(secondWord);
        secondWord.possibleChains.remove(secondWord);
        secondWord.possibleChains.remove(firstWord);

        int c = rand.nextInt(secondWord.possibleChains.size());

        Word thirdWord = secondWord.possibleChains.get(c);
        System.out.println(secondWord.getWord());

        System.out.println("Guess the missing word: ");
        System.out.println();
        System.out.println(firstWord.getWord()+" --> "+ "   ???    --> "+thirdWord.getWord());

        int tries = 3;
        while(tries > 0)
        {
            Scanner sc1 = new Scanner(System.in);
            System.out.print("Your guess: ");
            String userAnswer = sc1.nextLine().toUpperCase();
            Word guessedWord = new Word(userAnswer);

            if(secondWord.getWord().equals(guessedWord.getWord()))
            {
                System.out.println("Your guess is correct! Well done");
                System.out.println();
                break;
            }
            else
            {
                tries--;
                System.out.println("Your answer is wrong, you still have "+tries+" attempts left. Try again!");

                System.out.println();
            }  
        }
        if(tries == 0)
        {
        System.out.println("The correct word was "+ secondWord.getWord());
        }
        System.out.println();
    }
    
    public static void chainGenerator( FileReader fileReader)
    {
        ArrayList<Word> words = fileReader.getFilteredWords();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the word you would like to create a chain for:");
        String userInput = scan.nextLine().toUpperCase();
        Word userWord = new Word(userInput);

        boolean check = true;
        while(check)
        {
            if(!FileReader.searchWord(userInput,words))
            {
                System.out.println("Your word in not in our dictionary. Please try again!");
                userInput = scan.nextLine().toUpperCase();
                userWord = new Word (userInput);
            }
            else
            {
                check=false;
            }
        }

        System.out.println("Here is the chain of the word you asked for:");
        System.out.println();

        System.out.print(userWord.getWord());

        fileReader.generateChainForAWord(userWord);
        Random r = new Random(11);
        int a = r.nextInt(userWord.possibleChains.size());
        ArrayList<Word> chainUser = new ArrayList<>();
        
        Word temp = userWord;
        chainUser.add(temp);

        temp = userWord.possibleChains.get(a);
        Word anotherWord;                                                                                                                                                                  

        int count = 0;
        while(count<9)
        {
            
             if(chainUser.contains(temp))
            {
                
                anotherWord = temp;
                String aS = anotherWord.getWord();
                fileReader.generateChainForAWord(anotherWord);
                int b = r.nextInt(anotherWord.possibleChains.size());
                temp = anotherWord.possibleChains.get(b);
                if(temp.getWord().equals(userWord.getWord()))
                {
                    System.out.println();
                }
            }
            
            else
            {
                count++;
                chainUser.add(temp);
                System.out.print(" --> "+temp.getWord());
            }
        }
       
        System.out.println();
    }

    public static void main(String[] args) {


        FileReader fileReader = new FileReader();
        fileReader.openFile("filtered_words.txt",fileReader.filteredWords);
        
        Word table = new Word("HAMES");
        fileReader.generateChainForAWord(table);

        for(int i =0; i<table.possibleChains.size(); i++)
        {
            System.out.println(table.possibleChains.get(i).getWord());
        }
        
        System.out.println("Hello! Welcome to Chain of Words!");
        System.out.println();

        boolean check=true;

        

        while(check)
        {
            Scanner scan = new Scanner(System.in);

            System.out.println("\nWhat would you like to do: \n 1)Play the Guessing Game \n 2)Generate word chains \n 3)Exit the program ");
            System.out.println();

            
            int answer = scan.nextInt();

            switch (answer) {
                case 1:
                    playGuessingGame(scan, fileReader);
                    break;
                case 2:
                    chainGenerator(fileReader);
                    
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    check=false;
                    break;
                default:
                    System.out.println();
                    System.out.println("Could not understand, try again!");
                    System.out.println();
                    break;
            }
        }


    }
}
