/*
 * Hunter Broughton
 * CS231A
 * 4/15/23
 *
 * Test file for AVLTree.java
 * 
 * how to run:
 * javac AVLTreeTest.java
 * java -ea AVLTreeTest
 */

 public class AVLTreeTest {
    //call to the testing methdos
    public static void main(String[] args) {
        testPutAndRebalance();
        testRemoveAndRebalance();
        System.out.println("Tests Passed!");
    }

    //tests the act of putting items into the avl tree and ensures that the data structure is balanced
    public static void testPutAndRebalance() {
        AVLTree<String, Integer> tree = new AVLTree<>();

        tree.put("20", 20);
        tree.put("15", 15);
        tree.put("25", 25);
        tree.put("10", 10);
        tree.put("30", 30);
        tree.put("5", 5);
        tree.put("7", 7);

        int rootHeight = tree.maxDepth();
        assert rootHeight == 4 : "Error: Expected height 4, found " + rootHeight;

        tree.put("6", 6);

        rootHeight = tree.maxDepth();
        assert rootHeight == 4 : "Error: Expected height 4, found " + rootHeight;
    }


    //tests the act of removing from thea avl tree, and ensures that the data structure remains balanced
    public static void testRemoveAndRebalance() {
        AVLTree<String, Integer> tree = new AVLTree<>();

        tree.put("20", 20);
        tree.put("15", 15);
        tree.put("25", 25);
        tree.put("10", 10);
        tree.put("30", 30);
        tree.put("5", 5);
        tree.put("7", 7);
        tree.put("6", 6);

        tree.remove("30");

        int rootHeight = tree.maxDepth();
        assert rootHeight == 4 : "Error: Expected height 4, found " + rootHeight;

        tree.remove("15");        
        rootHeight = tree.maxDepth();
        assert rootHeight == 4 : "Error: Expected height 2, found " + rootHeight;
    }
}