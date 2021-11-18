package aoc2020.day17;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.Arrays;
import java.util.List;

public class Day17 extends Day {

	private Cube[][][] cubes;

	public Day17() {
		super(2020, 17);
		this.initCubes(this.example);
	}

	@Override
	public String resultPartOne() {
		System.out.println(Arrays.deepToString(this.cubes));
		return null;
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	@Override
	public int number() {
		return 17;
	}

	private void initCubes(@NotNull List<String> input) {
		var size = input.get(0).length() - 1;
		var endSize = size * 6;
		this.cubes = new Cube[endSize][endSize][endSize];
		for (var x = 0; x <= size; x++) {
			var line = input.get(x).split("");
			for (var y = 0; y <= size; y++) {
				var active = "#".equals(line[y]);
				this.cubes[x][y][0] = new Cube(active, x, y, 0);
			}
		}
	}

	private void doCycle() {

	}

	private record Cube(boolean active, int x, int y, int z) implements Comparable<Cube> {
		@Override
		public String toString() {
			return "Cube{" +
					"active=" + active +
					", x=" + x +
					", y=" + y +
					", z=" + z +
					'}';
		}

		public int compareTo(@NotNull Cube cube) {
			if (this.x < cube.x) {
				return -1;
			}
			if (this.x > cube.x) {
				return 1;
			}
			if (this.y < cube.y) {
				return -1;
			}
			if (this.y > cube.y) {
				return 1;
			}
			return Integer.compare(this.z, cube.z);
		}
	}
}
