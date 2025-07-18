package chapter6;

public class ExceptionLesson {
    public static void main(String[] args) throws Exception, Runtime{
        throw new Exception ("Bad Code");

        
    }
    
}

class ExceptionLesson2{
    public static void main(String[] args) {
        try{
            System.out.println(args[0]);
        }

        catch(ArrayIndexOutOfBoundsException e){
            // catch block
            System.out.println("eeeee")
        }

        finally {
            // finally block
            System.out.println("finally block")
        }

        System.out.println("Done")
    }
}