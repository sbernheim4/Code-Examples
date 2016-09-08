import java.lang.Math;
import java.util.Scanner;
import java.util.Arrays;
public class MazeGenerator {

	public static void main (String[] args) {
		Scanner console = new Scanner(System.in);
		// Read in a string of 1s and 0s where 1 represents a wall and 0 a space
		System.out.println("Enter a string of 1s and 0s that represents the maze\nFor example, the following string is for a 9x9 maze:\n111111111100010001111010101100010101101110101100000101111011101100000101111111111\n");
		String inputNum = console.next();
		// 9 x 9 maze example input --> 111111111100010001111010101100010101101110101100000101111011101100000101111111111

		// Get the width of the maze
		System.out.println("Enter the width of the matrix");
		int width = console.nextInt();

		// Get the height of the matrix
		System.out.println("Enter the height of the matrix");
		int height = console.nextInt();

		// Generate a matrix to hold the input string in a better format
		char[][] matrix = parseInput(inputNum, width, height);
		makeMaze(matrix);
	}

	public static char[][] parseInput(String inputNum, int width, int height) {
		// Create a new character array with dimensions the user entered
		char[][] input = new char[width][height];
		int index = 0;

		// Add characters to the matrix based on the width and height specified by the user
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++) {
				input[i][j] = inputNum.charAt(index);
				index++;
			}
		}
		return input;
	}

	public static void makeMaze(char[][] input) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// If the character is a 0 print a space
				if (input[i][j] == '0') {
					System.out.print(" ");
				} else {
					// Determine if a + or | or - should be printed

					// If the index of the 1 is event then print a +
					if ((i + j) % 2 == 0) {
						System.out.print("+");
					} else {
						// Determine if - or | should be printed
						if ((i >= 1 && input[i-1][j] == '1' && input[i+1][j] == '1' && ((i + 1 + j) % 2 == 0)) ) {
							// if i >=1 (aka not at row 0) && there is a + sign above and below the current sign print |
							System.out.print("|");
						} else {
							// else print -
							System.out.print("-");
						}
					}
				}
			}
			// Print a newline after a row has finished being printed for the next row
			System.out.println();
		}

	}
}
