
public class Rectangle extends GeometricShape2D {
    protected float width, height;

    public Rectangle(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float calculateArea() {
        return width * height;
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Rectangle, area: "+calculateArea()+", width: " + width + ", height: " + height);
    }
}
