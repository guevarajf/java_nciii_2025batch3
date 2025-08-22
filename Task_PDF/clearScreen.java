// Clear the screen method

import java.io.IOException;

public class clearScreen{
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Linux/Mac
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            // If system command fails, use newlines as fallback
            System.out.println("Could not clear screen, using fallback method...");
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}