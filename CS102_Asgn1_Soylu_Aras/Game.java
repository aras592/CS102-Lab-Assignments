import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Game {
    public static void main(String[] args)   
    {
        Scanner sc = new Scanner(System.in);
        Random r2 = new Random();

        System.out.print("Enter your name: ");
        String playerName= sc.nextLine();

        Player user = new Player(playerName, playerName.charAt(0));
        System.out.print("Enter number of computer players: ");
        int numPlayers = sc.nextInt();

        ArrayList<Player> players = new ArrayList<>();
        //players.add(user);

        
        for(int i=0; i<numPlayers; i++){
            Scanner sc1 = new Scanner(System.in);
            System.out.print("Enter the name of the computer "+(i+1)+": ");
            String comName = sc1.nextLine();

            char initial = comName.charAt(0);
            players.add(new Player(comName ,initial));
        }

        Board board = new Board();
        Dice dice = new Dice();
        int turn=0;
        while(turn<100)
        {
            for(Player player: players)
            {
                if(player.isEliminated||player.skipNextTurn)
                {
                    player.skipNextTurn=false;
                    continue;
                }
                if(player.isEliminated)
                {
                    players.remove(player);
                }
                
                int roll=dice.roll();
                player.move(roll, board.board.length);
                System.out.println(player.name + " rolls " + roll + " and landed in " + board.board[player.position]);

                board.displayMap();

                String cell = board.board[player.position];
                if(cell.contains("A") ||cell.contains("B") ||cell.contains("C") 
                ||cell.contains("D") ||cell.contains("E") ||cell.contains("F")
                ||cell.contains("G") ||cell.contains("H") ||cell.contains("I") 
                ||cell.contains("J") ||cell.contains("K") ||cell.contains("L"))
                {
                    Property property = board.getPropertyByName(cell);
                    if(property.owner == null)
                    {
                        if(player == user)
                        {
                            Scanner sc2 = new Scanner (System.in);
                            System.out.println("Do you want to buy "+property.name+ "(yes/no)?" );
                            String answer = sc2.nextLine();

                            if(answer.equalsIgnoreCase("yes"))
                            {
                                player.buyProperty(property);
                            }
                            
                        }
                        else if(player.coins >= property.price)
                        {
                            player.buyProperty(property);
                        }
                    }
                    else if(property.owner == user)
                    {
                        Scanner sc3 = new Scanner (System.in);
                        System.out.println("Do you want to (1) build a house, (2) sell a property, or (3) skip: ");
                        int choice = sc3.nextInt();
                        if(choice == 1)
                        {
                            player.buildHouse();
                        }
                        else if(choice == 2)
                        {
                            player.sellProperty();
                        }
                    }
                    else
                    {
                        player.payRent(property);
                    }
                }
                else
                {
                    if(player.position==0)
                    {
                        player.coins+=6;
                    }
                    else if(board.board[player.position].equals("1"))
                    {
                        int eventRoll = dice.roll();
                        if(eventRoll==1)
                        {
                            System.out.println(player.name+" rolled "+eventRoll+" and lost two coins!");
                            player.coins-=2;
                        }
                        else if(eventRoll==2)
                        {
                            System.out.println(player.name+" rolled "+eventRoll+" and lost one coin!");
                            player.coins-=1;
                        }
                        else if(eventRoll==3)
                        {
                            System.out.println(player.name+" rolled "+eventRoll+" and moved one space!");
                            player.move(1,board.board.length);
                        }
                        else if(eventRoll==4)
                        {
                            System.out.println(player.name+" rolled "+eventRoll+" andmoved two spaces!");
                            player.move(2,board.board.length);
                        }
                        else if(eventRoll==5)
                        {
                            System.out.println(player.name+" rolled "+eventRoll+" and gained one coin and moved one space!");
                            player.coins+=1;
                            player.move(1,board.board.length);
                        }
                        else
                        {
                            System.out.println(player.name+" rolled "+eventRoll+" and gained two coins and moved two spaces!");
                            player.coins+=2;
                            player.move(2,board.board.length);
                        }
                    }

                
                }
                System.out.println(player.name + " has " + player.coins + " coins left");
                
                if(player.coins<0)
                {
                    player.isEliminated=true;
                }
            }

            turn++;
        }
        System.out.println("Game over!");
    }
}
