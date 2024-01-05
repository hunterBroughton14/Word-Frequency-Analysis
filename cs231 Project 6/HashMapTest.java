/*
 * Hunter Broughton
 * CS231A
 * 4/9/2023
 * 
 * This is the HashMapTest.java file -
 * 
 * This class is designed to test the methods within HashMap.java
 * 
 * To run:
 * javac HashMapTest.java
 * java -ea HashMapTest
 */

import java.util.ArrayList;

public class HashMapTest {


    /*
     * main method to run the testing methods
     */
    public static void main(String[] args) {
        testSize();
        testClear();
        testMaxDepth();
        testToString();

        System.out.println("All tests passed!");
    }


    /*
     * tests the size() method from the HashMap.java file
     */
    private static void testSize() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        assert hashMap.size() == 0 : "testSize 1 failed";
        hashMap.put("apple", 1);
        assert hashMap.size() == 1 : "testSize 2 failed";
        hashMap.put("banana", 2);
        assert hashMap.size() == 2 : "testSize 3 failed";
        hashMap.remove("apple");
        assert hashMap.size() == 1 : "testSize 4 failed";
    }



    /*
     * tests the .clear() method from the HashMap.java file
     */
    private static void testClear() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("orange", 3);
        assert hashMap.size() == 3 : "testClear 1 failed";
        hashMap.clear();
        assert hashMap.size() == 0 : "testClear 2 failed";
        assert hashMap.entrySet().isEmpty() : "testClear 3 failed";
    }

    /*
     * tests the maxDepth() method from the HashMap.java file
     */
    private static void testMaxDepth() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        assert hashMap.maxDepth() == 0 : "testMaxDepth 1 failed";
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("orange", 3);
        assert hashMap.maxDepth() >= 1 : "testMaxDepth 2 failed";
    }


    /*
     * tests the toString() method from the HashMap.java file
     */
    private static void testToString() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("orange", 3);
        String result = hashMap.toString();
        assert result.contains("apple=1") : "testToString 1 failed";
        assert result.contains("banana=2") : "testToString 2 failed";
        assert result.contains("orange=3") : "testToString 3 failed";
    }
}
