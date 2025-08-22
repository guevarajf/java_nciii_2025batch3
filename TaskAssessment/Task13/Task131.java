//package Task13;

// Base class for all ingredients
abstract class Ingredient {
    protected String name;
    
    public Ingredient(String name) {
        this.name = name;
    }
    
    public abstract void showIngredients();
    
    public String getName() {
        return name;
    }
}

// Meal classes
class Meal extends Ingredient {
    public Meal(String name) {
        super(name);
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Meal: " + name + " - Base meal ingredient");
    }
}

// Specific meal types
class Affuada extends Meal {
    public Affuada() {
        super("Affuada");
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Affuada: Tomato Sauce ✓, Meat ✓, Other ingredients not included");
    }
}

class Mechado extends Meal {
    public Mechado() {
        super("Mechado");
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Mechado: Tomato Sauce ✓, Meat ✓, Potatoes & Carrots ✓");
    }
}

class Menudo extends Meal {
    public Menudo() {
        super("Menudo");
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Menudo: Tomato Sauce ✓, Meat ✓, Potatoes & Carrots ✓, Tomato Paste ✓, Liver Spread ✓, Raisins ✓, Hotdog ✓");
    }
}

class Caldereta extends Meal {
    public Caldereta() {
        super("Caldereta");
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Caldereta: All ingredients included ✓ - Tomato Sauce, Meat, Potatoes & Carrots, Tomato Paste, Liver Spread, Raisins, Hotdog, Cheese");
    }
}

// Additional ingredient classes for demonstration
class TomatoSauce extends Ingredient {
    public TomatoSauce() {
        super("Tomato Sauce");
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Tomato Sauce: Essential base for Filipino dishes");
    }
}

class Meat extends Ingredient {
    public Meat() {
        super("Meat");
    }
    
    @Override
    public void showIngredients() {
        System.out.println("Meat: Primary protein source");
    }
}

// Main class demonstrating polymorphism
public class Task131 {
    
    public static void main(String[] args) {
        System.out.println("=== Java Polymorphism Demonstration ===");
        System.out.println("Demonstrating various types of meal objects using polymorphism\n");
        
        // Create array of Ingredient references pointing to different object types
        Ingredient[] ingredients = {
            new Affuada(),
            new Mechado(), 
            new Menudo(),
            new Caldereta(),
            new TomatoSauce(),
            new Meat()
        };
        
        // Create specific meal objects with Meal reference variables
        Meal meal1 = new Affuada();
        Meal meal2 = new Mechado();
        Meal meal3 = new Menudo();
        Meal meal4 = new Caldereta();
        
        System.out.println("1. Demonstrating polymorphism with Ingredient reference variables:");
        System.out.println("----------------------------------------------------------------");
        
        // Polymorphic method calls - same method call, different behaviors
        for (Ingredient ingredient : ingredients) {
            ingredient.showIngredients();
        }
        
        System.out.println("\n2. Demonstrating polymorphism with Meal reference variables:");
        System.out.println("------------------------------------------------------------");
        
        Meal[] meals = {meal1, meal2, meal3, meal4};
        
        for (Meal meal : meals) {
            meal.showIngredients();
        }
        
        System.out.println("\n3. Demonstrating runtime type determination:");
        System.out.println("---------------------------------------------");
        
        // Show actual object types at runtime
        for (Ingredient ingredient : ingredients) {
            System.out.println("Reference type: Ingredient, Actual object: " + 
                             ingredient.getClass().getSimpleName() + 
                             " - Name: " + ingredient.getName());
        }
        
        System.out.println("\n4. Demonstrating method overriding and dynamic binding:");
        System.out.println("-------------------------------------------------------");
        
        // Single reference variable pointing to different objects at different times
        Ingredient currentIngredient;
        
        currentIngredient = new Affuada();
        System.out.print("currentIngredient = new Affuada(); -> ");
        currentIngredient.showIngredients();
        
        currentIngredient = new Mechado();
        System.out.print("currentIngredient = new Mechado(); -> ");
        currentIngredient.showIngredients();
        
        currentIngredient = new Menudo();
        System.out.print("currentIngredient = new Menudo(); -> ");
        currentIngredient.showIngredients();
        
        currentIngredient = new Caldereta();
        System.out.print("currentIngredient = new Caldereta(); -> ");
        currentIngredient.showIngredients();
        
        System.out.println("\n=== Polymorphism Demonstration Complete ===");
        System.out.println("This program demonstrates:");
        System.out.println("• Reference variables of parent type referring to child objects");
        System.out.println("• Method overriding and dynamic method binding");
        System.out.println("• Runtime type determination");
        System.out.println("• Same reference variable pointing to different object types at different times");
    }
}