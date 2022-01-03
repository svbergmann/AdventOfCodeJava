package aoc2019.Day3;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return this.x == position.x && this.y == position.y;
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + this.x + ", y=" + this.y + '}';
    }

    public int getManhattenDistanceToZero() {
        return Math.abs(this.x) + Math.abs(this.y);
    }
}
