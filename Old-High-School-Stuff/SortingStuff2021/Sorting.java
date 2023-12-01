import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Write a description of class Sorting here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sorting
{

    public static void main (String[] args){
        Timer timer = new Timer();

        int[] values = loadIntArrayFromFile ("25000_source.txt");

        timer.startTimer();

        int[] result = mySort(values);

        timer.endTimer();
        if (!checkResults (result, true)){
            System.out.println("ERROR - DID NOT SORT!!");
            System.exit (0);
        }
        System.out.println("Completed My Sort -- Time taken: "+ timer.getTimeString());

        values = loadIntArrayFromFile ("25000_source.txt");

        timer.startTimer();

        bubbleSortSteps(values);

        timer.endTimer();
        System.out.println("Completed Sort -- Time taken: "+ timer.getTimeString());
    }

    // [2, 1] ==> [1, 2];
    // Swapping two values in an array:
    // temp = values[0];
    // values[0] = values[1];
    // values[1] = temp;
    // won't work!

    // [4, 6, 1, 2] ==> [1, 2, 4, 6]

    public static int[] mySort (int[] values){
        int[] sortedValues = new int[values.length];
        boolean sorted = false;
        for (int i = 0; i < values.length-1; i++) {
            sortedValues[i] = values[i];
        }

        while (!sorted) {
            sorted = true;
            for (int i = 1; i < sortedValues.length; i++) {
                if (sortedValues[i-1] > sortedValues[i] && sortedValues[i] != sortedValues.length) {
                    sorted = false;
                    int temp = sortedValues[i];
                    sortedValues[i] = sortedValues[i-1];
                    sortedValues[i-1] = temp;
                }
            }
        }

        return sortedValues;
    }

    public static int[] bubbleSort (int[] num)
    {
        boolean done = false;
        for (int i = 0; i < num.length && !done; i++) {
            done = true;
            for (int x = 1; x < num.length - i; x++) {
                if (num[x - 1] > num[x]) {
                    int temp = num[x - 1];
                    num[x - 1] = num[x];
                    num[x] = temp;
                    done = false;
                }
            }
        }
        return num;
    }

    public static int[] insertionSort(int array[]) {  
        int n = array.length;  
        for (int j = 1; j < n; j++) {  
            int key = array[j];  
            int i = j-1;  
            while ( (i > -1) && ( array [i] > key ) ) {  
                array [i+1] = array [i];  
                i--;  
            }  
            array[i+1] = key;  
        }
        return array;  
    }  

    public static int[] bubbleSortSteps (int[] num)
    {
        boolean done = false;
        long steps = 0;
        steps++;
        steps++; // init for loop variable i = 0
        for (int i = 0; i < num.length && !done; i++) {
            steps += 2; // 2 for loop comparisons
            done = true;
            steps++; // done = true;
            steps++; // init next for loop variable x = 1
            for (int x = 1; x < num.length - i; x++) {
                steps++; //for loop condition
                if (num[x - 1] > num[x]) {
                    int temp = num[x - 1];
                    num[x - 1] = num[x];
                    num[x] = temp;
                    done = false;
                    steps += 4;
                }
                steps++; // if condition above
                steps++; // for loop incrementation, x++
            }
            steps++; // for loop incrementation i++
        }
        System.out.println("Ran step counter - Total Steps: " + steps);
        return num;

    }

    /**
     * Sort result checker.
     * 
     * This modular method will check any given array of integers and 
     * return true if the values are correctly sorted or false if there
     * are any errors. Setting the parameter boolean reporting to true
     * will also output the results to the screen.
     * 
     * @param theArray  The array of integers that you want to check
     * @param report    True if you want results displayed to the screen
     * @return boolean  True if the values are in numerical order
     * 
     * @author Jordan Cohen
     * @version April 2014
     */
    public static boolean checkResults (int[] theArray, boolean report)
    {
        System.out.println("Checking Validity");
        boolean stillValid = true;
        int counter = 0;
        while (stillValid && counter < theArray.length - 1)
        {
            if (theArray[counter] > theArray[counter + 1])
            {
                stillValid = false;
            }
            counter++;
        }
        if (report)
        {
            if (stillValid)
            {
                System.out.println("Checked " + theArray.length + " values. Sort is valid");
            }
            else
            {
                System.out.println("Checked " + theArray.length + " values. Found error at " + counter);
            }
        }

        return stillValid;
    }

    private static int[] loadIntArrayFromFile (String fileName)
    {
        int size = 0;
        int[] results;
        System.out.println("Attempting to read " + fileName);

        try{
            Scanner scan = new Scanner (new File (fileName));            
            while (scan.hasNext())
            {
                scan.nextLine();
                size++;
            }
            scan = new Scanner (new File (fileName));
            results = new int[size];
            for (int i = 0; i < size; i++)
            {
                results[i] = Integer.parseInt(scan.nextLine());
            }

            System.out.println("Completed Loading Process Successfully. Size is " + results.length);
        }
        catch (Exception e)
        {
            System.out.println("Error " + e);
            return null;
        }
        return results;
    }

}
