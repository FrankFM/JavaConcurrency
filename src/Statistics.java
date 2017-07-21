import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Frank Frederiksen-Møller <frfre16@student.sdu.dk>
 */

public class Statistics implements Stats
{

    @Override
    public int occurrences(int number)
    {
        int value = 0;
        for(Map.Entry<String, Integer> entry : Exam.occurrence.entrySet())
        {
            if (number == Integer.parseInt(entry.getKey()))
            {
                value = entry.getValue();
            }
        }
        System.out.println("The number " + number + " occurred " + value + " times in the files");
        return value;
    }

    @Override
    public List<Path> atMost(int max) {
    //This method is not implemented in the project
        return null;
    }



    @Override
    public int mostFrequent() {

        int mostFreq = 0;
        int key = 0;
        for(Map.Entry<String, Integer> entry : Exam.occurrence.entrySet())
        {
            if (mostFreq < entry.getValue())
            {
                mostFreq = entry.getValue();
                key = Integer.parseInt(entry.getKey());
            }
        }
        System.out.println("The number found the most times is: " + key + " " + "Found: " + mostFreq + " times.");
        return key;
    }

    @Override
    public int leastFrequent() {

        int leastFreq = 0;
        int key = 0;

        for(Map.Entry<String, Integer> entry : Exam.occurrence.entrySet())
        {
            if (Integer.MIN_VALUE < entry.getValue())
            {
                leastFreq = entry.getValue();
                key = Integer.parseInt(entry.getKey());
            }
        }
        System.out.println("The number found the least times is: " + key + " " + "Found: " + leastFreq + " times.");
        return key;
    }

    @Override
    public List<Path> byTotals() {
        //This method is not implemented in the project
        return null;
    }
}
