package utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class for read and save
 */
public class ReadAndSaveUtils {

    public static String returnFileContent(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        StringBuilder string = new StringBuilder();
        while (scanner.hasNext()) {
            string.append(scanner.next());
        }
        scanner.close();
        return string.toString();
    }
}
