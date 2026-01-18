
public class Cube extends Cuboid {
    public Cube(float side) {
        super(side, side, side);
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Cube, edge length: " + super.calculateVolume() / (super.calculateArea()));
    }
}
