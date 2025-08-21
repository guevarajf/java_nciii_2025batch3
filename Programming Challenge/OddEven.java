import java.util.Scanner;

public class OddEven {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter an integer (or type 'exit' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }

            try {
                int num = Integer.parseInt(input);

                // Check odd or even
                String parity = (num % 2 == 0) ? "Even" : "Odd";

                // Check sign
                String sign;
                if (num > 0) {
                    sign = "Positive";
                } else if (num < 0) {
                    sign = "Negative";
                } else {
                    sign = "Zero";
                }

                System.out.println("Result: " + parity + ", " + sign);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer or 'exit'.");
            }
        }

        scanner.close();
    }
}
