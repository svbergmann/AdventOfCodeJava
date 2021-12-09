package aoc2021.day9;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Day9 extends Day {

	private static HashMap<Point, Integer> heatMap;
	private int lengthOfMap;
	private int heightOfMap;

	public Day9() {
		super(2021, 9);
		this.init(false);
	}

	private static List<Point> getLowestPoints(@NotNull HashMap<Point, Integer> heatMap, int heightOfMap, int lengthOfMap) {
		return heatMap.entrySet()
				.stream()
				.filter(pointIntegerEntry -> {
					var lowest = new AtomicBoolean(true);
					pointIntegerEntry.getKey().getAdjacent(heightOfMap, lengthOfMap)
							.forEach(point -> {
								if (heatMap.get(point) <= pointIntegerEntry.getValue()) {
									lowest.set(false);
								}
							});
					return lowest.get();
				})
				.map(pointIntegerEntry -> new Point(pointIntegerEntry.getKey().x(), pointIntegerEntry.getKey().y))
				.collect(Collectors.toList());
	}

	public static @NotNull HashSet<HashSet<Point>> getBasins(HashMap<Point, Integer> heatMap, int heightOfMap, int lengthOfMap, int edgeValue) {
		var lowestPoints = getLowestPoints(heatMap, heightOfMap, lengthOfMap);
		var basins = new HashSet<HashSet<Point>>();
		for (var point : lowestPoints) {
			var adjacents = new HashSet<>(point.getAdjacent(heightOfMap, lengthOfMap));
			var searchedPoints = new HashSet<>(adjacents);
			do {
				var set = new HashSet<Point>();
				for (var adjacentPoint : adjacents) {
					searchedPoints.add(adjacentPoint);
					if (heatMap.get(adjacentPoint) != edgeValue) {
						set.addAll(adjacentPoint.getAdjacent(heightOfMap, lengthOfMap));
					}
				}
				adjacents.addAll(set);
			} while (searchedPoints.size() != adjacents.size());
			adjacents.removeIf(point1 -> heatMap.get(point1) == 9);
			basins.add(adjacents);
		}
		return basins;
	}

	private void init(boolean example) {
		this.heightOfMap = example ? this.example.size() : this.input.size();
		this.lengthOfMap = example ? this.example.get(0).length() : this.input.get(0).length();
		heatMap = new HashMap<>();
		for (var i = 0; i < this.heightOfMap; i++) {
			var line = example ? this.example.get(i) : this.input.get(i);
			var j = 0;
			for (var c : line.toCharArray()) {
				heatMap.put(new Point(i, j), Integer.parseInt(String.valueOf(c)));
				j++;
			}
		}
	}

	@Override
	public String resultPartOne() {
		return getLowestPoints(heatMap, this.heightOfMap, this.lengthOfMap).stream()
				.mapToInt(point -> heatMap.get(point) + 1).sum() + "";
	}

	@Override
	public String resultPartTwo() {
		var list = getBasins(heatMap, this.heightOfMap, this.lengthOfMap, 9)
				.stream().map(ArrayList::new).sorted(Comparator.comparingInt(ArrayList::size)).toList();
		return list.get(list.size() - 3).size() * list.get(list.size() - 2).size() * list.get(list.size() - 1).size() + "";
	}

	private record Point(int x, int y) {

		public @NotNull List<Point> getAdjacent(int heightOfMap, int lengthOfMap) {
			var res = new ArrayList<Point>();
			if (this.x + 1 < heightOfMap) res.add(new Point(this.x + 1, this.y));
			if (this.x - 1 >= 0) res.add(new Point(this.x - 1, this.y));
			if (this.y + 1 < lengthOfMap) res.add(new Point(this.x, this.y + 1));
			if (this.y - 1 >= 0) res.add(new Point(this.x, this.y - 1));
			return res;
		}

		@Override
		public String toString() {
			return "Point{" +
					"x=" + this.x +
					", y=" + this.y +
					", value=" +
					heatMap.get(this) +
					'}';
		}
	}
}
