import java.util.Scanner;

public class ShapeApp {
    static GeometricShape2D[] shapes2D = new GeometricShape2D[0];
    static GeometricShape3D[] shapes3D = new GeometricShape3D[0];

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("--------| MENU |--------");
            System.out.println("1. Create New Shape");
            System.out.println("2. Add to Multi-shape");
            System.out.println("3. List Shapes");
            System.out.println("4. Merge Multi-shapes");
            System.out.println("5. Edit Shape");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    createShape();
                    break;
                case 2:
                    addToMultishape();
                    break;
                case 3: 
                    listShapes();
                    break;
                case 4:
                    mergeAllMultiShapes();
                    break;
                case 5:
                    editShape();
                    break;
            }
        } while (choice != 0);

        if(choice == 0)
        System.out.println("Exiting program...");
    }

    static void createShape() {
        System.out.println("-----| Shape Selection |-----");
        System.out.println("\nSelect Shape Type:\n1. Rectangle\n2. Circle\n3. Square\n4. Ellipse\n5. Equilateral Triangle\n6. Cuboid\n7. Sphere\n8. Cylinder\n9. Cube\n10. Pyramid\n11.Multi-shape");
        int type = input.nextInt();

        switch (type) {
            case 1: 
                add2DShape(new Rectangle(getFloat("Width: "), getFloat("Height: ")));
                break;
            case 2 :
                add2DShape(new Circle(getFloat("Radius: ")));
                break;
            case 3: 
                add2DShape(new Square(getFloat("Edge length: ")));
                break;
            case 4: 
                add2DShape(new Ellipse(getFloat("Major Axis: "), getFloat("Minor Axis: ")));
                break;
            case 5: 
                add2DShape(new EquilateralTriangle(getFloat("Side length: ")));
                break;
            case 6: 
                add3DShape(new Cuboid(getFloat("Width: "), getFloat("Height: "), getFloat("Depth: ")));
                break;
            case 7: 
                add3DShape(new Sphere(getFloat("Radius: ")));
                break;
            case 8: 
                add3DShape(new Cylinder(getFloat("Radius: "), getFloat("Height: ")));
                break;
            case 9:
                add3DShape(new Cube(getFloat("Edge length: ")));
                break;
            case 10:
                add3DShape(new Pyramid(getFloat("Base Area: "), getFloat("Height: ")));
                break;
            case 11:
                add2DShape(createMultiShape());
            default:
                System.out.println("Invalid shape type.");
                break;
        }
    }

    static MultiShape2D createMultiShape() {
        MultiShape2D m = new MultiShape2D();
        System.out.println("Add shapes to Multi-shape. Enter 0 to stop.");
        while (true) {
            System.out.println("\n1. Rectangle\n2. Circle\n3. Square\n4. Ellipse\n5. Equilateral Triangle\n0. Done");
            int type = input.nextInt();
            if (type == 0) break;
            GeometricShape2D shape = null;

            switch (type) {
                case 1:
                    shape = new Rectangle(getFloat("Width: "), getFloat("Height: "));
                    break;
                case 2:
                    shape = new Circle(getFloat("Radius: "));
                    break;
                case 3:
                    shape = new Square(getFloat("Edge length: "));
                    break;
                case 4:
                    shape = new Ellipse(getFloat("Major Axis: "), getFloat("Minor Axis: "));
                    break;
                case 5:
                    shape = new EquilateralTriangle(getFloat("Side length: "));
                    break;
                default:
                    System.out.println("Invalid shape type.");
                    break;
            }
            
            if (shape != null) {
                m.addShape(shape);
            }
            
            if (shape != null) m.addShape(shape);
        }
        return m;
    }

    public static void addToMultishape() {
        if (shapes2D.length < 1) {
            System.out.println("Not enough 2D shapes to perform this operation (need at least 2).");
            return;
        }
        
        System.out.println("Available Multi-shapes to add to:");
        int multiShapeIndex = -1;
    
        for (int i = 0; i < shapes2D.length; i++) {
            if (shapes2D[i] instanceof MultiShape2D) {
                System.out.println("[" + i + "] Multi-shape, area: " + shapes2D[i].calculateArea());
            }
        }
    
        System.out.print("Select Multi-shape index to add into: ");
        multiShapeIndex = input.nextInt();
    
        if (multiShapeIndex < 0 || multiShapeIndex >= shapes2D.length || !(shapes2D[multiShapeIndex] instanceof MultiShape2D)) {
            System.out.println("Invalid selection.");
            return;
        }
    
        System.out.println("Available 2D shapes to add (including other MultiShapes):");
        
        for (int i = 0; i < shapes2D.length; i++) {
            if (i != multiShapeIndex) { 
                System.out.println("[" + i + "] " + shapes2D[i].getClass().getSimpleName());
            }
        }
    
        System.out.print("Select shape to add: ");
        int shapeToAddIndex = input.nextInt();
    
        if (shapeToAddIndex < 0 || shapeToAddIndex >= shapes2D.length || shapeToAddIndex == multiShapeIndex) {
            System.out.println("Invalid selection.");
            return;
        }
    
        MultiShape2D targetMultiShape = (MultiShape2D) shapes2D[multiShapeIndex];
        targetMultiShape.addShape(shapes2D[shapeToAddIndex]);
    
        shapes2D = removeShape2D(shapeToAddIndex);
    
        System.out.println("Shape added successfully to the Multi-shape.");
    }
    
    
    
    
    static void listShapes() {
        System.out.println("\n-----| Shape List |-----");
        System.out.println("\n2D Shapes:");
    
        int index = 0; 
    
        
        for (int i = 0; i < shapes2D.length; i++) {
            System.out.println("[" + index + "] " + shapes2D[i].getClass().getSimpleName());
            index++;
        }
    
        System.out.println("\n3D Shapes:");
    
       
        for (int i = 0; i < shapes3D.length; i++) {
            System.out.println("[" + index + "] " + shapes3D[i].getClass().getSimpleName());
            index++; 
        }
        
        System.out.print("\nEnter shape index for more details (-1 to exit): ");
        int selectedIndex = input.nextInt();
    
        if (selectedIndex >= 0 && selectedIndex < shapes2D.length) 
        {
            shapes2D[selectedIndex].printInfo("");
            
        } else if (selectedIndex >= shapes2D.length && selectedIndex < index) {
            GeometricShape3D shape3D = shapes3D[selectedIndex - shapes2D.length];
            shape3D.printInfo("");
            
        }
    }
    

    static void mergeAllMultiShapes() {
        for (int i = 0; i < shapes2D.length; i++) {
            if (shapes2D[i] instanceof MultiShape2D) {
                recursiveMerge((MultiShape2D) shapes2D[i]);
            }
        }
        System.out.println("All multi-shapes have been merged.");
    }

    static void recursiveMerge(MultiShape2D multiShape) {
        
        GeometricShape2D[] shapes = multiShape.getShapes(); 
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i] instanceof MultiShape2D) {
                recursiveMerge((MultiShape2D) shapes[i]); 
            }
        }
        multiShape.mergeShapes();
    }
    
    static void editShape() {
        System.out.println("\n-----| Shape Edit |-----");
        System.out.println("Select shape to edit:");
    
        int index = 0;
        int shapeIndex2D = -1;
        int shapeIndex3D = -1;
    
        System.out.println("\n2D Shapes:");
        for (int i = 0; i < shapes2D.length; i++) {
            if (!(shapes2D[i] instanceof MultiShape2D)) {
                System.out.println("[" + index + "] " + shapes2D[i].getClass().getSimpleName());
                if (index == shapeIndex2D) shapeIndex2D = i; 
                index++;
            }
        }
    
        System.out.println("\n3D Shapes:");
        for (int i = 0; i < shapes3D.length; i++) {
            System.out.println("[" + index + "] " + shapes3D[i].getClass().getSimpleName());
            if (index == shapeIndex3D) shapeIndex3D = i; 
            index++;
        }
    
        System.out.print("\nEnter shape index to edit: ");
        int selectedIndex = input.nextInt();
    
        if (selectedIndex >= 0 && selectedIndex < shapes2D.length) {
            edit2DShape(selectedIndex);
        }
        else if (selectedIndex >= shapes2D.length && selectedIndex < index) {
            edit3DShape(selectedIndex - shapes2D.length);
        }
        else {
            System.out.println("Invalid selection.");
        }
    }
    
    static void edit2DShape(int index) {
        if (shapes2D[index] instanceof Rectangle) {
            shapes2D[index] = new Rectangle(getFloat("Width: "), getFloat("Height: "));
        } else if (shapes2D[index] instanceof Circle) {
            shapes2D[index] = new Circle(getFloat("Radius: "));
        } else if (shapes2D[index] instanceof Square) {
            shapes2D[index] = new Square(getFloat("Edge length: "));
        } else if (shapes2D[index] instanceof Ellipse) {
            shapes2D[index] = new Ellipse(getFloat("Major Axis: "), getFloat("Minor Axis: "));
        } else if (shapes2D[index] instanceof EquilateralTriangle) {
            shapes2D[index] = new EquilateralTriangle(getFloat("Side length: "));
        }
    }
    
    static void edit3DShape(int index) {
        if (shapes3D[index] instanceof Cuboid) {
            shapes3D[index] = new Cuboid(getFloat("Width: "), getFloat("Height: "), getFloat("Depth: "));
        } else if (shapes3D[index] instanceof Sphere) {
            shapes3D[index] = new Sphere(getFloat("Radius: "));
        } else if (shapes3D[index] instanceof Cylinder) {
            shapes3D[index] = new Cylinder(getFloat("Radius: "), getFloat("Height: "));
        } else if (shapes3D[index] instanceof Cube) {
            shapes3D[index] = new Cube(getFloat("Edge length: "));
        } else if (shapes3D[index] instanceof Pyramid) {
            shapes3D[index] = new Pyramid(getFloat("Base Area: "), getFloat("Height: "));
        }
    }
    
    static void add2DShape(GeometricShape2D shape) {
        GeometricShape2D[] temp = new GeometricShape2D[shapes2D.length + 1];
        System.arraycopy(shapes2D, 0, temp, 0, shapes2D.length);
        temp[shapes2D.length] = shape;
        shapes2D = temp;
    }

    static void add3DShape(GeometricShape3D shape) {
        GeometricShape3D[] temp = new GeometricShape3D[shapes3D.length + 1];
        System.arraycopy(shapes3D, 0, temp, 0, shapes3D.length);
        temp[shapes3D.length] = shape;
        shapes3D = temp;
    }

    static GeometricShape2D[] removeShape2D(int index) {
        GeometricShape2D[] newArray = new GeometricShape2D[shapes2D.length - 1];
        for (int i = 0, j = 0; i < shapes2D.length; i++) {
            if (i != index) {
                newArray[j++] = shapes2D[i];
            }
        }
        return newArray;
    }
    
    static MultiShape2D[] getMultiShapes() {
        return java.util.Arrays.stream(shapes2D).filter(s -> s instanceof MultiShape2D).toArray(MultiShape2D[]::new);
    }

    static float getFloat(String msg) {
        System.out.print(msg);
        return input.nextFloat();
    }
}
