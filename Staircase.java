import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Staircase {

    public static void main(String[] args) {
        // input a single number
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Staircase(n);
    }

    public static void Staircase(int n){

        // creates a string of the staircase in reverse
        String reverseStairCase = "";
        for (int i = 0; i < n; i++){
            for (int j = n-i; j > 0; j--){
               reverseStairCase+="#";
            }
            reverseStairCase+="\n";
        }

        //Stores each line of the reverse staircase in an array
        Scanner reader = new Scanner(reverseStairCase);
        String[] arr = new String[n];
        for (int k = 0; reader.hasNextLine(); k++){
            arr[k] = reader.nextLine();
        }

        //prints out the array with the appropriate spaces
        for (int l = arr.length-1; l >=0; l--){
            for (int m = l-1; m >=0; m--){
                System.out.print(" ");
            }
            System.out.println(arr[l]);
        }
    }
}
