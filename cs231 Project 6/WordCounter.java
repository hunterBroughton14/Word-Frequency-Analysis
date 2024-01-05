/*
 * Hunter Broughton
 * CS231A
 * 4/9/2023
 * 
 * This is the WordCounter.java file -
 * This class implements the WordCounter, which is used to collect data on the words and their frequencies 
 * from the Reddit Comment files
 * 
 * Without our datastrcutures: BST and HashMap, keeping track of all the words in the Reddit Comment files would 
 * be impossible due to the sheer number of words within them.
 * 
 * However, with the use of our data structures, we can collect data on the patterns of given words and their 
 * frequency of use on Reddit from year to year 
 * 
 * This file contains both all of the nessescary method for the WordCounter, and the exploration piece of the project.
 * 
 * Please comment or uncomment the given code in the main method to explore the reddit files however you wish
 * 
 * how to run the file:
 * java -Xmx4g WordCounter
 */

 //import nessescary libraries
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * WordCoutner class
 */
public class WordCounter{


    /*
     * The Main Method implements various parts of the exploration. 
     * 
     * Please follow the commented instructions on how to use the main method - 
     * uncomment the block of code you want to run, and leave the other blocks commented
     * 
     * 
     */
    public static void main(String[] args) throws IOException {



        /*
         * this code below writes to the file the run times and the max depth of the WordCounter with BST and Hashmap on various
         * reddit files, please uncomment this block and comment the rest of the main method to run, and reffer to runtimes.txt to analyze the results
         * 
         * for enhanced, more accurate data, run this simulation multiple times and record averages. 
         * 
         * 
         * Please Note: this exploration test will take considerable time to run since it creates a WordCounter and reads in reddit files multiple times, 
         * 
         * 
         * another note: since this code block goes through such an extensive process of reading in files and creating WordCounters, it needs to be ran as such:
         * java -Xmx8g WordCounter
         * 
         */

        // try(BufferedWriter bw = new BufferedWriter(new FileWriter("dataStructureData.txt"))){
        //     for(int i = 2008; i<=2015; i+= 2){
        //         System.out.println(i);
        //         WordCounter hashWordCounter = new WordCounter("HashMap");
        //         ArrayList<String> redditWords = hashWordCounter.readWords("reddit_comments_" + Integer.toString(i) + ".txt");
        //         bw.write("HASH, " + Integer.toString(i) + ", Run Time: " +  Double.toString(hashWordCounter.buildMap(redditWords)));
        //         int hashMaxDepth = hashWordCounter.myMapSet.maxDepth();
        //         bw.write(", Max Depth: " + Integer.toString(hashMaxDepth));
        //         bw.newLine();

        //         WordCounter bstWordCounter = new WordCounter("bst");
        //         ArrayList<String> redditWords2 = bstWordCounter.readWords("reddit_comments_" + Integer.toString(i) + ".txt");
        //         bw.write("BST, " + Integer.toString(i) + ", Run Time: " +  Double.toString(bstWordCounter.buildMap(redditWords2)));
        //         int bstMaxDepth = bstWordCounter.myMapSet.maxDepth();
        //         bw.write(", Max Depth: " + Integer.toString(bstMaxDepth));
        //         bw.newLine();
        //     }
        // } catch (IOException e){
        //     e.printStackTrace();
        // }


        

        /*
         * basic exploration test
         * 
         * creates a wordcounter with a gien data strcture and analyzes a given reddit file
         * 
         * please uncomment this block and comment the rest of the main method to run
         */

        WordCounter myWordCounter = new WordCounter("avl");
        ArrayList<String> redditWords = myWordCounter.readWords("reddit_comments_2011.txt");
        System.out.println(myWordCounter.buildMap(redditWords));
        myWordCounter.writeWordCount("reddit_comments_2011_written.txt");
        System.out.println(myWordCounter.myMapSet.maxDepth());



        
        /*
         * uses the exploration method to record data on the frequency of various political words over the years on reddit
         * please uncomment this block and comment the rest of the main method to run
         * 
         * this code block takes a considerable amount of time to run, since it must read in words from all the redditt files
         */

        // try(BufferedWriter bw = new BufferedWriter(new FileWriter("political_words.txt"))){
        //     for(int i = 2008; i <=2015; i++){
        //         exploration("HashMap", i, bw);
        //     }
        //  } catch (IOException e){
        //         e.printStackTrace();
        //     }



        }
    

    //instance fields for the MapSet and an integer storing the wordcount
    MapSet<String, Integer> myMapSet;
    int wordCount;

    /*
     * constructs the WordCounter based on which data structure is specified
     */
    public WordCounter(String data_structure){
        if(data_structure == "bst"){
            myMapSet = new BSTMap<>();
        }else if(data_structure == "HashMap") {
            myMapSet = new HashMap<>();
        }else if(data_structure == "avl"){
            myMapSet = new AVLTree<>();
        }
    }


