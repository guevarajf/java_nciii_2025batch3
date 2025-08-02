package chapter6;

// Animal interface
interface Animal {
    boolean feed(boolean timeToEat);
    void groom();
    void pet();
}

// Gorilla class implementing Animal interface
class Gorilla implements Animal {
    private String name;
    private boolean isHungry;
    private boolean isClean;
    private boolean isHappy;
    
    // Constructor
    public Gorilla(String name) {
        this.name = name;
        this.isHungry = true;
        this.isClean = true;
        this.isHappy = true;
    }
    
    // lather, rinse, repeat
    @Override
    public boolean feed(boolean timeToEat) {
        if (timeToEat && isHungry) {
            System.out.println(name + " is eating delicious fruits and vegetables!");
            isHungry = false;
            isHappy = true;
            return true;
        } else if (!timeToEat) {
            System.out.println("It's not feeding time for " + name);
            return false;
        } else {
            System.out.println(name + " is not hungry right now.");
            return false;
        }
    }
    
    // pet at your own risk
    @Override
    public void groom() {
        System.out.println(name + " is grooming itself, cleaning its fur carefully.");
        isClean = true;
        // Lather, rinse, repeat process
        System.out.println("Lathering...");
        System.out.println("Rinsing...");
        System.out.println("Repeating the process...");
        System.out.println(name + " is now clean and well-groomed!");
    }
    
    // put gorilla food into cage
    @Override
    public void pet() {
        System.out.println("WARNING: Petting " + name + " at your own risk!");
        if (isHappy && !isHungry) {
            System.out.println(name + " enjoys the gentle petting and makes happy gorilla sounds.");
            System.out.println("Don't forget to put gorilla food into the cage afterwards!");
        } else if (isHungry) {
            System.out.println(name + " is hungry and might be aggressive. Feed first!");
            System.out.println("Quickly putting gorilla food into the cage...");
            isHungry = false;
        } else {
            System.out.println(name + " is not in the mood for petting right now.");
        }
    }
    
    // Additional utility methods
    public void makeHungry() {
        this.isHungry = true;
        System.out.println(name + " is now hungry!");
    }
    
    public void displayStatus() {
        System.out.println("\n" + name + "'s Status:");
        System.out.println("Hungry: " + isHungry);
        System.out.println("Clean: " + isClean);
        System.out.println("Happy: " + isHappy);
    }
}

public class Task16 {
    public static void main(String[] args) {
        // gorilla instance
        Gorilla kong = new Gorilla("Kong");
        
        System.out.println("=== Gorilla Care Simulation ===\n");
        
        // Display initial status
        kong.displayStatus();
        
        // Try to feed the gorilla
        System.out.println("\n--- Feeding Time ---");
        kong.feed(true);
        
        // Groom the gorilla
        System.out.println("\n--- Grooming Time ---");
        kong.groom();
        
        // Pet at your own risk
        System.out.println("\n--- Petting Time ---");
        kong.pet();
        
        // Make gorilla hungry again and try petting
        System.out.println("\n--- Making Gorilla Hungry ---");
        kong.makeHungry();
        kong.pet();
        
        // Final status
        kong.displayStatus();
    }
}