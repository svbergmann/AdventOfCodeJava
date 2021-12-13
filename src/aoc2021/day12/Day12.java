package aoc2021.day12;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


public class Day12 extends Day {

	private final HashSet<Cave> caves;
	private HashSet<ArrayList<Cave>> paths;
	private Cave startCave = null;

	public Day12() {
		super(2021, 12);
		this.caves = new HashSet<>();
		for (var s : this.input) {
			var s1 = s.split("-");
			Cave cave1 = null;
			Cave cave2 = null;
			for (var cave : this.caves) {
				if (cave.name.equals(s1[0])) {
					cave1 = cave;
					continue;
				}
				if (cave.name.equals(s1[1])) {
					cave2 = cave;
				}
			}
			if (cave1 != null) {
				if (cave2 == null) cave2 = new Cave(s1[1]);
				this.caves.add(cave2);
			} else if (cave2 != null) {
				cave1 = new Cave(s1[0]);
				this.caves.add(cave1);
			} else {
				cave1 = new Cave(s1[0]);
				this.caves.add(cave1);
				cave2 = new Cave(s1[1]);
				this.caves.add(cave2);
			}
			cave1.addConnection(cave2);
			cave2.addConnection(cave1);
		}
		this.startCave = null;
		for (var cave : this.caves) {
			if (cave.name.equals("start")) {
				this.startCave = cave;
				break;
			}
		}
	}

	@Override
	public String resultPartOne() {
		this.paths = new HashSet<>();
		this.findAllPathsRek(this.startCave, new ArrayList<>(List.of(this.startCave)), this.paths);
		return this.paths.size() + "";
	}

	private void findAllPathsRek(@NotNull Cave cave, ArrayList<Cave> path, HashSet<ArrayList<Cave>> bucket) {
		if (cave.name.equals("end")) {
			bucket.add(path);
			return;
		}
		for (var nextCave : cave.connections) {
			if (!nextCave.bigCave && path.contains(nextCave)) continue;
			var copy = new ArrayList<>(path);
			copy.add(nextCave);
			this.findAllPathsRek(nextCave, copy, bucket);
		}
	}

	private void findAllPathsRekPart2(@NotNull Cave cave, ArrayList<Cave> path, HashSet<ArrayList<Cave>> bucket, boolean duplicatedCave) {
		if (cave.name.equals("end")) {
			bucket.add(path);
			return;
		}
		for (var nextCave : cave.connections) {
			var changed = false;
			if (nextCave.name.equals("start")) continue;
			if (!nextCave.bigCave) {
				if (path.contains(nextCave)) {
					if (duplicatedCave) continue;
					duplicatedCave = true;
					changed = true;
				}
			}
			var copy = new ArrayList<>(path);
			copy.add(nextCave);
			this.findAllPathsRekPart2(nextCave, copy, bucket, duplicatedCave);
			if (changed) duplicatedCave = false;
		}
	}

	@Override
	public String resultPartTwo() {
		this.paths = new HashSet<>();
		this.findAllPathsRekPart2(this.startCave, new ArrayList<>(List.of(this.startCave)), this.paths, false);
		return this.paths.size() + "";
	}

	private static class Cave {
		private final HashSet<Cave> connections;
		String name;
		boolean visited;
		boolean bigCave;

		public Cave(@NotNull String name) {
			this.name = name;
			this.connections = new HashSet<>();
			this.visited = false;
			this.bigCave = Character.isUpperCase(name.charAt(0));
		}

		public void addConnection(Cave cave) {
			this.connections.add(cave);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || this.getClass() != o.getClass()) return false;
			Cave cave = (Cave) o;
			return Objects.equals(this.name, cave.name);
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.name);
		}

		@Override
		public String toString() {
			return this.name;
		}

	}
}
