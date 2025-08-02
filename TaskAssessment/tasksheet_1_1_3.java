/*
Stored in:
Repository:  https://github.com/guevarajf/java_nciii_2025batch3.git
Folder: TaskAssessment> 
Filename: tasksheet_1_1_3
*/

class tasksheet_1_1_3 {

    public static void main(String[] args) {
        int check_number = 10;
        String[] message = { " is even number", " is odd number" };
        for (int i = 0; i <= check_number; ++i) {
            System.out.println(i + message[i % 2]);
        }

    }
}