package utils;

import org.jetbrains.annotations.NotNull;

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
	 * @param year        the specific year
	 * @param numberOfDay the specific day
	 * @param example     if the returned file shall be the example.txt file
	 * @return an ArrayList of the lines or an empty one if reading did not work
	 */
	public static @NotNull ArrayList<String> readFromFile(
			int year, int numberOfDay, boolean example) {
		var res = new ArrayList<String>();
		try {
			var filename =
					"src/aoc" + year + "/day" + numberOfDay + (example ? "/example.txt" : "/input.txt");
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
		StringBuilder res = new StringBuilder();
		for (int[] ints : twoDimArray) {
			for (int anInt : ints) {
				res.append((anInt == 0) ? "." : anInt);
			}
			res.append("\n");
		}
		return res.toString();
	}

	public static class Pair<K, V> {
		private K key;
		private V value;

		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return this.value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "(" + this.key + ", " + this.value + ")";
		}
	}
}
