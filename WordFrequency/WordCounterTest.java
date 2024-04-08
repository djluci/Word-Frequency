package WordFrequency;

import java.io.FileNotFoundException;

public class WordCounterTest {
    public static void main(String[] args) throws FileNotFoundException {
        // case 1: testing node analyze() and getCount()
        {
            // setup
            WordCounter wc = new WordCounter();
            wc.analyze("counttest.txt");
            // verify
            System.out.println((int) wc.getCount("it") + " == 4");

            // test
            assert (int) wc.getCount("it") == 4 : "Error in WordCounter::getCount()";
        }

        // case 2: testing node getTotalWordCount()
        {
            // setup
            WordCounter wc = new WordCounter();
            wc.analyze("counttest.txt");
            // verify
            System.out.println((int) wc.getTotalWordCount() + " == 24");

            // test
            assert (int) wc.getTotalWordCount() == 24 : "Error in WordCounter::getTotalWordCount()";
        }

        // case 3: testing node getUniqueWordCount()
        {
            // setup
            WordCounter wc = new WordCounter();
            wc.analyze("counttest.txt");
            // verify
            System.out.println((int) wc.getUniqueWordCount() + " == 10");

            // test
            assert (int) wc.getUniqueWordCount() == 10 : "Error in WordCounter::getUniqueWordCount()";
        }

        // case 4: testing node readWordCountFile()
        {
            // setup
            WordCounter wc = new WordCounter();
            wc.analyze("counttest.txt");
            wc.readWordCountFile("counttest_wordcount.txt");
            // verify
            System.out.println((int) wc.getCount("it") + " == 4");

            // test
            assert (int) wc.getCount("it") == 4 : "Error in WordCounter::readWordCountFile()";
        }

    }
}
