/**
 * Created by f_mol on 28-03-2017.
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ListIterator;


/**This class is made for testing the methods in the Exam class
 *
 * @author Frank Frederiksen-Møller <frfre16@student.sdu.dk>
 */

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path dir = Paths.get("C:/Users/f_mol/IdeaProjects/JavaConcurrency/src/data_example");
        double time = System.nanoTime();

        if (Files.isDirectory(dir))
        {
            Exam.findAll(dir);
//            Exam.findAny(dir, 159, 6);
//            Exam.stats(dir);

        }
        double resTime = (System.nanoTime() - time);
        System.out.println("Time measured in seconds:" + resTime / 1000000000);

//        ListIterator<Result> test = Exam.total.listIterator();
//        int counter = 0;
//
//        while (test.hasNext())
//        {
//
//            counter++;
//            System.out.println(counter + ":" + test.next().path());
//
//        }

    }
}