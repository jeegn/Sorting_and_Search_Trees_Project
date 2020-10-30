public class RLRBT<Key extends Comparable<Key>, Value> {
    private Node root;
    private int N;

    /* CONSTRUCTOR */
    public RLRBT() {
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
            node.isRed = false;
            this.root = node;
            this.root.right = this.root.left = null;
            N++;
            return;
        }
        //parent = this.root;
        if (get(key) != null) {
            Node updateLocation = getLocation(root, key);
            updateLocation.val = node.val;
            return;
        }
        root = insertToLocation(this.root, node);
        root.isRed = false;
        N++;
        setHeight(this.root);
    }

    /***
     *get the value associated with the given key;
     *return null if key doesn't exist
     */
    public Value get(Key key) {
        //COPY FROM BST
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
     *return String representation of tree                                      
     *level by level                                                            
     */
    public String toString() {
        String ret = "Level 0: ";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));
        while (!queue.isEmpty()) {
            x = queue.dequeue();
            Node n = x.node;
            if (x.depth > level) {
                level++;
                ret += "\nLevel " + level + ": ";
            }
            if (n != null) {
                ret += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            } else
                ret += "|null|";
        }
        ret += "\n";
        return ret;
    }


    /***
     *return the black height of the tree
     */
    public int blackHeight() {
        //TO BE IMPLEMENTED
        if (isEmpty())
            return -1;
        int blackHeight = 0;
        Node temp = root;
        while (((temp.right != null) && (!temp.right.isRed)) ||
                ((temp.left != null) && (!temp.left.isRed))){
            if (!temp.isRed)
                blackHeight++;
            if (temp.right != null)
                temp = temp.right;
            else
                temp = temp.left;

        }
        return blackHeight;
    }

    /* PRIVATE METHODS */
    private Node insertToLocation(Node root, Node toInsert) {
        if (root == null)
            return toInsert;
        if (root.key.compareTo(toInsert.key) > 0) {
            root.left = insertToLocation(root.left, toInsert);
        } else if (root.key.compareTo(toInsert.key) < 0) {
            root.right = insertToLocation(root.right, toInsert);
        } else
            return root;
        if ((!root.isRed) && (root.left != null) && (root.left.isRed)) {
            if ((root.right != null) && (root.right.isRed))
                colorFlip(root);
            else
                root = rotateRight(root);
        }
        if ((root.right != null) && (root.right.right != null)
                && (root.right.isRed) && (root.right.right.isRed)) {
            root = rotateLeft(root);
            colorFlip(root);
        }
        if ((root.right != null) && (root.right.left != null) &&
                (root.right.isRed) && (root.right.left.isRed)) {
            root.right = rotateRight(root.right);
            root = rotateLeft(root);
            colorFlip(root);
        }
        return root;
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
        if (root == null)
            return null;
        if (root.key.compareTo(key) == 0) {
            return root;
        } else if (root.key.compareTo(key) < 0) {
            if (root.right == null)
                return null;
            return getLocation(root.right, key);
        } else {//if (root.key.compareTo(key) < 0) {
            if (root.left == null)
                return null;
            return getLocation(root.left, key);
        }
    }

    /***
     *swap colors of two Nodes
     */
    private void swapColors(Node x, Node y) {
        if (x.isRed == y.isRed)
            return;
        boolean temp = x.isRed;
        x.isRed = y.isRed;
        y.isRed = temp;
    }

    /***
     *rotate a link to the right
     */
    private Node rotateRight(Node x) {
        Node temp = x.left;
        x.left = temp.right;
        temp.right = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.left), x.height) + 1;
        return temp;
    }

    /***
     *rotate a link to the left
     */
    private Node rotateLeft(Node x) {
        Node temp = x.right;
        x.right = temp.left;
        temp.left = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.right), x.height) + 1;
        return temp;
    }

    /***
     *color flip
     */
    private Node colorFlip(Node x) {
        if (x.left == null || x.right == null)
            return x;
        if (x.left.isRed == x.right.isRed) {
            x.left.flip();
            x.right.flip();
            x.flip();
        }
        return x;
    }

    /***
     *return the neight of Node x
     *or -1 if x is null
     */
    private int height(Node x) {
        if (x == null)
            return -1;
        return x.height;
    }

    /* NODE CLASS */
    public class Node {
        Key key;
        Value val;
        Node left, right;
        int height;
        boolean isRed;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.isRed = true;
            this.left = null;
            this.right = null;
        }

        public String toString() {
            return "(" + key + ", " + val + "): "
                    + height + "; " + (this.isRed ? "R" : "B");
        }

        public void flip() {
            this.isRed = !this.isRed;
        }
    }

    /* PAIR CLASS */
    public class Pair {
        Node node;
        int depth;

        public Pair(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}    