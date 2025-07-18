package chapter6;

// Task15.java class
public class Task15 {
    public static void main(String[] args) {
        // Instances of Circle and Rectangle
        Circle circle = new Circle("Red", 5.0);
        Rectangle rectangle = new Rectangle("Blue", 10.0, 6.0);
        
        // Display Circle information
        System.out.println("=== CIRCLE ===");
        System.out.println("Color: " + circle.getColor());
        System.out.println("Radius: " + circle.getRadius());
        System.out.println("Area: " + String.format("%.2f", circle.calculateArea()));
        System.out.println("Perimeter: " + String.format("%.2f", circle.calculatePerimeter()));
        
        System.out.println();
        
        // Display Rectangle information
        System.out.println("=== RECTANGLE ===");
        System.out.println("Color: " + rectangle.getColor());
        System.out.println("Length: " + rectangle.getLength());
        System.out.println("Width: " + rectangle.getWidth());
        System.out.println("Area: " + String.format("%.2f", rectangle.calculateArea()));
        System.out.println("Perimeter: " + String.format("%.2f", rectangle.calculatePerimeter()));
    }
}

// Shape interface
interface Shape {
    double calculateArea();
    double calculatePerimeter();
}

// Abstract class AbstractShape implementing Shape interface
abstract class AbstractShape implements Shape {
    // Instance variables for common attributes
    protected String color;
    protected double length;
    protected double width;
    
    // Constructor that initialize attributes
    public AbstractShape(String color, double length, double width) {
        this.color = color;
        this.length = length;
        this.width = width;
    }
    
    // Abstract methods - implemented by concrete classes
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    // Getter methods
    public String getColor() {
        return color;
    }
    
    public double getLength() {
        return length;
    }
    
    public double getWidth() {
        return width;
    }
}

// class Circle extending AbstractShape
class Circle extends AbstractShape {
    private double radius;
    
    // Constructor
    public Circle(String color, double radius) {
        super(color, radius * 2, radius * 2); // length and width set to diameter
        this.radius = radius;
    }
    
    // Implementation of calculateArea method
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    // Implementation of calculatePerimeter method
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    public double getRadius() {
        return radius;
    }
}

// class Rectangle extending AbstractShape
class Rectangle extends AbstractShape {
    // Constructor
    public Rectangle(String color, double length, double width) {
        super(color, length, width);
    }
    
    // Implementation of calculateArea method
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    // Implementation of calculatePerimeter method
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}

