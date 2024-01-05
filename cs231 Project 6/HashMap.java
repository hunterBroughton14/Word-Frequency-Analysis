/*
 * Hunter Broughton
 * CS231A
 * 4/9/2023
 * 
 * This is the HashMap.java file -
 * This class implements a HashMap for our implemented MapSet interface
 * 
 * In the context of the project, this proves to be an effective an efficient method 
 * of storing the key value pairs of words and their corresponding frequencies. 
 * 
 * Conceptually, our HashMap will store the words in buckets, corresponding to the order of their keys,
 * 
 * this use of buckets in turn allows for efficient run times for getting values from the data structure
 * and removing values
 * 
 * please ensure this file is compiled to run the final program: WordCounter.java
 * 
 * how to compile:
 * javac HashMap.java
 */



//import the arraylist library 
import java.util.ArrayList;



/*
 * hashmap class implements the map set interface, with use of Key, Value Pairs
 */
public class HashMap<K, V> implements MapSet<K, V> {

    /*
     * main method used for intiial testing of the methods within the class,
     * please refer to the HashMapTest.java file for further testing
     */
    public static void main(String[] args) {
        HashMap myHasher = new HashMap();
        myHasher.put(10, 10);
        myHasher.put(9, 9);
        myHasher.remove(10);
        System.out.println(myHasher.entrySet());
        System.out.println(myHasher);   
    }
    


    /*
     * Node Class - node contains references to the next value in a bucket
     */
    private static class Node<K, V> extends KeyValuePair<K, V> {

        Node<K, V> next;

        public Node(K key, V value, Node<K, V> next){
            super(key, value);
            this.next = next;
        }
    }

    
    /*
     * instance fields: an array of Key Value Pair nodes, buckets
     * an integer value to store the size and a double value to store the max load factor, which represents 
     * the percentage of values a HashMap will hold relative to it's size
     */
    private Node<K, V>[] buckets;
    private int size;
    private double maxLoadFactor;

    

    //base constructor, calls higher level constructor with an initial capacity of 16
    public HashMap(){
        this(16);
    }

    //secon constructor, calls highes level constrcutor with a maxload factor of .75
    public HashMap(int initialCapacity){
        this(initialCapacity, .75);
    }

    //constructor that builds a hashmap with a specified intiial capacity and a specified maxLoadFactor
    public HashMap(int initialCapacity, double maxLoadFactor){
        this.buckets = (Node<K, V>[]) new Node[initialCapacity];
        this.size = 0;
        this.maxLoadFactor = maxLoadFactor;
        
    }

    //returns the capacity of the hashmap
    private int capacity(){
        return buckets.length;
    }

    //returns the hash value for a given key
    private int hash(K key){
        return Math.abs(key.hashCode() % capacity());
    }

   
    //puts a key value pair into the hash map and returns the value 
    //if the key pair is already in the hashmap, it will return the old value but replace it with the new value 
    public V put(K key, V value) {
        int index = hash(key);

        if(buckets[index] == null){
            buckets[index] = new Node<K, V>(key, value, null);
        }else{
            for(Node<K, V> curNode = buckets[index]; curNode!= null; curNode = curNode.next){
                if(curNode.getKey().equals(key)){
                    V oldVal = curNode.getValue();
                    curNode.setValue(value);
                    return oldVal;
                }
            }
            buckets[index] = new Node <K, V> (key, value, buckets[index]);
        }

        size++;
        if(size > capacity() * maxLoadFactor){
            resize(capacity() * 2);
        }
        return null;
    }


    /*
     * resizes the HashMap to a newCapacity 
     */
    private void resize(int newCapacity){
        Node<K, V>[] myBuckets = buckets;
        buckets = (Node<K, V>[]) new Node[newCapacity];
        size = 0;
        for(Node<K, V> curNode : myBuckets){

            while(curNode != null){
                put(curNode.getKey(), curNode.getValue());
                curNode = curNode.next;
            }
        }
    }

