package aoc2020.day17;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day17 extends Day {

	private HashSet<Cube> cubes;

	public Day17() {
		super(2020, 17);
		this.initCubes(this.input);
	}

	@Override
	public String resultPartOne() {
		for (var i = 1; i <= 6; i++) {
			this.generateNeighborCubes();
			this.doCycle();
//			System.out.println("After " + i + " cycle" + ((i == 1) ? "" : "s") + ":");
//			this.printCubes();
		}
		return String.valueOf(this.cubes.stream().filter(cube -> cube.active).count());
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
		var size = input.get(0).length();
		this.cubes = new HashSet<>();
		for (var x = 0; x < size; x++) {
			var line = input.get(x).split("");
			for (var y = 0; y < size; y++) {
				var active = "#".equals(line[y]);
				this.cubes.add(new Cube(active, x - size / 2, y - size / 2, 0));
			}
		}
	}

	private void printCubes() {
		var zMax = this.cubes.stream().max(Cube::compareZ).get().z;
		var yMax = this.cubes.stream().max(Cube::compareY).get().y;
		for (var z = -zMax; z <= zMax; z++) {
			System.out.println("z=" + z);
			for (var x = -yMax; x <= yMax; x++) {
				for (var y = -yMax; y <= yMax; y++) {
					int finalX = x;
					int finalY = y;
					int finalZ = z;
					if (this.cubes.stream().anyMatch(cube ->
							cube.x == finalX &&
									cube.y == finalY &&
									cube.z == finalZ)) {
						System.out.print(this.cubes.stream().filter(cube ->
								cube.x == finalX &&
										cube.y == finalY &&
										cube.z == finalZ).findFirst().get().active ? "#" : ".");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private void generateNeighborCubes() {
		var cubesToAdd = new ArrayList<Cube>();
		this.cubes.forEach(cube -> {
			for (var x = -1; x <= 1; x++) {
				var neighborX = cube.x + x;
				for (var y = -1; y <= 1; y++) {
					var neighborY = cube.y + y;
					for (var z = -1; z <= 1; z++) {
						var neighborZ = cube.z + z;
						if (cube.x == neighborX &&
								cube.y == neighborY &&
								cube.z == neighborZ) continue;
						if (this.cubes.stream()
								.noneMatch(cube1 ->
										cube1.x == neighborX &&
												cube1.y == neighborY &&
												cube1.z == neighborZ)) {
							cubesToAdd.add(new Cube(false, neighborX, neighborY, neighborZ));
						}
					}
				}
			}
		});
		this.cubes.addAll(cubesToAdd);
	}

	private void doCycle() {
		var tmpCubes = new HashSet<Cube>();
		this.cubes.forEach(cube -> {
			var numActiveNeighbors = (int) this.getNeighborCubes(cube)
					.stream()
					.filter(cube1 -> cube1.active)
					.count();
			var tmpCube = new Cube(cube);
			if (tmpCube.active) {
				switch (numActiveNeighbors) {
					case 2:
					case 3:
						break;
					default:
						tmpCube.active = false;
						break;
				}
			} else {
				if (numActiveNeighbors == 3) {
					tmpCube.active = true;
				}
			}
			tmpCubes.add(tmpCube);
		});
		this.cubes = tmpCubes;
	}

	private @NotNull List<Cube> getNeighborCubes(Cube cube) {
		var res = new ArrayList<Cube>();
		for (var x = -1; x <= 1; x++) {
			var neighborX = cube.x + x;
			for (var y = -1; y <= 1; y++) {
				var neighborY = cube.y + y;
				for (var z = -1; z <= 1; z++) {
					var neighborZ = cube.z + z;
					if (cube.x != neighborX ||
							cube.y != neighborY ||
							cube.z != neighborZ) {
						for (Cube cube1 : this.cubes) {
							if (cube1.x == neighborX &&
									cube1.y == neighborY &&
									cube1.z == neighborZ) {
								res.add(cube1);
							}
						}
					}
				}
			}
		}
		return res;
	}

	@Data
	private static class Cube implements Comparable<Cube> {
		public boolean active;
		public int x;
		public int y;
		public int z;

		public Cube(boolean active, int x, int y, int z) {
			this.active = active;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Cube(@NotNull Cube cube) {
			this.active = cube.active;
			this.x = cube.x;
			this.y = cube.y;
			this.z = cube.z;
		}

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

		public int compareZ(@NotNull Cube cube) {
			return Integer.compare(this.z, cube.z);
		}

		public int compareY(@NotNull Cube cube) {
			return Integer.compare(this.y, cube.y);
		}
	}
}
