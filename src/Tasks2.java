import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 *
 * @author Frank Frederiksen-Møller <frfre16@student.sdu.dk>
 */
public class Tasks2 implements Runnable
{

    //This class will execute threads for the method findAny
    // and give a text file to a new thread.



    public static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static Phaser ph2 = new Phaser();

    Path file;
    int n;
    int min;

    public Tasks2(Path file, int n, int min)
    {
        this.file = file;
        this.n = n;
        this.min = min;

    }
    public void run()
    {

        try {
            DirectoryStream< Path > stream = Files.newDirectoryStream( this.file );
            try {
                for( Path subPath : stream ) {
                    if ( Files.isDirectory( subPath ) ) {
                        ph2.register();
                        executor.submit(new Tasks2(subPath, n, min));
                    } else if ( subPath.toString().endsWith( ".txt" ) )
                    {

                        try {
                            Exam.findNumbers(subPath, n, min);
                        }
                        catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                }
            } finally {

                stream.close();
            }
        } catch( IOException e ) {
            e.printStackTrace();
        }ph2.arrive();


    }
}
