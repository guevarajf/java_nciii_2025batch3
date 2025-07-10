package Chapter1;

public class Constructor1Lesson {
    public static void main(String[] args) {
        Chicken c1 = new Chicken();
        Chicken c2 = new Chicken();
        System.out.println(c1);
        System.out.println(c2);
    }
    
}

class Chicken {
    int numEggs = 0;
    String name;
    
    public Chicken() {
        System.out.println("Chicken created");
    }
}