    /*
     * checks to see if the hashmap contains a given key
     */
    @Override
    public boolean containsKey(K key) {
        int index = hash(key);

        if(buckets[index] == null){
            return false;
        }else{
            for(Node<K, V> curNode = buckets[index]; curNode!= null; curNode = curNode.next){
                if(curNode.getKey().equals(key)){
                    return true;
                }
        }
        return false;
    }
    }

    /*
     * gets the value of a given key in the hashmap, returns null if the hashmap doesnt contain the key
     */
    @Override
    public V get(K key) {
        int index = hash(key);

        if(buckets[index] == null){
            return null;
        }else{
            for(Node<K, V> curNode = buckets[index]; curNode!= null; curNode = curNode.next){
                if(curNode.getKey().equals(key)){
                    return curNode.getValue();
                }
        }
        return null;
    }
    }

    /*
     * removes a key value pair from the hashmap and returns the value 
     * 
     * if the key is not found, null is returned
     */
    @Override
    public V remove(K key) {
        int index = hash(key);

        if(buckets[index] == null){
            return null;
        }else{
            Node <K, V> previousNode = null;
            Node<K, V> currNode = buckets[index];
            
            while(currNode != null){
                if(currNode.getKey().equals(key)){
                    if(previousNode == null){
                        buckets[index] = currNode.next;
                    }else{
                        previousNode.next = currNode.next;
                    }
                    size--;
                    if(size < .25 * maxLoadFactor * capacity()){
                        resize(capacity() / 2);
                    }
                    return currNode.getValue();
                }
                previousNode = currNode;
                currNode = currNode.next;
            }
        
        return null;
        }
    }

    /*
     * returns an arraylist of the keys in the hashMap
     */
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> keys = new ArrayList();
        for(Node<K, V> curNode : buckets){

            while(curNode != null){
                keys.add(curNode.getKey());
                curNode = curNode.next;
            }
        }
        return keys;
    }

    /*
     * returns an arrayList of the values in the hashMap
     */
    @Override
    public ArrayList<V> values() {
        ArrayList<V> vals = new ArrayList();
        for(Node<K, V> curNode : buckets){

            while(curNode != null){
                vals.add(curNode.getValue());
                curNode = curNode.next;
            }
        }
        return vals;
    }

    /*
     * returns an arrayList of the key value pairs in the HashMap
     */
    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> myList = new ArrayList();
        if(buckets == null){
            return myList;
        }

        for(Node<K, V> curNode : buckets){
            
            while(curNode!= null){
                myList.add(new KeyValuePair<>(curNode.getKey(), curNode.getValue()));
                curNode = curNode.next;
            }
        }
        return myList;
    }

    //returns the size of the hashmap
    @Override
    public int size() {
        return size;
    }

    /*
     * clears the contents of the hashmap
     */
    @Override
    public void clear() {
        size = 0;
        buckets = null;
    }

    /*
     * returns the maxDepth of the hashMap - aka, the length of the longest bucket
     */
    @Override
    public int maxDepth() {
        int maxDepth = 0;
        for(Node<K, V> curNode : buckets){
            int bucketDepth = 0;
            while(curNode!= null){
                bucketDepth++;
                curNode = curNode.next;
            }
            if(bucketDepth > maxDepth){
                maxDepth = bucketDepth;
            }
        }
        return maxDepth;
    }

    /*
     * returns a string representation of the Hashmap
     */
    public String toString(){
        String result = "{\n";
        boolean isFirst = true;
        int bucketCounter = 1;
        for(Node<K, V> curNode : buckets){
            result+= "Bucket " + bucketCounter + ":  ";
            while (curNode != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    result += ", ";
                }
                result += curNode.getKey() + "=" + curNode.getValue();
                curNode = curNode.next;
        }
        result += "\n";
        isFirst = true;
        bucketCounter++;
    }

    result += "}";
    return result;

    }

}
