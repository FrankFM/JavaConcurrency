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
public class Tasks3 implements Runnable
{
    //This class will execute threads for the method stats()
    // and give a text file to a new thread.

    public static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static Phaser ph3 = new Phaser();

    Path file;

    public Tasks3 (Path file)
    {
        this.file = file;

    }

    @Override
    public void run()
    {
        try {
            DirectoryStream< Path > stream = Files.newDirectoryStream( this.file );
            try {
                for( Path subPath : stream ) {
                    if ( Files.isDirectory( subPath ) ) {
                        ph3.register();
                        executor.submit(new Tasks3(subPath));
                    } else if ( subPath.toString().endsWith( ".txt" ) )
                    {

                        try {
                            Exam.numbersOccur(subPath);
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
        }ph3.arrive();



    }
}
