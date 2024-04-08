package WordFrequency;

/*
 * File Name:       BSTMapTest.java
 * Author:          Duilio Lucio
 * Project:         Word Frequency
 * Course:          CS231
 * Section:         B
 * Last Modified:   04/20/2023
 */

import java.util.Objects;

public class BSTMapTest {
    public static void main(String[] args){
        // Case 1: Testing Node get()
        {
            //Setup
            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
            map.put("a", 9);

            //Verify
            System.out.println((int) map.put("a", 10) + " == 9");
            map.put("a", 9);

            //Test
            assert (int) map.put("a", 10) == 9 : "Error in BSTMap::put()";
        }

        // Case 2: Testing Node get()
        {
            //Setup
            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            //Verify
            System.out.println((int) map.get("c") + " == 29");

            //Test
            assert (int) map.get("c") == 29 : "Error in BSTMap::get()";
        }

        // Case 3: Testing Node containsKey()
        {
            //Setup 
            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            //Verify
            System.out.println((boolean) map.containsKey("c") + " == true");

            //Test
            assert (boolean) map.containsKey("c") == true : "Error in BSTMap::containsKey()";
        }

        // Case 4: Testing Node keySet()
        {
            //Setup
            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // Verify
            System.out.println((String) map.keySet().get(0) + " == a");

            // Test
            assert Objects.equals((String) map.keySet().get(0), "a") : "Error in BSTMap::keySet()";
        }

        // Case 5: Testing Node values()
        {
            // Setup
            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // Verify
            System.out.println((Integer) map.values().get(0) + " == 10");

            // Test 
            assert (Integer) map.values().get(0) == 10 : "Error in BSTMap::values()";
        }

        // Case 6: Testing Node size()
        {
            // Setup
            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // Verify
            System.out.println((Integer) map.size() + " == 3");

            // Test
            assert (Integer) map.size() == 3 : "Error in BSTMap::size()";
        }
    }
}
