//package Task121;

public class MyDate {
    public static void main(String[] args) {
        DateTask date1 = new DateTask();
        DateTask date2 = new DateTask();
        
        // Set date1 to January 1st, 1978
        date1.setDate(1, 1, 1978);
        
        // Set date2 to September 21st, 1984
        date2.setDate(9, 21, 1984);
        
        System.out.println("Date 1: " + date1.toString());
        System.out.println("Date 2: " + date2.toString());
        
        // Display leap years
        System.out.println("\nLeap years from 1980 to 2023:");
        DateTask.leapYears();
        
        System.out.println("\nDemonstrating encapsulation:");
        System.out.println("Date1 - Day: " + date1.getDay() + ", Month: " + date1.getMonth() + ", Year: " + date1.getYear());
        
        System.out.println("\nTesting validation:");
        DateTask testDate = new DateTask();
        testDate.setDate(12, 25, 2023);
        System.out.println("Valid date (12/25/2023): " + testDate.toString());
        
        testDate.setDate(13, 32, 2023);
        System.out.println("Invalid date (13/32/2023): " + testDate.toString());
    }
}