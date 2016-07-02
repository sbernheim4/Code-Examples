import java.util.Scanner; 

public class Staircase {

    public static void main(String[] args) {
        System.out.println("Enter a number"); 
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
		Staircase(n);
    }

    public static void Staircase(int n){
    	
		// outer for loop to go through each line that will be printed to. 
		for (int line = 0; line < n; line++) {
			
			// controls the number of spaces to be printed on each line
			for (int spaces = n-line; spaces > 1; spaces--){
				System.out.print(" ");
			}
			
			/* 
			 * Controls the number of # symbols to be printed on each line 
			 * Start with 1 # on line 1, 2 # on line 2 ... until at the last line
			 * # is printed a line number of times
			 * NOTE: do line+1 to offset since line starts at 0 
			 */
			for (int star = line+1; star > 0; star--){
				System.out.print("#");
			} 

			System.out.println(); 
		}

	}
}
