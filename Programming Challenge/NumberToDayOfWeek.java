import java.util.Scanner;

public class NumberToDayOfWeek {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a number (1-7), or 0 to exit: ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }

            try {
                int dayNumber = Integer.parseInt(input);
                String day;

                switch (dayNumber) {
                    case 1: day = "Monday"; break;
                    case 2: day = "Tuesday"; break;
                    case 3: day = "Wednesday"; break;
                    case 4: day = "Thursday"; break;
                    case 5: day = "Friday"; break;
                    case 6: day = "Saturday"; break;
                    case 7: day = "Sunday"; break;
                    default: day = "Invalid input";
                }

                System.out.println(day);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }

        scanner.close();
    }
}
