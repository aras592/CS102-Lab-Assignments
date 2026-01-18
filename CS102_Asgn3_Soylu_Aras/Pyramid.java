
public class Pyramid extends GeometricShape3D {
    private float baseArea, height;

    public Pyramid(float baseArea, float height) {
        this.baseArea = baseArea;
        this.height = height;
    }

    public float calculateArea() {
        return baseArea + (float)Math.sqrt(baseArea) * height;
    }

    public float calculateVolume() {
        return (baseArea * height) / 3;
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Pyramid, area: "+calculateArea()+", volume: "+calculateVolume()+", base area: " + baseArea + ", height: " + height);
    }
}
