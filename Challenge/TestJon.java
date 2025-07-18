package Challenge;

class TestJon{

	public static void main(String[] args){
	// switch. Responses are Yes, No, Maybe
    
		int[] numbers = {3,6,7,4,2,5}; 
        int count = 1;
        for (int i=0; i<numbers.length; i++) {
        	for (int a = 0; a<6; a++){
              
               if(i != a){
                    if(numbers[i]*10 + numbers[a]<59)
                        System.out.println(count +". " +numbers[i] + numbers[a]);

               }
                
                
                
                
                count++;
            }
    	}
    

	}
}