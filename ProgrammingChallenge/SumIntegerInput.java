import java.util.Scanner;

public class SumIntegerInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int a = scanner.nextInt();

        System.out.print("Enter second number: ");
        int b = scanner.nextInt();

        System.out.println("Sum of input " + a + " and " + b + " is : " + (a + b));

        scanner.close();
    }
}