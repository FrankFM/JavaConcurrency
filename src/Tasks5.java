import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Created by f_mol on 06-05-2017.
 */
//public class Tasks5 implements Runnable
//{
//
//    //This class will execute threads for the method stats()
//    // and give a text file to a new thread.
//
//    public static ExecutorService executor = Executors.newFixedThreadPool(4);
//
//    public static Phaser ph5 = new Phaser();
//
//    Path file;
//
//    public Tasks5 (Path file)
//    {
//        this.file = file;
//
//    }
//
//    @Override
//    public void run()
//    {
//        try {
//            DirectoryStream< Path > stream = Files.newDirectoryStream( this.file );
//            try {
//                for( Path subPath : stream ) {
//                    if ( Files.isDirectory( subPath ) ) {
//                        ph5.register();
//                        executor.submit(new Tasks5(subPath));
//                    } else if ( subPath.toString().endsWith( ".txt" ) )
//                    {
//
//                        try {
//                            Exam.numbersByTotal(subPath);
//                        }
//                        catch (IOException e) {
//                            e.printStackTrace();
//
//                        }
//
//                    }
//
//                }
//            } finally {
//
//                stream.close();
//            }
//        } catch( IOException e ) {
//            e.printStackTrace();
//        }ph5.arrive();
//
//
//
//    }
//}
