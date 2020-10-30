import java.io.*;
public class BSTTest {
    public static void main(String[] args) {
	int score = 0;
	for(int i = 1; i <= 4; i++) {
	    printBegin(i);
	    if(test(i))
		score += 5;
	    System.out.println();
	}
	
	System.out.println("Total: " + score);
    }

    private static void printBegin(int testNum) {
	System.out.println("***** Begin Test " + testNum + " *****");
    }

    private static boolean test(int testNum) {
	System.out.println("Testing BST...");
	String iFile = "test_" + testNum + "_input.txt";
	String eFile = "test_" + testNum + "_expected.txt";
	String actual = "";
	String expected = "";
	BST<String, Integer> bst = new BST<String, Integer>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(iFile));
            String line = br.readLine();
            while(line != null && !line.equals("")) {
                String[] cmd = line.split(" ");
		if(cmd[0].equals("i")) {
		    bst.insert(cmd[1], Integer.parseInt(cmd[2]));
		} else if(cmd[0].equals("g")) {
		    Integer val = bst.get(cmd[1]);
		    if(val != null)
			actual += "(" + cmd[1] + ", " + val + ")\n";
		    else 
			actual += "null\n";
		} else if(cmd[0].equals("s")) {
		    actual += "tree size = " + bst.size() + "\n";
		} else if(cmd[0].equals("h")) {
		    if(cmd.length == 2)
			actual += "height " + cmd[1] + " = " + bst.height(cmd[1]) + "\n";
		    else 
			actual += "tree height = " + bst.height() + "\n";
		} else if(cmd[0].equals("p")) {
		    actual += bst.toString();
		}
		line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

	try {
	    br = new BufferedReader(new FileReader(eFile));
	    String line = br.readLine();
	    while(line != null && !line.equals("")) {
		expected += line + "\n";
		line = br.readLine();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return printResults(actual, expected, testNum);
    }

    private static boolean printResults(String actual, String expected, int testNum) {
	if(actual.equals(expected)) {
	    System.out.println("Test " + testNum + " passed!");
	    return true;
	} 
	System.out.println("Test " + testNum + " failed.");
	System.out.println("Expected: " + expected);
	System.out.println("Actual: " + actual);
	return false;
    }
}
	