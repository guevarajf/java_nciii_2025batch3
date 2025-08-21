import java.util.Scanner;

public class SumOfAllInteger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;

        System.out.println("Enter integers to sum. Type 'done' to finish:");

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int number = Integer.parseInt(input);
                sum += number;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer or 'done'.");
            }
        }

        System.out.println("The sum of all integer inputs is: " + sum);
        scanner.close();
    }
}
