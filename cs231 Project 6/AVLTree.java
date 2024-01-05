/*
 * Hunter Broughton
 * CS231A
 * 4/10/23
 * 
 * here is my extension class for an AVL Tree
 * 
 * 
 * an AVL tree is the equivalent of a binary search tree; however, the data structure 
 * prudces a tree that is always balanced
 * 
 * this is achieved from the further implementation of rotation methods, which rotates 
 * the node based structure of the tree to ensrue that the data structure is balanced 
 * 
 * please compile this file if you wish to use the avl tree in the final program: WordCounter.java
 * 
 * how to compile:
 * javac HashMap.java
 * 
 */


//import required librarires
import java.util.ArrayList;
import java.util.Comparator;

/*
 * the avl tree implements the MapSet interface, since we will be storing key, value pairs
 */
public class AVLTree<K, V> implements MapSet<K, V>{


    //private class to make a node, which will be used to create our AVLTree data structure 
    private static class Node<K, V> extends KeyValuePair<K, V>{
        Node<K, V> left, right;
        int height;

        public Node(K key, V value){
            super(key, value);
            left = null;
            right = null;
        }
    }

    /*
     * instnace fields for the avl tree: 
     * 
     * a reference to the root Node
     * 
     * an integer value for the size of the data structure 
     * 
     * and a comparator object to compare items within the data structure 
     */
    private Node<K, V> root;
    private int size;
    private Comparator<K> comparator;

    /*
     * high level constructor for the AVL Tree:
     * 
     * creates an AVL tree given a comparator object,
     * 
     * if the comparator is null, a compare method is created using the keys from the mapset inteface
     */
    public AVLTree(Comparator<K> comparator) {
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

    //constructs an AVLtree with a null comparator object
    public AVLTree(){
        this(null);
    }

    //returns the high of the avl tree
    //retuns negative 1 if there is nothing in the avl tree
    private int height(Node<K, V> node) {
        return (node == null) ? -1 : node.height;
    }
    

  
    /*
     * updates the height of the avl tree
     */
    private void updateHeight(Node<K, V> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }
    

    //returns the balance factor for a specific node in the avl tree
    private int getBalance(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return height(node.right) - height(node.left);
    }
    

    /*
     * this method takes in the root node of a subtree that needs to be rotated to the left
     * whats returned is the new root node of the rotated tree
     */
    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> node2 = node.right;
        node.right = node2.left;
        node2.left = node;
    
        updateHeight(node);
        updateHeight(node2);
    
        return node2;
    }
    
    /*
     * this method takes in the root node of a subtree that needs to be rotated to the right
     * whats returned is the new root node of the rotated tree
     */
    private Node<K, V> rightRotate(Node<K, V> node) {
        Node<K, V> node2 = node.left;
        node.left = node2.right;
        node2.right = node;
    
        updateHeight(node);
        updateHeight(node2);
    
        return node2;
    }


    /*
     * this method takes in the node of a subtree that potentially needs rebalancing.
     * the method will perform rotations based upon the balancing factor and the structure of the current subtrees
     */
    public Node<K, V> rebalance(Node<K, V> node) {
        updateHeight(node);
        int balance = getBalance(node);
    
        if (balance > 1) {
            if (height(node.right.right) < height(node.right.left)) {
                node.right = rightRotate(node.right);
            }
            node = leftRotate(node);
        } else if (balance < -1) {
            if (height(node.left.left) < height(node.left.right)) {
                node.left = leftRotate(node.left);
            }
            node = rightRotate(node);
        }
        return node;
    }
    

    
    

    //puts a key, value pair in the BST Map
    @Override
    public V put(K key, V value) {
        if (size == 0) {
            root = new Node<>(key, value);
            size++;
            return null;
        } else {
            root = put(key, value, root);
            return root.getValue();
        }
    }


    
    /*
     * puts a key value pair into the avl tree 
     */
    private Node<K, V> put(K key, V value, Node<K, V> curNode) {
        if (curNode == null) {
            size++;
            return new Node<>(key, value);
        }
    
        if (comparator.compare(key, curNode.getKey()) < 0) {
            curNode.left = put(key, value, curNode.left);
        } else if (comparator.compare(key, curNode.getKey()) > 0) {
            curNode.right = put(key, value, curNode.right);
        } else {
            curNode.setValue(value);
        }
    
        // Rebalance the tree after inserting the new node
        return rebalance(curNode);
    }
    
    

    /*
     * removes a given key, value pair from the avl tree
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
        if(toRemoveParent != null){
            rebalance(toRemoveParent);
        } 
        size--;

        root = rebalance(root);

        return value;

    }


    /*
     * method that handles the replacement of nodes within the avl tree
     * for when you remove from the avl tree
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

        if(toDeleteParent != null){
            rebalance(toDeleteParent);
        }

    }


    /*
     * checks if the data structure contains a given key 
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
     * gets the value of a given key in the data structure
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
     * returns an arraylist of the keys in the avl tree
     */
    public ArrayList<K> keySet() {
        ArrayList keys = new ArrayList();
        keySet(root, keys);
        return keys;
    }


    //recursive method that gathers the keys in the avl tree
    public void keySet(Node<K, V> cur, ArrayList<K> output){
        if(cur == null){
            return;
        }

        keySet(cur.left, output);
        output.add(cur.getKey());
        keySet(cur.right, output);
    }



     //returns an arraylist of the values in the avl tree
    public ArrayList<V> values() {
        ArrayList vals = new ArrayList();
        values(root, vals);
        return vals;
    }

    //recursive method that gathers the values in the avl tree
    public void values(Node<K, V> cur, ArrayList<V> output){
        if(cur == null){
            return;
        }

        values(cur.left, output);
        output.add(cur.getValue());
        values(cur.right, output);
    }


    //returns an arraylist of the keyvalue pairs in the avl tree
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList pairs = new ArrayList();
        entrySet(root, pairs);
        return pairs;
    }

    //recursive method that gathers the key value pairs in the avl tree
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



    //returns the size of the data structure
    @Override
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



    //clears the contents of the avl tree
    @Override
    public void clear() {
        this.size = 0;
        root = null;
    }


    //returns the max depth of the avl tree
    @Override
    public int maxDepth() {
        return maxDepth(root);
    }

    //recursive method that calculates the maxdepth of the avl tree 
    public int maxDepth(Node<K, V> cur){
        if (cur == null){
            return 0;
        }
        int depthLeft = maxDepth(cur.left);
        int depthRight = maxDepth(cur.right);
        return 1 + Math.max(depthLeft, depthRight);
    }

    //returns a string representation of the avl tree
    public String toString(){
        if(size == 0){
            return "Empty Tree";
        }
        else{
            return toString(root,0,"root");
        }
    }

    //builds the strin grepresentation to be returned if the AVL tree contains nodes
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