package aoc2021.day22;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Day22 extends Day {
    public Day22() {
        super(2021, 22);
    }

    private static @NotNull List<Instruction> readInstructions(@NotNull List<String> list) {
        var res = new ArrayList<Instruction>(list.size());
        for (var instructionline : list) {
            var on = instructionline.startsWith("on");
            var xValues = instructionline.split("x=")[1].trim().split(",")[0].trim();
            var yValues = instructionline.split("y=")[1].trim().split(",")[0].trim();
            var zValues = instructionline.split("z=")[1].trim().split(",")[0].trim();
            var minX = Integer.parseInt(xValues.split("\\.\\.")[0]);
            var maxX = Integer.parseInt(xValues.split("\\.\\.")[1]);
            var minY = Integer.parseInt(yValues.split("\\.\\.")[0]);
            var maxY = Integer.parseInt(yValues.split("\\.\\.")[1]);
            var minZ = Integer.parseInt(zValues.split("\\.\\.")[0]);
            var maxZ = Integer.parseInt(zValues.split("\\.\\.")[1]);
            res.add(new Instruction(minX, maxX, minY, maxY, minZ, maxZ, on));
        }
        return res;
    }

    private static int countOnCubes(int minX, int maxX,
                                    int minY, int maxY,
                                    int minZ, int maxZ,
                                    HashMap<Point3D, Boolean> cubes) {
        var res = 0;
        for (var x = minX; x <= maxX; x++) {
            for (var y = minY; y <= maxY; y++) {
                for (var z = minZ; z <= maxZ; z++) {
                    if (cubes.get(new Point3D(x, y, z))) res++;
                }
            }
        }
        return res;
    }

    @Override
    public String resultPartOne() {
        var instructionList = readInstructions(this.example);
        var cubes = new HashMap<Point3D, Boolean>();
        for (var instruction : instructionList) {
            for (var x = instruction.minX; x <= instruction.maxX; x++) {
                for (var y = instruction.minY; y <= instruction.maxY; y++) {
                    for (var z = instruction.minZ; z <= instruction.maxZ; z++) {
                        cubes.put(new Point3D(x, y, z), instruction.on);
                    }
                }
            }
        }
        return countOnCubes(-50, 50, -50, 50, -50, 50, cubes) + "";
    }

    @Override
    public String resultPartTwo() {
        return null;
    }

    private record Instruction(int minX, int maxX,
                               int minY, int maxY,
                               int minZ, int maxZ, boolean on) {

        @Contract(pure = true)
        public int compareMaxX(@NotNull Instruction instruction) {
            return this.maxX - instruction.maxX;
        }

        @Contract(pure = true)
        public int compareMaxY(@NotNull Instruction instruction) {
            return this.maxY - instruction.maxY;
        }

        @Contract(pure = true)
        public int compareMaxZ(@NotNull Instruction instruction) {
            return this.maxZ - instruction.maxZ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || this.getClass() != o.getClass()) return false;
            Instruction that = (Instruction) o;
            return this.minX == that.minX && this.maxX == that.maxX && this.minY == that.minY && this.maxY == that.maxY && this.minZ == that.minZ && this.maxZ == that.maxZ && this.on == that.on;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.minX, this.maxX, this.minY, this.maxY, this.minZ, this.maxZ, this.on);
        }
    }

    private record Point3D(int x, int y, int z) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || this.getClass() != o.getClass()) return false;
            Point3D point3D = (Point3D) o;
            return this.x == point3D.x && this.y == point3D.y && this.z == point3D.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y, this.z);
        }
    }
}
