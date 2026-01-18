
public class EquilateralTriangle extends GeometricShape2D {
    private float side;

    public EquilateralTriangle(float side) {
        this.side = side;
    }

    public float calculateArea() {
        return (float)(Math.sqrt(3) / 4 * side * side);
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Equilateral Triangle, area: "+calculateArea()+", side: " + side);
    }
}
