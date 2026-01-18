public class Property {
    String name;
    int price;
    int houseCost;
    int houses;
    Player owner;
    int[] rent;

    public Property(String name, int price, int houseCost, int[] rent)
    {
        this.name= name;
        this.price=price;
        this.houseCost=houseCost;
        this.houses=0;
        this.owner=null;
        this.rent=rent;
    }

    public Player getOwner()
    {
        return this.owner;
    }
    
    public int getRent()
    {
        return(this.rent[houses]);
    }


    
}
