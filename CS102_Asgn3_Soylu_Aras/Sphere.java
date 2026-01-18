
public class Sphere extends GeometricShape3D {
    private float radius;

    public Sphere(float radius) {
        this.radius = radius;
    }

    public float calculateArea() {
        return (float)(4 * Math.PI * radius * radius);
    }

    public float calculateVolume() {
        return (float)(4.0/3 * Math.PI * radius * radius * radius);
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Sphere, area: "+calculateArea()+", volume: "+calculateVolume()+", radius: " + radius);
    }
}
