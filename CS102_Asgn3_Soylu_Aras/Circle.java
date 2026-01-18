
public class Circle extends GeometricShape2D {
    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    public float calculateArea() {
        return (float)(Math.PI * radius * radius);
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Circle, area: "+calculateArea() +", radius: " + radius);
    }
}
