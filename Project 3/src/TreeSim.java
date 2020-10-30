import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;

public class TreeSim {
    private static ArrayList<Pair> list;
    private static BST<String, Integer> bst;
    private static RLRBT<String, Integer> rbt;
    private static double hSumBST = 0;
    private static double hSumRBT = 0;

    public static void main(String[] args) {
	String input = args[0];
	int count = Integer.parseInt(args[1]);
	loadList(input);
	worstCase();
	ArrayList<Pair> copy = null;
	for(int i = 0; i < count; i++) {
	    copy = copyList();
	    int[] heights = randomCase(copy);
	    hSumBST += heights[0];
	    hSumRBT += heights[1];
	}
	double avgBST = hSumBST/count;
	double avgRBT = hSumRBT/count;
	System.out.println("Average height BST: " + avgBST);
	System.out.println("Average height RBT: " + avgRBT);
    }

    private static int[] randomCase(ArrayList<Pair> list) {
	bst = new BST<String, Integer>();
	rbt = new RLRBT<String, Integer>();
	Random gen = new Random(System.currentTimeMillis());
	while(!list.isEmpty()) {
	    int i = gen.nextInt(list.size());
	    Pair p = list.remove(i);
	    bst.insert(p.key(), p.val());
	    rbt.insert(p.key(), p.val());
	}
	return new int[]{bst.height(), rbt.height()};
    }

    private static ArrayList<Pair> copyList() {
	ArrayList<Pair> copy = new ArrayList<Pair>();
	for(int i = 0; i < list.size(); i++)
	    copy.add(list.get(i));
	return copy;
    }

    private static void worstCase() {
	bst = new BST<String, Integer>();
	rbt = new RLRBT<String, Integer>();
	for(int i = 0; i < list.size(); i++) {
	    Pair p = list.get(i);
	    bst.insert(p.key(), p.val());
	    rbt.insert(p.key(), p.val());
	    System.out.println(i);
	}
	System.out.println("Worst case BST height: " + bst.height());
	System.out.println("Worst case RBT height: " + rbt.height());
	System.out.println("Worst case RBT black height: " + rbt.blackHeight());
    }

    private static void loadList(String fn) {
	BufferedReader br;
	list = new ArrayList<Pair>();
	try {
	    br = new BufferedReader(new FileReader(fn));
	    String line = br.readLine();
	    while(line != null) {
		String[] split = line.split(",");
		if(split.length < 3){
            line = br.readLine();
		    continue;
        }
		int i = 0;
		Pair p = new Pair(split[0], Integer.parseInt(split[2]));
		while(i < list.size() && 
		      p.compareTo(list.get(i)) > 0)
		    i++;
		list.add(i, p);		
		line = br.readLine();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}