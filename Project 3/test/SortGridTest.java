import java.util.*;
import java.io.*;

public class SortGridTest {
    private static int c1 = 0;
    private static int c2 = 0;
    private static int c3 = 0;
    public static void main(String[] args) {
	int score = 0;
	int testNum = 1;
	int ptsPoss = 2;

	/***ACCURACY TESTS***/
	//Test 1
	score += testAccuracy(testNum++, ptsPoss, 5, -9, 9);	
	//Test 2
	score += testAccuracy(testNum++, ptsPoss, 10, -9, 9);
	//Test 3
	score += testAccuracy(testNum++, ptsPoss, 15, -9, 9);
	//Test 4
	score += testAccuracy(testNum++, ptsPoss, 25, 10, 99);
	//Test 5
	score += testAccuracy(testNum++, ptsPoss, 50, 10, 99);
	//Test 6
	score += testAccuracy(testNum++, ptsPoss, 100, 10, 99);
	//Test 7
	score += testAccuracy(testNum++, ptsPoss, 100, -9, 9);

	/***ROBUSTNESS TESTS***/
	//Test 8
	score += testRobustness(testNum++, "test8.txt");
	//Test 9
	score += testRobustness(testNum++, "test9.txt");
	//Test 10
	score += testRobustness(testNum++, "test10.txt");
	//Test 11
	score += testRobustness(testNum++, "test11.txt");

	System.out.println("SortGrid Total: " + score);
    }

    private static int testRobustness(int testNum, String fn) {
	System.out.println("Starting Test " + testNum + "...");
	int[][] grid = readGrid(fn);
	int c = SortGrid.sort(grid);
	if(!isSorted(grid)) {
	    System.out.println("Grid is not sorted.");
	    return 0;
	}
	System.out.println("# of Compares: " + c);
	int score = 0;
	if(c <= 0)
	    return 0;
	if(c < c1)
	    score = 3;
	else if(c < c2)
	    score = 2;
	else if(c < c3)
	    score = 1;
	System.out.println("Score: " + score + "/3");
	return score;
    }

    private static boolean isSorted(int[][] grid) {
	int n = grid.length;
	int prev = grid[0][0];
	int r = 0;
	int c = 0;
	for(int i = 1; i < n*n; i++) {
	    c = i % n;
	    r = (i - c)/n;
	    if(grid[r][c] < prev)
		return false;
	    prev = grid[r][c];
	}
	return true;
    }

    private static int[][] readGrid(String fn) {
	int[][] grid = null;
	BufferedReader reader;
	try {
	    reader = new BufferedReader(new FileReader(fn));
	    String line = reader.readLine();
	    if(line != null)
		c1 = Integer.parseInt(line);
	    line = reader.readLine();
	    if(line != null)
		c2 = Integer.parseInt(line);
	    line = reader.readLine();
	    if(line != null)
		c3 = Integer.parseInt(line);
	    line = reader.readLine();
	    int n = 0;
	    if(line != null)
		n = Integer.parseInt(line);
	    line = reader.readLine();
	    int r = 0;
	    grid = new int[n][n];
	    while(line != null) {
		String[] str = line.split(" ");
		if(str.length < n)
		    break;
		for(int c = 0; c < n; c++)
		    grid[r][c] = Integer.parseInt(str[c]);
		r++;
		line = reader.readLine();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return grid;
    }
    private static int testAccuracy(int testNum, int ptsPoss, int n, int min, int max) {
	int[][] grid = new int[n][n];
	int[] array = new int[n*n];
	setVals(grid, array, min, max); 
	Arrays.sort(array);
	SortGrid.sort(grid);
	boolean passed = check(grid, array);
	printMsg(testNum, passed, grid, array, ptsPoss);
	if(passed)
	    return ptsPoss;
	return 0;
    }

    private static void printMsg(int testNum, boolean passed, int[][] grid, int[] array, int ptsPoss) {
	if(passed) {
	    System.out.println("Test " + testNum + " passed! (+" + ptsPoss + "pts)");
	} else {
	    System.out.println("Test " + testNum + " failed.");
	    printExpAct(array, grid);
	}
    }

    private static void printExpAct(int[] array, int[][] grid) {
	System.out.println("Expected: ");
	printArrAsGrid(array, grid.length);
	System.out.println("Actual: ");
	printGrid(grid);
    }

    private static void printArrAsGrid(int[] array, int n) {
	int r = 0;
	int c = 0;
	while(r < n) {
	    while(c < n) {
		System.out.print(array[n*r + c] + " ");
		c++;
	    }
	    c = 0;
	    r++;
	    System.out.println();
	}
    }
	
    private static boolean check(int[][] grid, int[] array) {
	int r = 0;
	int c = 0;
	int n = grid.length;
	while(r*n + c < array.length) {
	    if(grid[r][c] != array[r*n+c]) 
		return false;
	    c++;
	    if(c == n){
		c = 0;
		r++;
	    }
	}
	return true;
    }

    private static void printGrid(int[][] grid) {
	for(int i = 0; i < grid.length; i++) {
	    for(int j = 0; j < grid[0].length; j++) {
		System.out.print(grid[i][j] + " ");
	    }
	    System.out.print("\n");
	}
    }

    private static void setVals(int [][] grid, int[] array, int min, int max) {
	int n = grid.length;
	Random gen = new Random(System.currentTimeMillis());
	int r = 0;
	int c = 0;
	while(n*r + c < array.length) {
	    int num = gen.nextInt(max - min + 1) + min;
	    grid[r][c] = num;
	    array[n*r + c] = num;
	    c++;
	    if(c == n) {
		c = 0;
		r++;
	    }
	}
    }
}