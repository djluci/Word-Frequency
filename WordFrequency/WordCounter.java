package WordFrequency;

/*
 * File Name:       WordCounter.java
 * Author:          Duilio Lucio
 * Project:         Word Frequency
 * Course:          CS231
 * Section:         B
 * Last Modified:   04/21/2023
 */

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class WordCounter {
    int wordCount; // total word count
    MapSet<String, Integer> map; // map, can be either HashMap or BST
    String dataStructure;
    String hashFunction;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public WordCounter(String data_structure) {
        dataStructure = data_structure;
        hashFunction = "default";
        if (Objects.equals(data_structure.trim().toLowerCase(), "hashmap")) {
            map = new HashMap<String, Integer>(hashFunction, 16);
        } else {
            map = new BSTMap<String, Integer>();
        }
        wordCount = 0;
    }

    public static ArrayList<String> readWords(String filename) {
        ArrayList<String> output = new ArrayList<>();
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);

            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = br.readLine();
            // start a while loop that loops while line isn't null
            while (line != null) {
                // split line into words. The regular expression can be interpreted
                // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                String[] words = line.split("[^a-zA-Z0-9']");
                for (String s : words) {
                    String word = s.replace("'", "").trim().toLowerCase();
                    // Might want to check for a word of length 0 and not process it
                    // Write code to update the map
                    if (word.length() != 0) {
                        output.add(word);
                    }
                }
                // assign to line the result of calling the readLine method of your BufferedReader object.
                line = br.readLine();
            }
            // call the close method of the BufferedReader
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
        return output;
    }

    public double buildMap(ArrayList<String> words) {
        double startTime = System.nanoTime();
        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
            wordCount++;
        }
        double endTime = System.nanoTime();
        return (double) (endTime - startTime) * 1e-6;
    }

    public void clearMap() {
        if (Objects.equals(dataStructure.trim().toLowerCase(), "hashmap")) {
            map = new HashMap<String, Integer>(hashFunction, 16);
        } else {
            map = new BSTMap<String, Integer>();
        }
        wordCount = 0;
    }

    /**
     * getter for total word count
     *
     * @return int, the total word count
     */
    public int getTotalWordCount() {
        return wordCount;
    }

    /**
     * getter function for the unique word count
     *
     * @return, int, the size of the map which represents the count of unique words.
     */
    public int getUniqueWordCount() {
        return map.size();
    }

    /**
     * get the total count for a certain word in the BST or HashMap
     *
     * @param word, String, the word to be searched
     * @return int, the total count
     */
    public int getCount(String word) {
        return map.get(word);
    }

    /**
     * getter, frequency of a certain word
     *
     * @param word, the word to be searched
     * @return int, the frequency
     */
    public double getFrequency(String word) {
        return ((double) getCount(word)) / ((double) getTotalWordCount());
    }


    /**
     * Writes the contents of the BSTMap or MapSet.
     * The first line of the file contains the total number of words.
     * Each subsequent line contains a word and its frequency.
     *
     * @param filename, String -> the output filename
     */
    public void writeWordCountFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write("totalWordCount : " + wordCount + "\n");
        ArrayList<MapSet.KeyValuePair<String, Integer>> kvps = map.entrySet();
        for (MapSet.KeyValuePair<String, Integer> kvp : kvps) {
            fw.write("" + kvp.getKey() + " " + kvp.getValue() + "\n");
        }
        fw.close();
    }

    /**
     * reads the contents of a word count file and reconstructs
     * the fields of the WordCount object, including the MapSet (BST or HashMap).
     *
     * @param filename, String, the filename to be loaded
     */
    public void readWordCountFile(String filename) throws FileNotFoundException {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);

            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = br.readLine();
            String[] words = line.split(" : ");
            wordCount = Integer.parseInt(words[1]);
            line = br.readLine();
            // start a while loop that loops while line isn't null
            while (line != null) {
                // split line into words.
                words = line.split(" ");
                map.put(words[0], Integer.parseInt(words[1]));
                // assign to line the result of calling the readLine method of your BufferedReader object.
                line = br.readLine();
            }
            // call the close method of the BufferedReader
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
    }

    public long getCollisionNumber() {
        if (map instanceof HashMap) {
            return ((HashMap<String, Integer>) map).countCollisions();
        } else {
            return 0;
        }
    }

    private static double getAverageFromArray(ArrayList<Double> arr) {
        return (double) arr.stream().sorted().skip(1).limit(3).reduce(0., Double::sum) / 3.;
    }

    private static void analyzeFiles(String[] filenames, boolean writeResults) throws IOException {
        ArrayList<ArrayList<Double>> hashMapOutput = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> bstOutput = new ArrayList<ArrayList<Double>>();

        for (String dataStructure : new String[]{"HashMap", "BST"}) {

            WordCounter wc = new WordCounter(dataStructure);
            System.out.println("Data Structure: " + ANSI_RED + "| " + dataStructure + " |" + ANSI_RESET);

            for (String filename : filenames) {
                if (filename.endsWith("txt")) {
                    ArrayList<Double> timeElapsedArr = new ArrayList<>();
                    ArrayList<Double> totalWordsArr = new ArrayList<>();
                    ArrayList<Double> uniqueWordsArr = new ArrayList<>();
                    ArrayList<Double> collisionsArr = new ArrayList<>();
                    ArrayList<Double> filenameOutput = new ArrayList<Double>();

                    for (int i = 0; i < 5; i++) {

                        ArrayList<String> words = wc.readWords(filename);
                        double timeElapsed = wc.buildMap(words);

                        timeElapsedArr.add(timeElapsed);
                        totalWordsArr.add((double) wc.getTotalWordCount());
                        uniqueWordsArr.add((double) wc.getUniqueWordCount());
                        collisionsArr.add((double) wc.getCollisionNumber());

                        wc.clearMap();
                    }
                    double timeElapsed = getAverageFromArray(timeElapsedArr);
                    double totalWordCount = (int) getAverageFromArray(totalWordsArr);
                    double uniqueWordCount = (int) getAverageFromArray(uniqueWordsArr);
                    double collisionNumber = getAverageFromArray(collisionsArr);
                    filenameOutput.add(timeElapsed);
                    filenameOutput.add(totalWordCount);
                    filenameOutput.add(uniqueWordCount);
                    filenameOutput.add(collisionNumber);

                    if (dataStructure.equals("HashMap")) {
                        hashMapOutput.add(filenameOutput);
                    } else {
                        bstOutput.add(filenameOutput);
                    }

                    System.out.println(" └-> Filename: \"" + ANSI_CYAN + "" + filename + "" + ANSI_RESET + "\":");
                    System.out.println("     ├-> " + ANSI_GREEN + "Processing Time (ms)" + ANSI_RESET + "\t | \t\t" + ANSI_BLUE + timeElapsed + "" + ANSI_RESET);
                    System.out.println("     ├-> " + ANSI_GREEN + "Total Word Count" + ANSI_RESET + "\t\t | \t\t" + ANSI_YELLOW + totalWordCount + "" + ANSI_RESET);
                    System.out.println("     ├-> " + ANSI_GREEN + "Unique Word Count" + ANSI_RESET + "\t\t | \t\t" + ANSI_PURPLE + uniqueWordCount + "" + ANSI_RESET);
                    System.out.println("     └-> " + ANSI_GREEN + "Collision Count" + ANSI_RESET + "\t\t | \t\t" + ANSI_WHITE + collisionNumber + "" + ANSI_RESET);
                } else {
                    System.out.println("Invalid argument: \"" + filename + "\"");
                }
            }
        }

        if (writeResults) {
            FileWriter fw = new FileWriter("./output.csv");
            fw.write("Filename,HashMap Processing Time (ms),HashMap Total Word Count,HashMap Unique Word Count, HashMap Collision Count,BST Processing Time (ms),BST Total Word Count,BST Unique Word Count\n");
            for (int i = 0; i < filenames.length; i++) {
                fw.write("" + filenames[i] + ","
                        + hashMapOutput.get(i).get(0) + ","
                        + hashMapOutput.get(i).get(1) + ","
                        + hashMapOutput.get(i).get(2) + ","
                        + hashMapOutput.get(i).get(3) + ","
                        + bstOutput.get(i).get(0) + ","
                        + bstOutput.get(i).get(1) + ","
                        + bstOutput.get(i).get(2) + "\n");
            }
            fw.close();
        }

    }

    public HashMap<String, Integer> buildAndCreateMap(ArrayList<String> words) {
        HashMap<String, Integer> myMap = new HashMap<String, Integer>("default", 16);
        for (String word : words) {
            if (myMap.containsKey(word)) {
                myMap.put(word, myMap.get(word) + 1);
            } else {
                myMap.put(word, 1);
            }
        }
        return myMap;
    }

    private double parallelizedBuild(String filename, int jobs) throws InterruptedException, ExecutionException {
        ArrayList<String> words = readWords(filename);
        ArrayList<ArrayList<String>> wordPartitions = new ArrayList<>();
        for (int i = 0; i < jobs; i++) {
            wordPartitions.add(new ArrayList<String>(words.subList((int) words.size() / jobs * i, (int) words.size() / jobs * (i + 1))));
        }

        double startTime = System.nanoTime();

        ExecutorService EXEC = Executors.newCachedThreadPool();
        ArrayList<Callable<HashMap<String, Integer>>> tasks = new ArrayList<Callable<HashMap<String, Integer>>>();
        for (final ArrayList<String> wordsPartition : wordPartitions) {
            Callable<HashMap<String, Integer>> c = new Callable<HashMap<String, Integer>>() {
                @Override
                public HashMap<String, Integer> call() throws Exception {
                    return buildAndCreateMap(wordsPartition);
                }
            };
            tasks.add(c);
        }
        List<Future<HashMap<String, Integer>>> results = EXEC.invokeAll(tasks);

        ((HashMap<String,Integer>) map).__extension_updateCapacity(results.get(0).get().capacity());

        for (Future<HashMap<String, Integer>> fr : results) {
            for (int i = 0; i < fr.get().capacity(); i++) {
                if(fr.get().map[i] != null){
                    for(MapSet.KeyValuePair<String, Integer> kvp: fr.get().map[i]) {
                        ((HashMap<String,Integer>) map).__extension_addKVP(i, kvp);
                        wordCount += kvp.getValue();
                    }
                }
            }
        }
        double endTime = System.nanoTime();
        return (double) (endTime - startTime) * 1e-6;
    }

    public static void main(String[] args) throws IOException {
        analyzeFiles(args, true);
    }
}
