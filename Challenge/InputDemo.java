package Challenge;

import java.util.Scanner;

public class InputDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        System.out.print("What is your name : ");
        String response = scanner.nextLine();
        System.out.println("Welcome " + response);



    }
    
}
