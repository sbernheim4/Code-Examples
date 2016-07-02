public class Staircase {

    public static void main(String[] args) {
        System.out.println("Enter a number"); 
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
		Staircase(n);
    }

    public static void Staircase(int n){
    	
		for (int line = 0; line < n; line++) {
			int spaces; 

			for (spaces = n-line-1; spaces > 0; spaces--){
				System.out.print(" ");
			}

			for (int star = line+1; star > 0; star--){
				System.out.print("#");
			} 

			System.out.println(); 
		}

	}
}
