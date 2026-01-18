import java.util.ArrayList;
import java.util.*;

public class Player {
    public String name;
    public char initial;
    public int coins;
    public int position;
    public boolean isEliminated;
    public boolean skipNextTurn;
    public ArrayList<Property> ownedProperties;

    public Player(String name, char initial)
    {
        this.name=name;
        this.initial=initial;
        this.coins=10;
        this.position=0;
        this.isEliminated=false;
        this.skipNextTurn=false;
        this.ownedProperties=new ArrayList<>();
    }

    public void move(int steps, int boardSize)
    {
        position=(position+steps)%boardSize;
    }

    public void buyProperty(Property property)
    {
        if(coins >= property.price)
        {
            coins-= property.price;
            property.owner = this;
            ownedProperties.add(property);
            System.out.println(name+ " bought "+property.name+" for " +property.price+ " coins!");
        }
        else
        {
            System.out.println(name+" does not have enough coins to buy " + property.name);
        }
    }

    public void payRent(Property property)
    {
        if(property.owner != null && property.owner != this)
        {
            int rent = property.getRent();
            if(coins >= rent)
            {
                coins -= rent;
                property.owner.coins += rent;
                System.out.println(name+" paid "+rent+" coins to "+property.owner.name);
            }
            else
            {
                isEliminated = true;
                System.out.println(name+ " is eliminated due to not enough coins to pay the rent.");
            }
        }
    }

    public void buildHouse()
    {
        Scanner sc = new Scanner(System.in);
        if(ownedProperties.isEmpty())
        {
            System.out.println(name +" has no properties to build on!");
            return;
        }
        System.out.println(name + " choose a property to build house on: " );
        for(int i = 0; i < ownedProperties.size(); i++)
        {
            Property property = ownedProperties.get(i);
            if(property.houses < 4 && coins >= property.houseCost)
            {
                System.out.println(i + ": " + property.name + " (Houses: "+ property.houses + ", Cost: "+ property.houseCost+ " )");
            }
        }
        System.out.println("Enter the number of property to build a house, or -1 to skip");
        int choice = sc.nextInt();

        if(choice >= 0 && choice < ownedProperties.size())
        {
            Property property = ownedProperties.get(choice);
            if(coins >= property.houses)
            {
                coins -= property.houseCost;
                property.houses++;
                System.out.println("House built on "+property.name+ "! It now has " +property.houses+ " houses.");
            }
            else
            {
                System.out.println("Not enough coins to build a house on "+property.name);
            }
        }
    }

    public void sellProperty()
    {
        if(ownedProperties.isEmpty())
        {
            System.out.println(name+" has no properties to sell!");
            return;
        }

        Property propertyToSell = ownedProperties.get(0);
        coins += propertyToSell.price;
        propertyToSell.houses = 0;
        propertyToSell.owner = null;
        ownedProperties.remove(propertyToSell);
        
        System.out.println(name + " sold " +propertyToSell.name+ " back to the bank!");
    }
}   

