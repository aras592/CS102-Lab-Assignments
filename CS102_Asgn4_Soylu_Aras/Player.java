import java.awt.Color;
import java.util.ArrayList;

public class Player {
    public String name;
    public char initial;
    public Color tokenColor;
    public int coins;
    public int position;
    public boolean isEliminated;
    public boolean skipNextTurn;
    public ArrayList<Property> ownedProperties;
    
    public boolean hasSoldThisTurn;
    public boolean hasBuiltThisTurn;
    public boolean hasBoughtThisTurn;

    public Player(String name, char initial, Color tokenColor) 
    {
        this.name = name;
        this.initial = initial;
        this.tokenColor = tokenColor;
        this.coins = 10;
        this.position = 0;
        this.isEliminated = false;
        this.skipNextTurn = false;
        this.ownedProperties = new ArrayList<>();
        this.hasSoldThisTurn = false;
        this.hasBuiltThisTurn = false;
        this.hasBoughtThisTurn = false;
    }
    
    public void move(int steps, int boardSize) 
    {
        position = (position + steps) % boardSize;
    }
    
    public void buyProperty(Property property) 
    {
        if (coins >= property.price && property.owner == null) 
        {
            coins -= property.price;
            property.owner = this;
            ownedProperties.add(property);
            System.out.println(name + " bought " + property.name + " for " + property.price + " coins!");
            hasBoughtThisTurn = true;
            checkElimination();
        }
    }
    
    public void payRent(Property property) 
    {
        if (property.owner != null && property.owner != this) 
        {
            int rent = property.getRent();
            if (coins >= rent) 
            {
                coins -= rent;
                property.owner.coins += rent;
                System.out.println(name + " paid " + rent + " coins to " + property.owner.name);
            } 
            else 
            {
                coins -= rent;
                System.out.println(name + " could not pay rent and is eliminated.");
            }
            checkElimination();
        }
    }
    
    public boolean canBuildHouse(Property property) 
    {
        return (property.houses < 4 && coins >= property.houseCost);
    }
    
    public void buildHouse(Property property) 
    {
        if (canBuildHouse(property)) 
        {
            coins -= property.houseCost;
            property.houses++;
            System.out.println("House built on " + property.name + ". It now has " + property.houses + " houses.");
            hasBuiltThisTurn = true;
            checkElimination();
        }
    }
    
    public void sellProperty(Property property) 
    {
        if (ownedProperties.contains(property)) 
        {
            coins += property.price;
            property.houses = 0;
            property.owner = null;
            ownedProperties.remove(property);
            System.out.println(name + " sold " + property.name + " back to the bank!");
            hasSoldThisTurn = true;
        }
    }
    
    public void checkElimination() 
    {
        if (coins < 0 && !isEliminated) 
        {
            for (Property prop : ownedProperties) 
            {
                prop.owner = null;
                prop.houses = 0;
            }
            ownedProperties.clear();
            isEliminated = true;
            System.out.println(name + " is eliminated.");
        }
    }
    
    public void resetTurnActions() 
    {
        hasSoldThisTurn = false;
        hasBuiltThisTurn = false;
        hasBoughtThisTurn = false;
    }
}
