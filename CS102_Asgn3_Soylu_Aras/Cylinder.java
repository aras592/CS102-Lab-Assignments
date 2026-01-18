
public class Cylinder extends GeometricShape3D {
    private float radius, height;

    public Cylinder(float radius, float height) {
        this.radius = radius;
        this.height = height;
    }

    public float calculateArea() {
        return (float)(2 * Math.PI * radius * (radius + height));
    }

    public float calculateVolume() {
        return (float)(Math.PI * radius * radius * height);
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Cylinder, area: "+calculateArea()+", volume: "+calculateVolume()+", radius: " + radius + ", height: " + height);
    }
}
