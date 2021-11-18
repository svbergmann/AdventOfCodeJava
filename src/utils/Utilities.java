package utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Utilities {

	public static final String PART_ONE = "Result of part one: ";
	public static final String PART_TWO = "Result of part two: ";

	/**
	 * Reads Strings from a file and puts them linewise into an ArrayList.
	 *
	 * @param numberOfDay the specific day
	 * @param example     if the returned file shall be the example file
	 * @return an ArrayList of the lines or an empty one if reading did not work
	 */
	public static ArrayList<String> readFromFile(int year, int numberOfDay, boolean example) {
		var res = new ArrayList<String>();
		try {
			var filename = "src/aoc" + year + "/day" + numberOfDay;
			if (example) {
				filename += "/example.txt";
			} else {
				filename += "/input.txt";
			}
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			reader.lines().forEach(res::add);
			reader.close();
		} catch (IOException e) {
			Logger.getGlobal().info("Reading did not work.");
		}
		return res;
	}

}