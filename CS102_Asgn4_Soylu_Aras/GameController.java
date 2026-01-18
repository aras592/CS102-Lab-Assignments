
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GameController 
{
    private Player[] players;
    private Board board;
    private Dice dice;
    private int currentPlayerIndex;
    private StringBuilder log;
    private boolean hasRolled; 
    private int turnNumber;

    public GameController(Player[] players) 
    {
        this.players = players;
        board = new Board();
        dice = new Dice();
        currentPlayerIndex = 0; 
        log = new StringBuilder();
        hasRolled = false;
        turnNumber = 1;
        players[currentPlayerIndex].resetTurnActions();
        appendLog("Game started." );
        appendLog("Playing order determined:");
        
        for(int i = 0; i < 4; i++)
        {
            appendLog("- "+players[i].name);
        }

        appendLog("Turn " + turnNumber + ". " + players[currentPlayerIndex].name + "'s turn.");
    }
    
    public Board getBoard() 
    {
        return board;
    }
    
    public Player[] getPlayers() 
    {
        return players;
    }
    
    public String getLog() 
    {
        return log.toString();
    }
    
    private void processSpecialEvent(Player current) 
    {
        String label = board.board[current.position];
        String specialEventInfo = "";
        
        if (label.equals("1")) 
        {
            specialEventInfo += current.name + " landed on special event cell 1.\n";
            int eventRoll = dice.roll();
            specialEventInfo += "Special event roll: " + eventRoll + "\n";
            switch (eventRoll) 
            {
                case 1:
                    current.coins -= 2;
                    specialEventInfo += current.name + " loses 2 coins.\n";
                    break;
                case 2:
                    current.coins -= 1;
                    specialEventInfo += current.name + " loses 1 coin.\n";
                    break;
                case 3:
                    current.move(1, board.board.length);
                    specialEventInfo += current.name + " moves 1 space forward due to special event.\n";
                    break;
                case 4:
                    current.move(2, board.board.length);
                    specialEventInfo += current.name + " moves 2 spaces forward due to special event.\n";
                    break;
                case 5:
                    current.coins += 1;
                    current.move(1, board.board.length);
                    specialEventInfo += current.name + " receives 1 coin and moves 1 space forward due to special event.\n";
                    break;
                case 6:
                    current.coins += 2;
                    current.move(2, board.board.length);
                    specialEventInfo += current.name + " receives 2 coins and moves 2 spaces forward due to special event.\n";
                    break;
            }
            specialEventInfo += current.name + " is now at cell " + board.board[current.position]
                    + " (Index: " + current.position + ").\n";
        } 
        else if (label.equals("2")) 
        {
            specialEventInfo += current.name + " landed on special event cell 2.\n";
            int coinsReceived = 0;
            for (Player other : players) 
            {
                if (other != current && !other.isEliminated) 
                {
                    other.coins -= 1;
                    coinsReceived++;
                    specialEventInfo += other.name + " loses 1 coin to " + current.name + ".\n";
                }
            }
            current.coins += coinsReceived;
            specialEventInfo += current.name + " receives a total of " + coinsReceived + " coins from other players.\n";
        } 
        else if (label.equals("3")) 
        {
            specialEventInfo += current.name + " landed on special event cell 3 and will skip their next turn.\n";
            current.skipNextTurn = true;
        }
        
        if (!specialEventInfo.isEmpty()) 
        {
            appendLog(specialEventInfo);
            JOptionPane.showMessageDialog(null, specialEventInfo, "Special Event (Cell " + label + ")", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void rollDice() 
    {
        Player current = players[currentPlayerIndex];
        if (current.skipNextTurn) {
            appendLog(current.name + " is skipping their turn.");
            current.skipNextTurn = false;
            finishTurn();
            return;
        }
        if (!canRoll()) {
            appendLog("You have already rolled this turn.");
            return;
        }

        if (!canRoll()) 
        {
            appendLog("You have already rolled this turn.");
            return;
        }
        int oldPos = current.position;
        int roll = dice.roll();
        current.move(roll, board.board.length);

        if (oldPos + roll >= board.board.length) 
        {
            String msg = "";
            if (current.position == 0) 
            {
                current.coins += 6;
                msg = current.name + " completed a tour and landed exactly on cell 0, receiving 6 coins.";
            } 
            else 
            {
                current.coins += 3;
                msg = current.name + " completed a tour and passed cell 0, receiving 3 coins.";
            }
            appendLog(msg);
            JOptionPane.showMessageDialog(null, msg, "Special Event (Cell 0)", JOptionPane.INFORMATION_MESSAGE);
        }
        
        appendLog(current.name + " rolled a " + roll + " and moved to cell " 
                + board.board[current.position] + " (Index: " + current.position + ").");
        
        if (board.board[current.position].equals("1") ||
            board.board[current.position].equals("2") ||
            board.board[current.position].equals("3")) {
            processSpecialEvent(current);
        }
        
        String label = board.board[current.position]; 
        if (!label.equals("0") && !label.equals("1") && !label.equals("2") && !label.equals("3")) 
        {
            Property prop = board.getPropertyAtCell(current.position);
            if (prop != null) 
            {
                if (prop.owner == null) 
                {
                    appendLog("Property " + prop.name + " is available for purchase at " + prop.price + " coins.");
                } 
                else if (prop.owner != current) 
                {
                    current.payRent(prop);
                    appendLog(current.name + " paid rent to " + prop.owner.name + ".");
                } 
                else 
                {
                    appendLog("You landed on your own property " + prop.name + ".");
                }
            }
        }
        
        current.checkElimination();
        hasRolled = true;
    }



    private void simulateComputerTurn() 
    {
        Player current = players[currentPlayerIndex];
        if (current.skipNextTurn) {
            appendLog(current.name + " skips their turn.");
            current.skipNextTurn = false;
            return;
        }
    
        appendLog(current.name + "'s turn (Computer).");

        int oldPos = current.position;
        int roll = dice.roll();
        current.move(roll, board.board.length);

        if (oldPos + roll >= board.board.length) 
        {
            String msg = "";
            if (current.position == 0) 
            {
                current.coins += 6;
                msg = current.name + " completed a tour and landed exactly on cell 0, receiving 6 coins.";
            } 
            else 
            {
                current.coins += 3;
                msg = current.name + " completed a tour and passed cell 0, receiving 3 coins.";
            }
            appendLog(msg);
            JOptionPane.showMessageDialog(null, msg, "Special Event (Cell 0)", JOptionPane.INFORMATION_MESSAGE);
        }
        
        appendLog(current.name + " rolled a " + roll + " and moved to cell " 
                + board.board[current.position] + " (Index: " + current.position + ").");
        
        if (board.board[current.position].equals("1") ||
            board.board[current.position].equals("2") ||
            board.board[current.position].equals("3")) {
            processSpecialEvent(current);
        }
        
        String label = board.board[current.position];  
        if (!label.equals("0") && !label.equals("1") && !label.equals("2") && !label.equals("3")) 
        {
            Property prop = board.getPropertyAtCell(current.position);
            if (prop != null) 
            {
                if (prop.owner == null && current.coins >= prop.price) 
                {
                    current.buyProperty(prop);
                    appendLog(current.name + " bought property " + prop.name + ".");
                } 
                else if (prop.owner != null && prop.owner != current) 
                {
                    current.payRent(prop);
                    appendLog(current.name + " paid"+prop.getRent()+"rent to " + prop.owner.name + ".");
                }
            }
        }
        
        current.checkElimination();
        
        if (current.coins >= 5 && !current.hasBuiltThisTurn && !current.ownedProperties.isEmpty())
        {
            if (Math.random() < 0.5) 
            {
                ArrayList<Property> eligible = new ArrayList<>();
                for (Property ownedProp : current.ownedProperties) 
                {
                    if (current.canBuildHouse(ownedProp)) 
                    {
                        eligible.add(ownedProp);
                    }
                }
                if (!eligible.isEmpty()) 
                {
                    int idx = (int)(Math.random() * eligible.size());
                    Property chosen = eligible.get(idx);
                    current.buildHouse(chosen);
                    appendLog(current.name + " built a house on property " + chosen.name + ".");
                }
            }
        }
    }

    public void appendLog(String message) 
    {
        log.append(message + "\n");
        System.out.println(message);
    }
    
   
    public boolean canRoll() 
    {
        return !hasRolled && isHumanTurn();
    }
    
    public boolean canBuy() 
    {
        if (!isHumanTurn()) 
        {
            return false;
        }

        Player current = players[currentPlayerIndex];
        Property prop = board.getPropertyAtCell(current.position);

        return (prop != null && prop.owner == null && current.coins >= prop.price &&
                !current.hasSoldThisTurn && !current.hasBoughtThisTurn);
    }
    
    public boolean canSell() 
    {
        if (!isHumanTurn()) 
        {
            return false;
        }

        Player current = players[currentPlayerIndex];

        return (!current.hasSoldThisTurn && !current.hasBoughtThisTurn && current.ownedProperties.size() > 0);
    }
    
    public boolean canBuild() 
    {
        if (!isHumanTurn()) 
        {
            return false;
        }

        Player current = players[currentPlayerIndex];
        for (Property prop : current.ownedProperties) 
        {
            if (current.canBuildHouse(prop)) {
                return !current.hasBuiltThisTurn;
            }
        }
        return false;
    }
    
    public boolean canEndTurn() 
    {
        return hasRolled && isHumanTurn();
    }
    
    public Player getCurrentPlayer() 
    {
        return players[currentPlayerIndex];
    }
    
    
    public void buyProperty() 
    {
        Player current = players[currentPlayerIndex];
        Property prop = board.getPropertyAtCell(current.position);
        if (prop != null && prop.owner == null && current.coins >= prop.price &&
            !current.hasSoldThisTurn && !current.hasBoughtThisTurn) 
        {
            current.buyProperty(prop);
            if(isHumanTurn())
            {
                JOptionPane.showMessageDialog(null, current.name+" bought property "+prop.name+" for "+prop.price+" coins.", "Property Buying", JOptionPane.INFORMATION_MESSAGE);
            }
            appendLog(current.name + " bought property " + prop.name + " for "+prop.price+" coins.");
        } 
        else 
        {
            appendLog("Cannot buy property.");
        }
    }
    
    public void sellProperty() 
    {
        Player current = players[currentPlayerIndex];
        if (current.ownedProperties.size() > 0 && !current.hasSoldThisTurn && !current.hasBoughtThisTurn) 
        {
            if (isHumanTurn()) 
            {
                new SellDialog(this);
            } 
            else 
            {
            
                Property prop = current.ownedProperties.get(0);
                current.sellProperty(prop);
                appendLog(current.name + " sold property " + prop.name + ".");
            }
        } 
        else 
        {
            appendLog("No property to sell or action already taken this turn.");
        }
    }

    public void promptBuildHouse() 
    {
        Player current = players[currentPlayerIndex];
        if (isHumanTurn()) 
        {
            new BuildDialog(this);
        } 
        else 
        {
            boolean built = false;
            for (Property prop : current.ownedProperties) 
            {
                if (current.canBuildHouse(prop)) 
                {
                    current.buildHouse(prop);
                    appendLog(current.name + " built a house on property " + prop.name + ".");
                    built = true;
                    break;
                }
            }
            if (!built) 
            {
                appendLog("No property available to build a house for " + current.name + ".");
            }
        }
    }
    
    public boolean isHumanTurn() 
    {
        if (players[0].isEliminated) 
        {
            return true;
        }
        return currentPlayerIndex == 0;
    }
    
    public void finishTurn() 
    {  
        Player current = players[currentPlayerIndex];
        if (current.skipNextTurn) {
            appendLog(current.name + " is skipping their turn.");
            current.skipNextTurn = false;
            hasRolled = true; 
        }
        if (!canEndTurn()) {
            appendLog("You must roll before ending your turn.");
            return;
        }

        appendLog(current.name + " ends their turn.");
        hasRolled = false;
        nextPlayer();
        
        if (players[0].isEliminated) 
        {
            while (!gameOver()) 
            {
                if (!players[currentPlayerIndex].isEliminated) 
                {
                    simulateComputerTurn();
                }
                nextPlayer();
            }
            if (gameOver()) 
            {
                endGame();
                return;
            }
        } 
        else 
        {
            while (!isHumanTurn() && !gameOver()) 
            {
                simulateComputerTurn();
                nextPlayer();
            }
            if (isHumanTurn()) 
            {
                turnNumber++;
                players[currentPlayerIndex].resetTurnActions();
            }
        }
        
        if (gameOver()) 
        {
            endGame();
        } 
        else 
        {
            appendLog("Turn " + turnNumber + ": It is now " + players[currentPlayerIndex].name + "'s turn.");
        }
    }
    

    
    private void nextPlayer() 
    {
        int activeCount = 0;
        for (Player p : players) 
        {
            if (!p.isEliminated) 
            {
                activeCount++;
            }
        }
        if (activeCount <= 1) 
        {
            endGame();
            return;
        }
        
        do 
        {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        } while (players[currentPlayerIndex].isEliminated);
    }
    

    private void endGame() 
    {
        String winner = "";
        for (Player p : players) 
        {
            if (!p.isEliminated) 
            {
                winner += p.name + " ";
            }
        }
        String message = "Game Over on turn " + turnNumber + "! Winner(s): " + winner;
        appendLog(message);
        JOptionPane.showMessageDialog(null, message);
    }
    
    private boolean gameOver() 
    {
        int activePlayers = 0;
        for (Player p : players) 
        {
            if (!p.isEliminated) activePlayers++;
        }
        return activePlayers <= 1;
    }
}
