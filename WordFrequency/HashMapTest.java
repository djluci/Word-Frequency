package WordFrequency;

/*
 * File Name:       HashMapTest.java
 * Author:          Duilio Lucio
 * Project:         Word Frequency
 * Course:          CS231
 * Section:         B
 * Last Modified:   04/20/2023
 */

import java.util.Objects;

public class HashMapTest {
    
    public static void main(String[] args){
        // Case 1: Testing node get()
        {
            // setup
            HashMap<String,Integer> map = new HashMap<String, Integer>(16);
            map.put("a", 9);
            // verify
            System.out.println((int) map.put("a", 10) + " == 9");
            map.put("a", 9);

            // test
            assert (int) map.put("a", 10) == 9 : "Error in HashMap::put()";
        }

        // case 2: testing node get()
        {
            // setup
            HashMap<String,Integer> map = new HashMap<String, Integer>(16);
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // verify
            System.out.println((int) map.get("c") + " == 29");

            // test
            assert (int) map.get("c") == 29 : "Error in HashMap::get()";
        }

        // case 3: testing node containsKey()
        {
            // setup
            HashMap<String,Integer> map = new HashMap<String, Integer>(16);
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // verify
            System.out.println((boolean) map.containsKey("c") + " == true");

            // test
            assert (boolean) map.containsKey("c") == true : "Error in HashMap::containsKey()";
        }

        // case 4: testing node keySet()
        {
            // setup
            HashMap<String,Integer> map = new HashMap<String, Integer>(16);
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // verify
            System.out.println((String) map.keySet().get(0) + " == a");

            // test
            assert Objects.equals((String) map.keySet().get(0), "a") : "Error in HashMap::keySet()";
        }

        // case 5: testing node values()
        {
            // setup
            HashMap<String,Integer> map = new HashMap<String, Integer>(16);
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // verify
            System.out.println((Integer) map.values().get(0) + " == 10");

            // test
            assert (Integer) map.values().get(0) == 10 : "Error in HashMap::values()";
        }

        // case 6: testing node size()
        {
            // setup
            HashMap<String,Integer> map = new HashMap<String, Integer>(16);
            map.put("a", 10);
            map.put("b", 2);
            map.put("c", 29);

            // verify
            System.out.println((Integer) map.size() + " == 3");

            // test
            assert (Integer) map.size() == 3 : "Error in HashMap::size()";
        }
    }
}
