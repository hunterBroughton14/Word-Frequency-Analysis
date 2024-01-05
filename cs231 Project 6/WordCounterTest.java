/*
 * Hunter Broughton
 * CS231A
 * 4/9/2023
 * 
 * This is the WordCounterTest.java file -
 * 
 * This class is designed to test the methods within WordCounter.java
 * 
 * To run:
 * javac WordCounterTest.java
 * java -ea WordCounterTest
 */


//import statements 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordCounterTest {


    /*
     * main method runs the testing methods
     */
    public static void main(String[] args) throws IOException {
        testReadWords();
        testBuildMap();
        testClearMap();
        testTotalWordCount();
        testUniqueWordCount();
        testGetCount();
        testWriteWordCount();
        testReadWordCount();
        System.out.println("all tests passed!");
    }

    /*
     * method that tests the readWords method from the WordCounter.java file
     */
    public static void testReadWords() throws IOException {
        
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> expectedWords = new ArrayList<>();
        expectedWords.add("hello");
        expectedWords.add("world");
        expectedWords.add("this");
        expectedWords.add("is");
        expectedWords.add("a");
        expectedWords.add("test");

        ArrayList<String> actualWords = myWordCounter.readWords("test.txt");

        assert expectedWords.size() == actualWords.size() : "Expected size: " + expectedWords.size() + " but was: " + actualWords.size();
        for (int i = 0; i < expectedWords.size(); i++) {
            assert expectedWords.get(i).equals(actualWords.get(i)) : "Expected word: " + expectedWords.get(i) + " but was: " + actualWords.get(i);
        }
    }


    /*
     * tests the buildMap() method from the WordCounter class,
     * 
     * bst used right now, but use any data structure you like
     */
    public static void testBuildMap() throws FileNotFoundException, IOException {
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = new ArrayList<>();
        words.add("this");
        words.add("is");
        words.add("a");
        words.add("test");

        
        double runTime = myWordCounter.buildMap(words);

        assert runTime >= 0 : "Negative runtime: " + runTime;
    }


    /*
     * tests the clearMap() method from the wordCounter.java file
     */
    public static void testClearMap() throws FileNotFoundException, IOException{
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = new ArrayList<>();
        words.add("this");
        words.add("is");
        words.add("a");
        words.add("test");

        myWordCounter.buildMap(words);
        myWordCounter.clearMap();
        assert myWordCounter.myMapSet.size() == 0 : "Clear is not working properly";
    }


    /*
     * tests the TotalWordCount() method from the WordCounter.java file
     */
    public static void testTotalWordCount() throws FileNotFoundException, IOException{
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = myWordCounter.readWords("test.txt");
        myWordCounter.buildMap(words);
        assert myWordCounter.totalWordCount() == 6 : "error in getting total word count";
    }



    /*
     * tests the uniqueWordCount() method from the WordCounter.java file
     */
    public static void testUniqueWordCount() throws IOException{
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = myWordCounter.readWords("test.txt");
        myWordCounter.buildMap(words);
        assert myWordCounter.uniqueWordCount() == 6 : "error in getting total word count";
    }


    /*
     * tests the getCount() method from the WordCounter.java file
     */
    public static void testGetCount() throws IOException{
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = myWordCounter.readWords("test.txt");
        myWordCounter.buildMap(words);
        assert myWordCounter.getCount("wassup") == 0 : "error: thinks a random word is in the file";
        assert myWordCounter.getCount("test") == 1 : "error: thinks the word test is not in the file";
    }


    /*
     * tests the writeWordCount() method from the WordCounter.java file
     */
    public static void testWriteWordCount() throws FileNotFoundException, IOException{
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = myWordCounter.readWords("test.txt");
        myWordCounter.buildMap(words);
        boolean result = myWordCounter.writeWordCount("test2.txt");
        assert result : "writeWordCount should return true on successful write";


        List<String> lines = Files.readAllLines(Paths.get("test2.txt"));
        assert lines.size() == 7 : "The file should have 3 lines";
        assert lines.get(0).equals("6") : "The first line should have the total word count";
        assert lines.contains("hello 1") : "The file should contain the entry 'hello 3'";
        assert lines.contains("test 1") : "The file should contain the entry 'world 2'";
    }


    /*
     * tests the readWordCount() method from the wordCounter.java file
     */
    public static void testReadWordCount() throws FileNotFoundException, IOException{
        WordCounter myWordCounter = new WordCounter("bst");
        ArrayList<String> words = myWordCounter.readWords("test.txt");
        myWordCounter.buildMap(words);
        boolean result = myWordCounter.readWordCount("test2.txt");
        assert result : "readWordCount should return true on a successful read";

        assert myWordCounter.myMapSet.get("test") == 1 : "the word test should be in the MapSet after reading the file"; 
    }


}
