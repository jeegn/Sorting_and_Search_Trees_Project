public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    private int N;
    private int height;

    /* CONSTRUCTOR */
    public BST() {
	this.root = null;
	this.N = 0;
    }

    /* PUBLIC METHODS */

    /***
     *insert a new (key, val) into tree
     *or replace value of existing key
     */
    public void insert(Key key, Value val) {
	//TO BE IMPLEMENTED
		Node node = new Node(key, val);
		node.height = 0;
		if (isEmpty()) {
			root = node;
			root.right = root.left = null;
			N++;
			return;
		}
		if (get(key) != null) {
			Node updateLocation = getLocation(root, key);
			updateLocation.val = node.val;
			return;
		}
		root = insertToLocation(root, node);
		N++;
		setHeight(root);
    }
    
    /***
     *get the value associated with the given key;
     *return null if key doesn't exist
     */
    public Value get(Key key) {
	//TO BE IMPLEMENTED
		Node node = getLocation(root, key);
		if (node != null) {
			return node.val;
		}
		return null;
    }

    /***
     *return true if the tree
     *is empty and false 
     *otherwise
     */
    public boolean isEmpty() {
	return root == null;
    }

    /***
     *return the number of Nodes
     *in the tree
     */
    public int size() {
	return N;
    }

    /***
     *returns the height of the tree
     */
    public int height() {
	return height(root);
    }

    /***
     *returns the height of node 
     *with given key in the tree;
     *return -1 if the key does
     *not exist in the tree
     */
    public int height(Key key) {
	    //TO BE IMPLEMENTED
		Node node = getLocation(root, key);
		if (node == null)
			return -1;
		return node.height;
    }

    /***
     *return a String version of the tree
     *level by level
     */
    public String toString() {
	String str = "";
	Pair x = null;
	Queue<Pair> queue = new Queue<Pair>(N);
	int level = 0;
	queue.enqueue(new Pair(root, level));
	str += "Level 0: ";
	while(!queue.isEmpty()) {
	    x = queue.dequeue();
	    Node n = x.node;
	    if(x.depth > level) {
		level++;
		str += "\nLevel " + level + ": ";
	    }
	    if(n != null) {
		str += "|" + n.toString() + "|";
		queue.enqueue(new Pair(n.left, x.depth + 1));
		queue.enqueue(new Pair(n.right, x.depth + 1));
	    } else  
		str += "|null|";
	}
	str += "\n";
	return str;
    }
		

    /* PRIVATE METHODS */
	private Node insertToLocation(Node root, Node toInsert) {
		if (root == null)
			return toInsert;
		if (root.key.compareTo(toInsert.key) > 0) {
			root.left = insertToLocation(root.left, toInsert);
		} else if (root.key.compareTo(toInsert.key) < 0) {
			root.right = insertToLocation(root.right, toInsert);
		}
		return root;
//		if (root.key.compareTo(toInsert.key) == 0) {
//			root.val = toInsert.val;
//			return root;
//		}
//		//Right Subtree
//		else if (root.key.compareTo(toInsert.key) < 0) {
//			if (root.right == null) {
//				//root.right.key = toInsert.key;
//				//root.right.val = toInsert.val;
//				root.right = toInsert;
//				root.right.right = root.right.left = null;
//				N++;
//				return root;
//			}
//			else {
//				return insertToLocation(root.right, toInsert);
//			}
//		}
//		//Left Subtree
//		else {//if (root.key.compareTo(toInsert.key) > 0) {
//			if (root.left == null) {
//				root.left = toInsert;
//				root.left.right = root.left.left = null;
//				N++;
//				return root;
//			}
//			else {
//				return insertToLocation(root.left, toInsert);
//			}
//		}
		//return root;
	}
	private void setHeight(Node root) {
		if (root == null)
			return;
		root.height = maxHeight(root) - 1;
		setHeight(root.left);
		setHeight(root.right);
	}
	private int maxHeight(Node node) {
		if (node == null)
			return 0;
		int leftHeight = maxHeight(node.left);
		int rightHeight = maxHeight(node.right);
		if (rightHeight > leftHeight)
			return (rightHeight + 1);
		else
			return (leftHeight + 1);
	}
	private Node getLocation(Node root, Key key) {
		if (root.key.compareTo(key) == 0) {
			return root;
		}
		else if (root.key.compareTo(key) < 0) {
			if (root.right == null)
				return null;
			return getLocation(root.right, key);
		}
		else {//if (root.key.compareTo(key) < 0) {
			if (root.left == null)
				return null;
			return getLocation(root.left, key);
		}
	}
    /***
     *return the height of x
     *or -1 if x is null
     */
    private int height(Node x) {
	if(x == null)
	    return -1;
	return x.height;
    }

    /* NODE CLASS */
    public class Node {
	Key key;
	Value val;
	Node left, right;
	int height;

	public Node(Key key, Value val) {
	    this.key = key;
	    this.val = val;
	}
	
	public String toString() {
	    return "(" + key + ", " + val + "): " + height;
	}
    }

    /* PAIR CLASS */
    public class Pair {
	Node  node;
	int depth;
	
	public Pair(Node node, int depth) {
	    this.node = node;
	    this.depth = depth;
	}
    }
}    