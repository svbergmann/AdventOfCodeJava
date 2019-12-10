package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

/**
 * Class for read and save
 */
public class ReadAndSaveUtils {

    public static String returnFileContent(String filename) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filename)).lines().collect(Collectors.joining("\n"));
    }
}
