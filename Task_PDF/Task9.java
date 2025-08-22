//package Chapter4;

import static java.lang.Math.*;

public class Task9 {
    
    public static int add(int a, int b) {
        return a + b;
    }
    
    public static int subtract(int a, int b) {
        return a - b;
    }
    
    public static int multiply(int a, int b) {
        return a * b;
    }
    
    public static float divide(int a, int b) {
        return (float) a / b;
    }
    
    public static void main(String[] args) {
        int x = 10;
        int y = 5;
        
        System.out.println("Addition: " + add(x, y));
        System.out.println("Subtraction: " + subtract(x, y));
        System.out.println("Multiplication: " + multiply(x, y));
        System.out.println("Division: " + divide(x, y));
        
        // Using static imports from Math class
        System.out.println("Square root of 16: " + sqrt(16));
        System.out.println("Power of 2^3: " + pow(2, 3));
        System.out.println("Max of 5 and 8: " + max(5, 8));
    }
}