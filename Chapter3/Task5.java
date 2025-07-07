import java.util.*;

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
                
        System.out.print("Enter the 1st out of 3 number: ");
        numbers.add(scanner.nextInt());
        
        System.out.print("Enter the 2nd out of 3 number: ");
        numbers.add(scanner.nextInt());
        
        System.out.print("Enter the 3rd out of 3 number: ");
        numbers.add(scanner.nextInt());
        
        // Find the largest number using Collections.max()
        int largest = Collections.max(numbers);
        
        // Check if all numbers are equal
        boolean allEqual = true;
        for (int i = 1; i < numbers.size(); i++) {
            if (!numbers.get(i).equals(numbers.get(i-1))) {
                allEqual = false;
                break;
            }
        }
        
        // Output result
        if (allEqual) {
            System.out.println("All numbers are equal");
        } else {
            System.out.println("The largest number is: " + largest);
        }
        
        scanner.close();
    }
}