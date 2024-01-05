/*
 * Hunter Broughton
 * CS231A
 * 4/1/2023
 * 
 * This is the BSTMap.java file -
 * This class implements a Binary Search tree for our implemented MapSet interface
 * 
 * In the context of the project, this proves to be an effective an efficient method 
 * of storing the key value pairs of words and their corresponding frequencies. 
 * 
 * Conceptually, our binary search tree will store the data in an ordered fashion,
 * but in the form of a tree data strcture, this allows for faster lookup and removal times
 * 
 * please ensure this file is compiled to run the final program: WordCounter.java
 * 
 * how to compil:
 * javac BSTMap.java
 */


//import libraries
import java.util.ArrayList;
import java.util.Comparator;



/*
 * Binary search tree of Key Value pairs implements the MapSet interface of Key Value pairs
 */
public class BSTMap<K, V> implements MapSet<K, V>{

    /*
     * main method used for initial, basic testing of the methods
     * in particular, the to string method was tested using this main method;
     * however, further testing is done in the BSTMapTest.java file
     */
    public static void main(String[] args) {
        BSTMap<String, Integer> myTree = new BSTMap<>();

        //testing put and toString()
        myTree.put("10", 10);
        myTree.put("30", 30);
        myTree.put("20", 20);
        myTree.put("40", 40);

        System.out.println(myTree.toString());

        myTree.remove("10");

        System.out.println("\n + \n " + myTree.toString());
    }

    /*
     * node class:
     * 
     * nodes keep track to their left and right children
     */
    private static class Node<K, V> extends KeyValuePair<K, V>{
        Node<K, V> left, right;

        public Node(K key, V value){
            super(key, value);
            left = null;
            right = null;
        }
    }

    //instance fields for BST Map:
    //A node for the root of the BST, and integer for the size, and a Comparator object ot compare key-value paris
    private Node<K, V> root;
    private int size;
    private Comparator<K> comparator;


    //BST Map Constructor - takes in a paramateter for the comparator object
    public BSTMap(Comparator<K> comparator) {
        size = 0;
        root = null;
        if (comparator != null) {
            this.comparator = comparator;
        } else {
            this.comparator = new Comparator<K>() {

                @Override
                public int compare(K o1, K o2) {
                    return ((Comparable<K>) o1).compareTo(o2);
                }

            };
        }
    }

    //calls the other constructor with null as the comparator
    public BSTMap(){
        this(null);
    }



    //puts a key, value pair in the BST Map
    @Override
    public V put(K key, V value) {
        if (size == 0) {
            root = new Node<>(key, value);
            size++;
            return null;
        } else {
            return put(key, value, root);
        }
    }


    //recursive method for put - compares the key value pair to various nodes in the current
    //state of the binary search tree, until the correct spot for placement is found
    private V put(K key, V value, Node<K, V> curNode) {
        if (comparator.compare(key, curNode.getKey()) < 0) {
            if (curNode.left == null) {
                curNode.left = new Node<>(key, value);
                size++;
                return null;
            } else {
                return put(key, value, curNode.left);
            }
        } else if (comparator.compare(key, curNode.getKey()) > 0) {
            if (curNode.right == null) {
                curNode.right = new Node<>(key, value);
                size++;
                return null;
            } else {
                return put(key, value, curNode.right);
            }
        } else {
            V oldVal = curNode.getValue();
            curNode.setValue(oldVal);
            return oldVal;
        }
    }


