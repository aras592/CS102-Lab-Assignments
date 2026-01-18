import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    public ArrayList <Word> allWords;
    public ArrayList <Word> filteredWords;

    public FileReader()
    {
        this.allWords = new ArrayList<>();
        this.filteredWords = new ArrayList<>();
    }

    public void openFile(String fileName, ArrayList<Word> array)
    {
        try 
        {
            String filename = fileName;
            Scanner sc = new Scanner(new File(filename));

            while(sc.hasNextLine()) 
            {
                String line = sc.nextLine();
                Word eachWord = new Word(line);
                array.add(eachWord);
            }
            sc.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("File not found...");
        }
    }

    public void removeUnchainableWords()
    {
      
        for(Word w : allWords)
        {
            for(Word w2 : allWords)
            {
                if(w.canChain(w2) && !w.getWord().equals(w2.getWord())&& 
                !filteredWords.contains(w2))
                {
                    filteredWords.add(w2);
                    if(!filteredWords.contains(w))
                    {
                        filteredWords.add(w);
                    }
                }

            }
        }
    }
        
        public void createFilteredFile()
        {

        try 
        {
            String filename = " filtered_words.txt";
            FileWriter fw;
            fw = new FileWriter(filename);

            for(int i = 0; i < filteredWords.size(); i++)
            {
                
                Word w1 = allWords.get(i);
                String www1 = w1.getWord();
                for(int j = 1; j < allWords.size(); j++)
                {
                    Word w2 = allWords.get(j);
                    String www2 = w2.getWord();
                    if(w1.canChain(w2))
                    {
                        filteredWords.add(w2);
                
                        fw.write(filteredWords.get(i).getWord()+ "\n");
            }
            fw.close();
                }
            }   
        }
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    
        }
        


    public void generateChainForAWord(Word word)
    {
        for(int i = 0; i < filteredWords.size(); i++)
        {
            if(word.canChain(filteredWords.get(i))&&
            !word.getWord().equals(filteredWords.get(i).getWord()))
            {
                word.addToPossibleChains(filteredWords.get(i));
            }
        }
    }

    public static boolean searchWord(String userAnswer, ArrayList <Word> arrayl )
    {
        for(Word word : arrayl)
        {
            if(word.getWord().equals(userAnswer.toUpperCase()))
            {
                return true;
            }
        }
        return false;
    }

    //getters setters
    public ArrayList<Word> getFilteredWords()
    {
        return filteredWords;
    }

    

}
