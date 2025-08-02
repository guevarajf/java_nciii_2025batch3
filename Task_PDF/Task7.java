package Chapter4;

import java.util.Scanner;

public class Task7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter First integer: ");
        int number1 = scanner.nextInt();

        System.out.print("Enter Second integer: ");
        int number2 = scanner.nextInt();

        Task7 obj = new Task7();

        System.out.println("Addition result: " + obj.num_add(number1, number2));
        System.out.println("Subtraction result: " + obj.num_sub(number1, number2));
        System.out.println("Multiplication result: " + obj.num_mul(number1, number2));
        System.out.println("Division result: " + obj.num_div(number1, number2));

        scanner.close();
    }


    public int num_add(int a, int b) {
        return a + b;
    }

    public int num_sub(int a, int b) {
        return a - b;
    }

    public int num_mul(int a, int b) {
        return a * b;
    }

    public double num_div(int a, int b) {
        if (b == 0) {
            System.out.println("Error: Division by zero");
            return 0;
        }
        return (double) a / b;
    }
}

