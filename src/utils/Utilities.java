package utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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
		for (var ints : twoDimArray) {
			for (var anInt : ints) {
				res.append((anInt == 0) ? "." : anInt);
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

	public static <T> Set<T> findDuplicates(@NotNull Collection<T> collection) {
		var uniques = new HashSet<T>();
		return collection.stream()
				.filter(t -> !uniques.add(t))
				.collect(Collectors.toCollection(HashSet::new));
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

	private record Point(int x, int y) implements Comparable<Point> {
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || this.getClass() != o.getClass()) return false;
			Point point = (Point) o;
			return this.x == point.x && this.y == point.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.x, this.y);
		}

		@Override
		public int compareTo(@NotNull Point o) {
			return (this.x - o.x == 0) ? this.y - o.y : this.x - o.x;
		}

		public @NotNull HashSet<Point> getAdjacentAndDiagonal(int heightOfMap, int lengthOfMap) {
			var res = new HashSet<Point>();
			if (this.x + 1 < heightOfMap) {
				res.add(new Point(this.x + 1, this.y));
				if (this.y + 1 < lengthOfMap) res.add(new Point(this.x + 1, this.y + 1));
			}
			if (this.x - 1 >= 0) {
				res.add(new Point(this.x - 1, this.y));
				if (this.y - 1 >= 0) res.add(new Point(this.x - 1, this.y - 1));
			}
			if (this.y + 1 < lengthOfMap) {
				res.add(new Point(this.x, this.y + 1));
				if (this.x - 1 >= 0) res.add(new Point(this.x - 1, this.y + 1));
			}
			if (this.y - 1 >= 0) {
				res.add(new Point(this.x, this.y - 1));
				if (this.x + 1 < heightOfMap) res.add(new Point(this.x + 1, this.y - 1));
			}
			return res;
		}

		public @NotNull HashSet<Point> getAdjacent(int heightOfMap, int lengthOfMap) {
			var res = new HashSet<Point>();
			if (this.x + 1 < heightOfMap) res.add(new Point(this.x + 1, this.y));
			if (this.x - 1 >= 0) res.add(new Point(this.x - 1, this.y));
			if (this.y + 1 < lengthOfMap) res.add(new Point(this.x, this.y + 1));
			if (this.y - 1 >= 0) res.add(new Point(this.x, this.y - 1));
			return res;
		}
	}
}
