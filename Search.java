//-----------------------------------------------------------------------------
// Name: Anthony Wong
// CruzID: 1652596
// Class: 12B
// Date: 24 Jan 2019
// Desc: Reads a file containing words seperated on new lines and searches for a target word and states target's line number in the file.
// File Name: Search.java
//-----------------------------------------------------------------------------

import java.util.Scanner;
import java.io.*;

class Search {

  public static void main(String[] args) {

    // check number of command line arguments
    if(args.length < 2){
       System.err.println("Usage: Search file target1 [target2 ..]");
       System.exit(1);
    }

    Scanner in = null;
    try {
      in = new Scanner(new File(args[0]));
    }
    catch(IOException e) {
      System.err.println(args[0] + " (No such file or directory)");
      System.err.println("Usage: Search file target1 [target2 ..]");
      System.exit(1);
    }

    // count the number of lines in file
    in.useDelimiter("\\Z"); // matches the end of file character
    String s = in.next();   // read in whole file as a single String
    String[] lines = s.split("\n");  // split s into individual lines
    int lineCount = lines.length;  // extract length of the resulting array
    in.close();

    // creates an array the same size as the number of lines in the file
    String[] file = new String[lineCount];

    String line;
    Scanner scan = null;

    try {
      scan = new Scanner(new File(args[0]));
    }
    catch(FileNotFoundException e) {
      System.err.println(args[0] + " (No such file or directory)");
      System.err.println("Usage: Search file target1 [target2 ..]");
      System.exit(1);
    }

    // read lines from file and puts them in array
    int i = 0;
    while(scan.hasNextLine()) {
      line = scan.nextLine();
      file[i] = line;
      i++;
    }

    scan.close();

    int[] lineNumber = new int[file.length];
    for (int j = 1; j <= file.length; j++) {
      lineNumber[j - 1] = j;
    }

    mergeSort(file, lineNumber, 0, file.length - 1); //orders the words in the file

    for (int j = 1; j < args.length; j++) {
      if (binarySearch(file, 0, file.length - 1, args[j]) == -1) {
        System.out.println(args[j] + " not found");
      }
      else {
        System.out.println(args[j] + " found on line " + lineNumber[binarySearch(file, 0, file.length - 1, args[j])]);
      }
    }
  }

  public static int binarySearch(String[] A, int p, int r, String target) {
    // returns the value of the target string

    int q;
    if(p > r) {
      return -1;
    }
    else {
      q = (p + r) / 2;

      if(target.compareTo(A[q]) == 0) {
        return q;
      }
      else if(target.compareTo(A[q]) < 0) {
        return binarySearch(A, p, q - 1, target);
      }
      else {
        return binarySearch(A, q + 1, r, target);
      }
    }
  }

  public static void mergeSort(String[] A, int[] lineNumber, int p, int r){
    int q;
    if(p < r) {
      q = (p + r) / 2;
      mergeSort(A, lineNumber, p, q);
      mergeSort(A, lineNumber, q + 1, r);
      merge(A, lineNumber, p, q, r);
    }
  }

  public static void merge(String[] A, int[] lineNumber, int p, int q, int r) {
    // merges sorted subarrays A[p..q] and A[q+1..r]
    int n1 = q - p + 1;  // length of first subarray
    int n2 = r - q;  // length of second subarray
    String[] L1 = new String[n1];
    String[] R1 = new String[n2];
    int[] L2 = new int[n1];
    int[] R2 = new int[n2];
    int i, j, k;

    for(i = 0; i < n1; i++) {
      L1[i] = A[p + i];
    }
    for(j = 0; j < n2; j++) {
      R1[j] = A[q + j + 1];
    }
    for(i = 0; i < n1; i++) {
      L2[i] = lineNumber[p + i];
    }
    for(j = 0; j < n2; j++) {
      R2[j] = lineNumber[q + j + 1];
    }

    i = 0; j = 0;
    for(k = p; k <= r; k++) {
      if(i < n1 && j < n2) {
        if(L1[i].compareTo(R1[j]) < 0) {
          A[k] = L1[i];
          lineNumber[k] = L2[i];
          i++;
        }
        else {
          A[k] = R1[j];
          lineNumber[k] = R2[j];
          j++;
        }
      }
      else if(i < n1) {
        A[k] = L1[i];
        lineNumber[k] = L2[i];
        i++;
      }
      else {
        A[k] = R1[j];
        lineNumber[k] = R2[j];
        j++;
      }
    }
  }
}
