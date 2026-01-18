
public class Square extends Rectangle {
    public Square(float side) {
        super(side, side);
    }
    
    public void printInfo(String indent) {
        System.out.println(indent + "Square, area: "+calculateArea()+", edge length: " + width);
    }
}
