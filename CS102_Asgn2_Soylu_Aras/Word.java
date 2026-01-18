import java.util.ArrayList;

public class Word{

    public ArrayList<Word> possibleChains;
    public String wordString;

    public Word(String wordString)
    {
        this.wordString = wordString.toUpperCase();
        this.possibleChains = new ArrayList<>();
    }


    public boolean canChain(Word otherWord)
    {
        String word1 = this.wordString;
        String word2 = otherWord.wordString;

        int lengthDifference = Math.abs(word1.length()-word2.length());
    
        if(lengthDifference > 1)
        {
            return false;
        }

        int word1CharIndexes = 0;
        int word2CharIndexes = 0;
        int charDiffCount = 0;

        
        while(word1CharIndexes < word1.length() && word2CharIndexes <word2.length())
        {
            if(word1.charAt(word1CharIndexes) != word2.charAt(word2CharIndexes))
            {
                charDiffCount++;
                if(charDiffCount > 1)
                {
                    return false;
                }
                else if(lengthDifference == 0)
                {
                    word1CharIndexes++;
                    word2CharIndexes++;
                }   
                else if(word1.length() > word2.length())
                {
                    word1CharIndexes++;
                }
                else
                {
                    word2CharIndexes++;
                }
            }
            else
            {
                word1CharIndexes++;
                word2CharIndexes++;
            }
            
        }

        if(word1CharIndexes<word1.length() || word2CharIndexes <word2.length())
        {
            charDiffCount++;
        }
        
        return charDiffCount <= 1;
        
    }
    
    

    public void addToPossibleChains(Word chainWord)
    {
        possibleChains.add(chainWord);
    }


    //getters setters
    public String getWord()
    {
        return wordString;
    }

    public ArrayList<Word> getPossibleChains()
    {
        return possibleChains;
    }
}