
public class Cuboid extends GeometricShape3D {
    private float width, height, depth;

    public Cuboid(float w, float h, float d) {
        this.width = w;
        this.height = h;
        this.depth = d;
    }

    public float calculateArea() {
        return 2 * (width * height + height * depth + width * depth);
    }

    public float calculateVolume() {
        return width * height * depth;
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Cuboid, area: "+calculateArea()+", volume: "+calculateVolume()+", width: " + width + ", height: " + height + ", depth: " + depth);
    }
}
