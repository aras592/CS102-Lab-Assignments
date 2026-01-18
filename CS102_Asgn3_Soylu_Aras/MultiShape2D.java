public class MultiShape2D extends GeometricShape2D {
    private GeometricShape2D[] shapes = new GeometricShape2D[0];

    public void addShape(GeometricShape2D shape) {

        for (GeometricShape2D existingShape : shapes) {
            if (existingShape == shape) { 
                System.out.println("This shape is already in the MultiShape.");
                return;
            }
        }

        GeometricShape2D[] newShapes = new GeometricShape2D[shapes.length + 1];
        System.arraycopy(shapes, 0, newShapes, 0, shapes.length);
        newShapes[shapes.length] = shape;
        shapes = newShapes;
    }

    public void mergeShapes() {
        float totalArea = calculateArea();
        if (totalArea > 0) {
            float edgeLength = (float) Math.round(Math.sqrt(totalArea));
            shapes = new GeometricShape2D[]{ new Square(edgeLength) };
        }
    }

    public float calculateArea() {
        float totalArea = 0;
        for (GeometricShape2D shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }

    public void printInfo(String indent) {
        System.out.println(indent + "Multi-shape, area " + calculateArea() + ":");
        for (GeometricShape2D shape : shapes) {
            shape.printInfo(indent + "  ");
        }
    }

    public GeometricShape2D[] getShapes() {
        return shapes;
    }
}
