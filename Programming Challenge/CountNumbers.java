import java.util.Scanner;

public class CountNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter a sentence: ");
        String input = scanner.nextLine();

        // Trim leading/trailing spaces and split by one or more spaces
        String[] words = input.trim().split("\\s+");

        // Check for empty input
        int wordCount = input.trim().isEmpty() ? 0 : words.length;

        // Display result
        System.out.println("Number of words: " + wordCount);

        scanner.close();
    }
}
