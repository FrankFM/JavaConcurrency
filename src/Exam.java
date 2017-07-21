import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Frank Frederiksen-Møller <frfre16@student.sdu.dk>
 */
public class Exam
{




    public static ConcurrentHashMap<String, Integer> occurrence = new ConcurrentHashMap<>();
    public static List<Result> total = Collections.synchronizedList(new ArrayList<>());

    public static List< Result > findAll( Path dir ) {

        /**

     * This method recursively visits a directory to find all the text files contained in it and its subdirectories.
     *
     * You should consider only files ending with a .txt suffix. You are guaranteed that they will be text files.
     *
     * You can assume that each text file contains a (non-empty) comma-separated sequence of
     * (positive) numbers. For example: 100,200,34,25
     * There won't be any new lines, spaces, etc., and the sequence never ends with a comma.
     *
     * The search is recursive: if the directory contains subdirectories,
     * these are also searched and so on so forth (until there are no more
     * subdirectories).
     *
     * This method returns a list of results. The list contains a result for each text file that you find.
     * Each {@link Result} stores the path of its text file, and the highest number (maximum) found inside of the text file.
     *
     * @param dir the directory to search
     * @return a list of results ({@link Result}), each giving the highest number found in a file
     */
        int phase = Tasks.ph.getPhase();
        Tasks.ph.register();
        Tasks.executor.submit(new Tasks(dir));
        Tasks.ph.awaitAdvance(phase);


        try
        {
            Tasks.executor.shutdown();
            Tasks.executor.awaitTermination(5, TimeUnit.SECONDS);

        }

        catch (InterruptedException e)
        {
            System.err.println("Tasks Interrupted");
        }

        finally
        {
            if (!Tasks.executor.isTerminated())
            {
                System.err.println("Cancelling non-finished Tasks");
            }
            Tasks.executor.shutdownNow();

        }

        return total;
    }


    public static void findMax (Path file) throws IOException
    {
        //This method searches for the highest number in each line of every text file
        //and adds Path and Number to the synchronized Arraylist "total"

        BufferedReader reader = Files.newBufferedReader( file );
        reader.lines().forEach( ( line ) -> {
            String[] numbers = line.split( "," );
            int temp = 0;

            for (String number : numbers)
            {
                if (temp < Integer.parseInt(number))
                {
                    temp = Integer.parseInt(number);

                }
            //Adds every Path and Number to the Arraylist
            }final int finalMax = temp;
            total.add(new Result() {
                @Override
                public Path path() {
                    return file;
                }

                @Override
                public int number() {
                    return finalMax;
                }
            });

        });
    }



    public static Result findAny( Path dir, int n, int min )

    {/**
     * Finds a file that contains at most (no more than) n numbers and such that all
     * numbers in the file are equal or greater than min.
     *
     * This method searches only for one (any) file in the directory
     * (parameter dir) such that the condition above is respected.
     * As soon as one such occurrence is found, the search can be
     * stopped and the method can return immediately.
     *
     * As for method {@code findAll}, the search is recursive.
     */

        int phase2 = Tasks2.ph2.getPhase();
//        Tasks2.ph2.register();
        Tasks2.executor.submit(new Tasks2(dir, n, min));
//        Tasks2.ph2.awaitAdvance(phase2);

        try
        {

//            Tasks2.executor.shutdown();
            Tasks2.executor.awaitTermination(5, TimeUnit.SECONDS);

        }

        catch (InterruptedException e)
        {
            System.err.println("Tasks Interrupted");
        }

        finally
        {
            if (!Tasks2.executor.isTerminated())
            {
                System.err.println("Cancelling non-finished Tasks");
            }
            Tasks2.executor.shutdownNow();

        }

        return null;

    }

    public static void findNumbers (Path file, int n, int min ) throws IOException
    {
        //This method reads a line in a given file and count the amount of numbers in the file

        BufferedReader reader = Files.newBufferedReader(file);
        reader.lines().forEach((line) -> {
            String[] numbers = line.split(",");

            int countNumbers = 0;
            int minimum = 0;

            // The for-loop iterate through the String array with numbers
            // and parses each number to an integer
            for (String number : numbers)
            {
                Integer.parseInt(number);
                countNumbers++;

                // The nested if statement checks wether a number is greater or equal to min
                // if true minimum is incremented.
                if (Integer.parseInt(number) >= min)
                {
                    minimum++;
                }

            }

            //The if statement checks wether the amount of numbers in a given file contains at most n numbers
            //and that all the numbers are equal or greater than min.
            //Minimum is compared to countNumbers to make sure that the file contains numbers greater or equal to min
            //If true the program will return immediately

            if (countNumbers <= n && minimum == countNumbers)
            {
                System.out.println(countNumbers + " : " + file.toString());
                Tasks2.executor.shutdownNow();
//                Tasks2.ph2.forceTermination();
//                System.exit(0);
            }

        });

    }


    public static Stats stats( Path dir )
    {/**
     * Computes overall statistics about the occurrences of numbers in a directory.
     *
     * This method recursively searches the directory for all numbers in all files and returns
     * an {@link Stats} object containing the statistics of interest. See the
     * documentation of {@link Stats}.
     */
        Statistics stat = new Statistics();

        int phase3 = Tasks3.ph3.getPhase();
        Tasks3.ph3.register();
        Tasks3.executor.submit(new Tasks3(dir));
        Tasks3.ph3.awaitAdvance(phase3);


        try
        {

            Tasks3.executor.shutdown();
            Tasks3.executor.awaitTermination(5, TimeUnit.SECONDS);
            for(Map.Entry<String, Integer> entry : occurrence.entrySet())
            {
                String key = entry.getKey().toString();
                Integer value = entry.getValue();
//                System.out.println( key + " -> " + value);

            }
            stat.occurrences(50);
            stat.mostFrequent();
            stat.leastFrequent();

        }

        catch (InterruptedException e)
        {
            System.err.println("Tasks Interrupted");
        }

        finally
        {
            if (!Tasks3.executor.isTerminated())
            {
                System.err.println("Cancelling non-finished Tasks");
            }
            Tasks3.executor.shutdownNow();

        }
        return null;
    }

    public static void numbersOccur(Path file) throws IOException
    {
        //This method counts the amount of times every number appears in every text file
        //and puts the result in the Concurrent Hashmap "occurrence".

        BufferedReader reader = Files.newBufferedReader(file);
        reader.lines().forEach((line) -> {
            String[] numbers = line.split(",");
            String key = null;
            int value = 0;
            synchronized (occurrence)
            {
                for (String number : numbers) {
                    if (occurrence.containsKey(number)) {
                        int count = occurrence.get(number);
                        occurrence.put(number, count + 1);
                    } else {
                        occurrence.put(number, 1);
                    }
                }
            }

        });


    }

}
