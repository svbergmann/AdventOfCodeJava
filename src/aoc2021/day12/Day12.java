package aoc2021.day12;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.HashSet;
import java.util.Objects;

public class Day12 extends Day {

	private final HashSet<Cave> caves;

	public Day12() {
		super(2021, 12);
		this.caves = new HashSet<>();
		for (var s : this.example) {
			var s1 = s.split("-");
			Cave cave1;
			Cave cave2;
			boolean cave1Added = false;
			boolean cave2Added = false;
			for (var cave : this.caves) {
				if (cave.name.equals(s1[0])) {
					cave1 = cave;
					continue;
				}
				if (cave.name.equals(s1[1])) {
					cave2 = cave;
				}
			}
			if (!cave1Added) {
				var cave = new Cave(s1[0]);
				cave.addConnection(new Cave(s1[1]));
				this.caves.add(cave);
			}
			if (!cave2Added) {
				var cave = new Cave(s1[1]);
				cave.addConnection(new Cave(s1[0]));
				this.caves.add(cave);
			}
		}
	}

	@Override
	public String resultPartOne() {

		return null;
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	private static class Cave {
		String name;
		HashSet<Cave> connections;
		boolean visited;
		boolean bigCave;

		public Cave(@NotNull String name) {
			this.name = name;
			this.connections = new HashSet<>();
			this.visited = false;
			this.bigCave = Character.isUpperCase(name.toCharArray()[0]);
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
	}
}
