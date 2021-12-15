package utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Simple point record class.
 *
 * @param x the x value
 * @param y the y value
 */
public record Point(int x, int y) implements Comparable<Point> {
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
	public String toString() {
		return "Point{" +
				"x=" + this.x +
				", y=" + this.y +
				'}';
	}

	@Override
	public int compareTo(@NotNull Point o) {
		return (this.x - o.x == 0) ? this.y - o.y : this.x - o.x;
	}

	/**
	 * Computes a set of adjacent and diagonal adjacent points according to the given params.
	 *
	 * @param heightOfMap the height
	 * @param lengthOfMap the length
	 * @param diagonal    if diagonal adjacent points should be included
	 * @return a HashSet with adjacent points
	 */
	public @NotNull Set<Point> getAdjacent(int heightOfMap, int lengthOfMap, boolean diagonal) {
		var res = new HashSet<Point>();
		if (this.x + 1 < heightOfMap) {
			res.add(new Point(this.x + 1, this.y));
			if (this.y + 1 < lengthOfMap && diagonal) res.add(new Point(this.x + 1, this.y + 1));
		}
		if (this.x - 1 >= 0) {
			res.add(new Point(this.x - 1, this.y));
			if (this.y - 1 >= 0 && diagonal) res.add(new Point(this.x - 1, this.y - 1));
		}
		if (this.y + 1 < lengthOfMap) {
			res.add(new Point(this.x, this.y + 1));
			if (this.x - 1 >= 0 && diagonal) res.add(new Point(this.x - 1, this.y + 1));
		}
		if (this.y - 1 >= 0) {
			res.add(new Point(this.x, this.y - 1));
			if (this.x + 1 < heightOfMap && diagonal) res.add(new Point(this.x + 1, this.y - 1));
		}
		return res;
	}
}
