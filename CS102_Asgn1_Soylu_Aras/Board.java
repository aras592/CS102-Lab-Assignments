public class Board {
    String[] board;
    Property[] properties;

    public Board()
    {
        board= new String[]{"0","A","B","C","1","D","E","F","2","G","H","I","3","J","K","L"};
        properties= new Property[12];
        properties[0]=(new Property("A", 2, 1, new int[]{1, 2, 3, 4, 6}));
        properties[1]=(new Property("B", 2, 1, new int[]{1, 2, 3, 4, 6}));
        properties[2]=(new Property("C", 2, 1, new int[]{1, 2, 3, 4, 6}));
        properties[3]=(new Property("D", 4, 1, new int[]{2, 2, 3, 3, 7}));
        properties[4]=(new Property("E", 4, 1, new int[]{2, 2, 3, 3, 7}));
        properties[5]=(new Property("F", 4, 1, new int[]{2, 2, 3, 3, 7}));
        properties[6]=(new Property("G", 6, 2, new int[]{1, 3, 4, 6, 7}));
        properties[7]=(new Property("H", 6, 2, new int[]{1, 3, 4, 6, 7}));
        properties[8]=(new Property("I", 6, 2, new int[]{1, 3, 4, 6, 7}));
        properties[9]=(new Property("J", 8, 3, new int[]{3, 3, 6, 6, 9}));
        properties[10]=(new Property("K", 8, 3, new int[]{3, 3, 6, 6, 9}));
        properties[11]=(new Property("L", 8, 3, new int[]{3, 3, 6, 6, 9}));
    }

    public void displayMap()
    {
        for(int i=0; i<5; i++)
        {
            if(properties[i].owner==null)
            {
               System.out.print("|" + board[i]+"....|"); 
            }
            else
            {
                System.out.print("|" + board[i]+".."+properties[i].owner.initial+properties[i].houses+"|");
            }
        }
        System.out.println();
        
        System.out.println("|" +board[15]+"....|" +"                     "+"|" + board[5]+ "....|");
        System.out.println("|" +board[14]+"....|" +"                     "+"|" + board[6]+ "....|");
        System.out.println("|" +board[13]+"....|" +"                     "+"|" + board[7]+ "....|");
        
        for(int i=12; i>=8; i--)
        {
            System.out.print("|" + board[i]+"....|");
        }
        System.out.println();
    }

    public Property getPropertyByName(String cell)
    {
        for(int i = 0; i < board.length; i++)
        {
            if(cell.contains(properties[i].name))
            {
                return properties[i];
            }
            
        }
        return(null);
    }
}
