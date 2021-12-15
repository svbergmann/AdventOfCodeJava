package utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
			var filename = "src/aoc" + year + "/day" + numberOfDay + (example ? "/example.txt" : "/input.txt");
			try (var reader = new BufferedReader(new FileReader(filename))) {
				reader.lines().forEach(res::add);
			}
		} catch (IOException e) {
			Logger.getGlobal().info("Reading did not work.");
		}
		return res;
	}

	/**
	 * Reads ints from a file and puts them linewise into a 2dim array.
	 *
	 * @param list the list to be processed
	 * @return a 2dim array of ints
	 */
	public static int[][] getListAsTwoDimArray(List<String> list) {
		var array = new int[list.size()][list.get(0).length()];
		for (var i = 0; i < list.size(); i++) {
			var charArray = list.get(i).toCharArray();
			for (var j = 0; j < charArray.length; j++) {
				array[i][j] = charArray[j] - '0';
			}
		}
		return array;
	}

	public static @NotNull String getTwoDimArrayString(int[][] twoDimArray) {
		if (twoDimArray == null) return "";
		var res = new StringBuilder();
		for (int[] ints : twoDimArray) {
			for (int anInt : ints) {
				res.append(anInt);
			}
			res.append("\n");
		}
		return res.toString();
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

	/**
	 * Tries to compute all duplicates from the given collection.
	 *
	 * @param collection the collection where the duplicates are
	 * @param <T>        the generic param
	 * @return a set of all duplicates
	 */
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