    /*
     * checks if the BST contains a given key
     */
    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        if(value == null){
            return false;
        }else{
            return true;
        }
    }

    /*
     * gets the value for a given key
     */
    @Override
    public V get(K key) {
        return get(key, root);
    }

    /*
     * recursive method for get - compares node values until the correct node is found, and that nodes
     * value is returned
     */
    private V get(K key, Node<K, V> cur){
        if (cur == null){
            return null;
        }
        if(comparator.compare(key, cur.getKey()) < 0){
            return get(key, cur.left);
        }else if(comparator.compare(key, cur.getKey()) > 0){
            return get(key, cur.right);
        }else{
            return cur.getValue();
        }

    }

    /*
     * removes a key value pair from the BST and returns the value of the pairing that is removed
     * returns null if the key value pairs is not found within the tree
     */
    public V remove(K key) {
        Node<K, V> toRemoveParent = null;
        Node<K, V> toRemove = root;

        while(toRemove != null){
            if(comparator.compare(key, toRemove.getKey()) < 0){
                toRemoveParent = toRemove;
                toRemove = toRemove.left;
            } else if(comparator.compare(key, toRemove.getKey()) > 0){
                toRemoveParent = toRemove;
                toRemove = toRemove.right;
            }else{
                break;
            }
        }

        if(toRemove == null){
            return null;
        }

        V value = toRemove.getValue();

        handleReplacement(toRemove, toRemoveParent);

        size--;

        return value;

    }


    /*
     * method that handles the replacement of nodes within the binary search tree
     * for when you remove from the binary search tree
     */
    public void handleReplacement(Node<K, V> toDelete, Node<K, V> toDeleteParent){
        Node<K, V> replacement;
        if(toDelete.left == null){
            replacement = toDelete.right;
        }else if(toDelete.right == null){
            replacement = toDelete.left;
        }else{
            Node<K, V> parent = toDelete;
            replacement = toDelete.right;
            while(replacement.left!= null){
                parent = replacement;
                replacement = replacement.left;
            }

            if(parent != toDelete){
                parent.left = replacement.right;
            }
            else{
                parent.right = replacement.right;
            }

            replacement.left = toDelete.left;
            replacement.right = toDelete.right;
        }

        if(toDeleteParent == null){
            root = replacement;
        }else if (toDeleteParent.left == toDelete){
            toDeleteParent.left = replacement;
        }else{
            toDeleteParent.right = replacement;
        }

    }

    
    /*
     * returns an arraylist of the keys in the binary search tree
     */
    public ArrayList<K> keySet() {
        ArrayList keys = new ArrayList();
        keySet(root, keys);
        return keys;
    }


    //recursive method that gathers the keys in the binary search tree
    public void keySet(Node<K, V> cur, ArrayList<K> output){
        if(cur == null){
            return;
        }

        keySet(cur.left, output);
        output.add(cur.getKey());
        keySet(cur.right, output);
    }

    //returns an arraylist of the values in the binary search tree
    public ArrayList<V> values() {
        ArrayList vals = new ArrayList();
        values(root, vals);
        return vals;
    }

    //recursive method that gathers the values in the binary search tree
    public void values(Node<K, V> cur, ArrayList<V> output){
        if(cur == null){
            return;
        }

        values(cur.left, output);
        output.add(cur.getValue());
        values(cur.right, output);
    }

    
    //returns an arraylist of the keyvalue pairs in the binary search tree
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList pairs = new ArrayList();
        entrySet(root, pairs);
        return pairs;
    }

    //recursive method that gathers the key value pairs in the binary search tree
    public void entrySet(Node<K, V> cur, ArrayList<MapSet.KeyValuePair<K, V>> output){
        if(cur == null){
            return;
        }

        entrySet(cur.left, output);
        K key = cur.getKey();
        V val = cur.getValue();
        output.add(new KeyValuePair<>(key, val));
        entrySet(cur.right, output);

    }

    //returns the size of the binary search tree
    public int size() {
        return size(root);
    }

    //recursive method that calculates the size of the tree
    public int size(Node<K, V> cur){
        if(cur == null){
            return 0;
        }
        int sizeLeft = size(cur.left);
        int sizeRight = size(cur.right);
        return 1 + sizeLeft + sizeRight;
    }

    //clears the entire tree
    public void clear() {
        this.size = 0;
        root = null;
    }

    
    //returns the maxdepth of the BST
    public int maxDepth() {
        return maxDepth(root);
    }

    //recursive method that calculates the maxdepth of the bst
    public int maxDepth(Node<K, V> cur){
        if (cur == null){
            return 0;
        }
        int depthLeft = maxDepth(cur.left);
        int depthRight = maxDepth(cur.right);
        return 1 + Math.max(depthLeft, depthRight);
    }

    //returns a string representation of the BST
    public String toString(){
        if(size == 0){
            return "Empty Tree";
        }
        else{
            return toString(root,0,"root");
        }
    }

    //builds the stringrepresentation to be returned if the BST contains nodes
    private String toString(Node<K, V> curNode, int depth, String direction) {
        if (curNode == null) {
            return "";
        }
    
        String myself = curNode.toString();
    
        String left = toString(curNode.left, depth + 1, "left");
        String right = toString(curNode.right, depth + 1, "right");
    
        String result = "";
        if (!right.isEmpty()) {
            result += right + '\n';
        }
        result += "  ".repeat(depth) + direction + ": " + myself;
        if (!left.isEmpty()) {
            result += '\n' + left;
        }
        return result;
    }
}
    
    

