import java.util.Scanner;

public class IntegerInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter an integer: ");
        int n = scanner.nextInt();

        // Generate pattern using for loop
        for (int i = 1; i <= n; i++) {
            // Print i asterisks on the ith line
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println(); // Move to next line
        }

        scanner.close();
    }
}
