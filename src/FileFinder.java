/**
 * Created by f_mol on 28-03-2017.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class FileFinder
{
    private static void countNumbers( Path file )  throws IOException
    {
        BufferedReader reader = Files.newBufferedReader( file );
        reader.lines().forEach( ( line ) -> {
            String[] numbers = line.split( "," );
            int total = 0;
            for( String number : numbers ) {
                total += Integer.parseInt( number );
            }
            System.out.println( file.toString() + " : " + total );
        } );
    }

    private static void visit( Path dir )
    {
        try {
            DirectoryStream< Path > stream = Files.newDirectoryStream( dir );
            try {
                for( Path subPath : stream ) {
                    if ( Files.isDirectory( subPath ) ) {
                        visit( subPath );
                    } else if ( subPath.toString().endsWith( ".txt" ) ) {
                        countNumbers( subPath ); //erstat countNumbers med hvilken som helst funktion (BufferedReader)
                    }
                }
            } finally {
                stream.close();
            }
        } catch( IOException e ) {
            e.printStackTrace();
        }
        // Files is a very useful utility class
        // DirectoryStream< Path > dirStream = Files.newDirectoryStream( dir );
    }

    public static void main( String[] args )
    {
        Path dir = Paths.get("C:/Users/f_mol/IdeaProjects/JavaConcurrency/src/data_example");
        System.out.println(dir);
        if ( Files.isDirectory( dir ) ) {
            System.out.println(dir);
            visit( dir );

        }
    }
}