/* PLEASE NOTE: THIS IS NOT MY SOLUTION. I DID NOT DERIVE THE SOLUTION METHOD
NOR WRITE THE CODE. THE SOLUTION CAME FROM THE LINK BELOW. I HAVE THIS CODE AS
AN EXAMPLE OF HOW THE XOR OPERATOR WORKS AND ITS SYNTAX IN JAVA. UNTIL NOW I HAD
NOT EVER USED THE XOR OPERATOR OR KNOWN WHAT IT DOES. ALL CREDIT GOES TO THE
EXPLANATION OF THE LINK BELOW.

http://www.byte-by-byte.com/twomissingnumbers/2/*/


import java.util.Arrays;

public class TwoMissingNumbers {

    public static void main(String[] args) {
        /*
        Array with values from 1 to n where n = 16 but with two numbers
        missing */
        int[] missingArray = {1,2,4,5,6,7,8,9,10,11,12,13,15,16};
        twoMissing(missingArray);
    }

    public static void twoMissing(int[] array) {
        System.out.println("Input Array: " + Arrays.toString(array));

        // What is n for the array --> Add two b/c two numbers are missing
        int size = array.length + 2;
        System.out.println("Size: " + size);

        // Sum from 1 to n
        long totalSum = size * (size + 1) / 2;
        System.out.println("Total Sum: " + totalSum);

        // Get the sum of the elements in the array
        long arrSum = 0;
        for (int i : arr) {
            arrSum += i;
        }
        System.out.println("arrSum: " + arrSum);

        /* Determine the halfway point --> Of the two missing numbers, one is
        greater than n/2 and one is less than n/2. The Pivot will ensure that
        the two values are separated for the xor xomparison below */
        int pivot = (int) ((totalSum - arrSum) / 2);
        System.out.println("Pivot: " + pivot);

        int totalLeftXor = 0;
        int arrLeftXor = 0;
        int totalRightXor = 0;
        int arrRightXor = 0;

        // Get the xor of all the numbers 1 to pivot
        for (int i = 1; i <= pivot; i++) {
            totalLeftXor ^= i;
        }
        System.out.println("totalLeftXor: " + totalLeftXor);

        // Get the xor of all the numbers from pivot+1 to n
        for (int i = pivot + 1; i <= size; i++) {
            totalRightXor ^= i;
        }
        System.out.println("totalRightXor: " + totalRightXor);

        /* While below the pivot, get the xor of 1 to pivot of the array and the
        xor of pivot+1 to n.

        The difference here is that arrLeftXor will not contain one of the
        missing numbers and arrRightXor will not contain the xor with the other
        number */
        for (int i : array) {
            if (i <= pivot) {
                arrLeftXor ^= i;
            } else arrRightXor ^= i;
        }
        System.out.println("arrLeftXor: " + arrLeftXor);
        System.out.println("arrRightXor: " + arrRightXor);

        /* totalLeftXor ^ arrLeftXor will reveal the missing number that is less
        than the pivot and totalRightXor ^ arrRightXor will reveal the missing
        number that is bigger than the pivot */
        int[] answer = new int[]{ totalLeftXor ^ arrLeftXor,
            totalRightXor ^ arrRightXor};
        System.out.println("The missing numbers are: " + totalLeftXor ^ arrLeftXor + " and " + totalRightXor ^ arrRightXor);
    }
}
