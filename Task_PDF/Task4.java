import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        do{
            System.out.print("Enter string b [Press Enter to exit]: ");
            String b = scanner.nextLine();
        
            if (b.toLowerCase().equals("")){
                break;
            }

            String b_Reverse = "";
        
            for (int i = b.length() - 1; i >= 0; i--) {
                b_Reverse += b.substring(i, i + 1);
            }
        
            if (b.equals(b_Reverse)) {
                System.out.println("The input String '" + b +"' is a Palindrome of '" + b_Reverse +"'");
            } else {
                System.out.println("The input String is not a Palindrome");
            }
            System.out.println("");

        } while (true); 
      
        scanner.close();

    }
}