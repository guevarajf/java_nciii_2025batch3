//package Chapter4;
import java.io.IOException;
public class Task8 {
    
    // Method  with variable arguments and computes the cumulative sums
    public static void computeCumulativeSum(int... numbers) {
        if (numbers.length == 0) {
            System.out.println("No parameters provided.");
            return;
        }
        
        System.out.println("Parameters: " + java.util.Arrays.toString(numbers));
        
        int totalSum = 0;
        // calculate the total sum of all parameters
        for (int num : numbers) {
            totalSum += num;
        }
        
        System.out.println("Total sum of all parameters: " + totalSum);
        System.out.println();
        
        // compute / display cumulative sum for each parameter
        int cumulativeSum = 0;
        for (int i = 0; i < numbers.length; i++) {
            cumulativeSum += numbers[i];
            System.out.println("Parameter " + (i + 1) + ": " + numbers[i] + 
                             " -> Cumulative sum up to this point: " + cumulativeSum);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {

        // Clear screen before starting
        clearScreen.clearScreen();

        System.out.println("Example 1: Parameters (4, 5, 10)");
        computeCumulativeSum(4, 5, 10);
        
        System.out.println("Example 2: Parameters (1, 2, 3, 4)");
        computeCumulativeSum(1, 2, 3, 4);
        
        System.out.println("Example 3: Parameters (7, 8)");
        computeCumulativeSum(7, 8);
        
        System.out.println("Example 4: Single parameter (5)");
        computeCumulativeSum(5);

    }
}