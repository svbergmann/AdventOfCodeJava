package utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Utilities {

	public static final String PART_ONE = "Result of part one: ";
	public static final String PART_TWO = "Result of part two: ";

	/**
	 * Reads Strings from a file and puts them linewise into an ArrayList.
	 *
	 * @param year        the specific year
	 * @param numberOfDay the specific day
	 * @param example     if the returned file shall be the example.txt.txt file
	 * @return an ArrayList of the lines or an empty one if reading did not work
	 */
	public static @NotNull List<String> readFromFile(int year, int numberOfDay, boolean example) {
		var res = new ArrayList<String>();
		try {
			var filename = "src/aoc" + year + "/day" + numberOfDay + (example ? "/example.txt.txt" : "/input.txt");
			try (var reader = new BufferedReader(new FileReader(filename))) {
				reader.lines().forEach(res::add);
			}
		} catch (IOException e) {
			Logger.getGlobal().info("Reading did not work.");
		}
		return res;
	}

	public static @NotNull String getTwoDimArrayString(int[][] twoDimArray) {
		if (twoDimArray == null) return "";
		String res = "";
		for (var ints : twoDimArray) {
			res = Arrays.stream(ints)
			            .mapToObj(anInt -> String.valueOf((anInt == 0) ? "." : anInt))
			            .collect(Collectors.joining("", "", "\n"));
		}
		return res;
	}

	public static @NotNull String getTwoDimArrayString(boolean[][] twoDimArray) {
		if (twoDimArray == null) return "";
		StringBuilder res = new StringBuilder();
		for (var bools : twoDimArray) {
			for (var aBool : bools) {
				res.append((aBool) ? "#" : ".");
			}
			res.append("\n");
		}
		return res.toString();
	}

	public static <T> Set<T> findDuplicates(@NotNull Collection<T> collection) {
		var uniques = new HashSet<T>();
		return collection.stream()
		                 .filter(t -> !uniques.add(t))
		                 .collect(Collectors.toCollection(HashSet::new));
	}

	public static String returnFileContent(String filename) throws FileNotFoundException {
		return new BufferedReader(new FileReader(filename)).lines().collect(Collectors.joining("\n"));
	}
}
