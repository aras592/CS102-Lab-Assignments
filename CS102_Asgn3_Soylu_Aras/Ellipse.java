
public class Ellipse extends GeometricShape2D {
    private float majorAxis, minorAxis;

    public Ellipse(float major, float minor) {
        this.majorAxis = major;
        this.minorAxis = minor;
    }

    public float calculateArea() {
        return (float)(Math.PI * majorAxis * minorAxis);
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Ellipse, area: "+calculateArea()+ ", major axis: " + majorAxis + ", minor axis: " + minorAxis);
    }
}