    /*
     * This method implements part of the extension: analyzing the frequency of specific words.
     * 
     * For this project, i decided to track political words.
     * 
     * This method will take in the type of data structure, the year you want to analyze, and a BufferedWriter
     */
    public static void exploration(String mapType, int year, BufferedWriter bw) throws IOException{
            WordCounter myWordCounter = new WordCounter(mapType);
            ArrayList<String> redditWords = myWordCounter.readWords("reddit_comments_" + year + ".txt");
            System.out.println(myWordCounter.buildMap(redditWords));
            myWordCounter.removeCommonWords("commonWords.txt");
            myWordCounter.writeWordCount("reddit_comments_" + year + "_written.txt");
            //myWordCounter.getMostFrequentWords();
            bw.write(Integer.toString(year));
            bw.newLine();
            bw.write("\"Obama\" word count: " + myWordCounter.getCount("Obama"));
            bw.newLine();
            bw.write("\"Biden\" word count: " + myWordCounter.getCount("Biden"));
            bw.newLine();
            bw.write("\"Iraq\" word count: " + myWordCounter.getCount("Iraq"));
            bw.newLine();
            bw.write("\"Trump\" word count: " + myWordCounter.getCount("Trump"));
            bw.newLine();
            bw.write("\"Clinton\" word count: " + myWordCounter.getCount("Clinton"));
            bw.newLine();
            bw.write("\"Isis\" word count: " + myWordCounter.getCount("Isis"));
            bw.newLine();
            bw.write("\"Depression\" word count: " + myWordCounter.getCount("Depression"));
            bw.newLine();
            // System.out.println("\"Obama\" word count: " + myWordCounter.getCount("Obama"));
            // System.out.println("\"Biden\" word count: " + myWordCounter.getCount("Biden"));
            // System.out.println("\"Iraq\" word count: " + myWordCounter.getCount("Iraq"));
            // System.out.println("\"Trump\" word count: " + myWordCounter.getCount("Trump"));
            // System.out.println("\"Clinton\" word count: " + myWordCounter.getCount("Clinton"));
            // System.out.println("\"Isis\" word count: " + myWordCounter.getCount("Isis"));
        }

    

    /*
     * this method will read in the list of common words from kaggle, and then remove 
     * all of the common words from the data structure
     */
    public void removeCommonWords(String commonWordsFilename) throws IOException {
        // Read common words from the file
        List<String> commonWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(commonWordsFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                commonWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Remove common words from the map
        List<String> wordsToRemove = new ArrayList<>();
        for (MapSet.KeyValuePair<String, Integer> entry : myMapSet.entrySet()) {
            String word = entry.getKey();
            if (commonWords.contains(word.toLowerCase())) {
                wordsToRemove.add(word);
            }
        }
    
        for (String wordToRemove : wordsToRemove) {
            myMapSet.remove(wordToRemove);
        }
    }
    
    

    /*
     * this method will read in a reddit file, and add all of the words from the reddit
     * file to an ArrayList of Strings. 
     * 
     * This is accomplished using some handy regex, which notes a word as anything followed from a space. 
     */
    public ArrayList<String> readWords(String filename) throws IOException{
        ArrayList<String> words = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while((line = br.readLine()) != null){
            List<String> linesWords = Arrays.asList(line.split("\\s+"));
            words.addAll(linesWords);
            wordCount += linesWords.size();
        }
        br.close();
        return words;
    }



    /*
     * This is another method that can be used for exploration
     * 
     * This method writes to a file, highest_frequency_words.txt, the words that appear the most frequently in a given year
     */
    public List<MapSet.KeyValuePair<String, Integer>> getMostFrequentWords() throws IOException{
        ArrayList<MapSet.KeyValuePair<String, Integer>> sortedStrings = new ArrayList<>(myMapSet.entrySet());
        sortedStrings.sort((s1, s2) -> s2.getValue().compareTo(s1.getValue()));
        List<MapSet.KeyValuePair<String, Integer>> topWords = sortedStrings.subList(0, 150);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("highest_frequency_words.txt"))){
            for(MapSet.KeyValuePair<String, Integer> word : topWords){
                bw.write(word.toString());
                bw.newLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return topWords;
    }



    /*
     * this is the method that builds our data structure for the reddit comments
     * 
     * it takes in an ArrayList of word strings, and using that, will buildout either the BST Map or 
     * a hashmap, it also denotes the run time required to build such data structures. 
     */
    public double buildMap(ArrayList<String> words) throws FileNotFoundException, IOException{
        long startTime = System.currentTimeMillis();


        for(String word : words){
            if(myMapSet.containsKey(word)){

                
                int value = myMapSet.get(word);
                myMapSet.put(word, ++value);

            }else{
                myMapSet.put(word, 1);
            }
        }
        long endTime = System.currentTimeMillis();
        double runTime = (double) (endTime - startTime);
        return runTime;
    }

    //clears the MapSet
    public void clearMap(){
        myMapSet.clear();
    }

    //returns the total word count of the reddit file
    public int totalWordCount(){
        return wordCount;
    }

    //returns the number of words in the data structure, aka, the number of unique words, ignoring frequency 
    public int uniqueWordCount(){
        return myMapSet.size();
    }

    //returns the number of times a word is in the reddit file
    public int getCount(String word){
        if(myMapSet.get(word)== null){
            return 0;
        }else{
            return myMapSet.get(word);
        }

    }

    //returns the rate at which a word is used in the reddit file
    public double getFrequency(String word){
        double frequency = getCount(word) / wordCount;
        return frequency;
    }



    /*
     * writes a wordcount file given the current set of words in the data structure. 
     * 
     * first line denotes the total number of words,
     * and the following lines contian a word and it's frequency 
     */
    public boolean writeWordCount(String filename) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))){
            bw.write(Integer.toString(wordCount));
            bw.newLine();

            for(MapSet.KeyValuePair<String, Integer> pairing : myMapSet.entrySet()){
                bw.write(pairing.getKey() + " " + pairing.getValue());
                bw.newLine();
            }

            bw.close();

            return true;

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    /*
     * reads in a word count fie given the filename 
     * 
     * the method clears the current map and inputs the data from the file 
     * into the map data structure. 
     */
    public boolean readWordCount(String filename) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            clearMap();
    
            String line;
            // Read the total word count from the first line
            wordCount = Integer.parseInt(br.readLine());
    
            // Read the subsequent lines containing words and their counts
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                if (words.length == 2) {
                    String word = words[0];
                    int count = Integer.parseInt(words[1]);
                    myMapSet.put(word, count);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}

