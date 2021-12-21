package aoc2020.day17;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day17 extends Day {

    private HashMap<Coordinate3D, Boolean> cubes3D;
    private HashMap<Coordinate4D, Boolean> cubes4D;
    private int yMax;
    private int zMax;
    private int wMax;

    public Day17() {
        super(2020, 17);
        this.initCubes3D(this.input);
        this.initCubes4D(this.input);
    }

    @Override
    public String resultPartOne() {
        return this.solve3D(false);
    }

    @Override
    public String resultPartTwo() {
        return this.solve4D(true);
    }

    private @NotNull String solve3D(boolean printCubes) {
        if (printCubes) {
            System.out.println("Initial state:");
            this.printCubes3D();
        }
        for (var i = 1; i <= 6; i++) {
            this.generateNeighborCubes3D();
            this.doCycle3D();
            if (printCubes) {
                System.out.println("After " + i + " cycle" + ((i == 1) ? "" : "s") + ":");
                this.printCubes3D();
            }
        }
        return String.valueOf(this.cubes3D.values()
                .stream()
                .filter(aBoolean -> aBoolean)
                .count());
    }

    private @NotNull String solve4D(boolean printCubes) {
        if (printCubes) {
            System.out.println("Initial state:");
            this.printCubes4D();
        }
        for (var i = 1; i <= 6; i++) {
            System.out.println("Cycle " + i + " ...");
            this.generateNeighborCubes4D();
            this.doCycle4D();
            if (printCubes) {
                System.out.println("After " + i + " cycle" + ((i == 1) ? "" : "s") + ":");
                System.out.println();
                this.printCubes4D();
            }
        }
        return String.valueOf(this.cubes4D.values()
                .stream()
                .filter(aBoolean -> aBoolean)
                .count());
    }

    private void initCubes3D(@NotNull List<String> input) {
        this.yMax = input.get(0)
                .length();
        this.zMax = 0;
        this.cubes3D = new HashMap<>();
        for (var x = 0; x < this.yMax; x++) {
            var line = input.get(x)
                    .split("");
            for (var y = 0; y < this.yMax; y++) {
                var active = "#".equals(line[y]);
                this.cubes3D.put(new Coordinate3D(x - this.yMax / 2, y - this.yMax / 2, 0), active);
            }
        }
    }

    private void initCubes4D(@NotNull List<String> input) {
        this.yMax = input.get(0)
                .length();
        this.zMax = 0;
        this.wMax = 0;
        this.cubes4D = new HashMap<>();
        for (var x = 0; x < this.yMax; x++) {
            var line = input.get(x)
                    .split("");
            for (var y = 0; y < this.yMax; y++) {
                var active = "#".equals(line[y]);
                this.cubes4D.put(new Coordinate4D(x - this.yMax / 2, y - this.yMax / 2, 0, 0), active);
            }
        }
    }

    private void printCubes3D() {
        for (var z = -this.zMax; z <= this.zMax; z++) {
            System.out.println("z=" + z);
            for (var x = -this.yMax; x <= this.yMax; x++) {
                for (var y = -this.yMax; y <= this.yMax; y++) {
                    var tmpCoord = this.cubes3D.get(new Coordinate3D(x, y, z));
                    if (tmpCoord != null) {
                        System.out.print(tmpCoord ? "#" : ".");
                    }
                }
                System.out.println();
            }
        }
    }

    private void printCubes4D() {
        for (var w = -this.wMax; w <= this.wMax; w++) {
            for (var z = -this.zMax; z <= this.zMax; z++) {
                System.out.println("z=" + z + ", w=" + w);
                for (var x = -this.yMax; x <= this.yMax; x++) {
                    for (var y = -this.yMax; y <= this.yMax; y++) {
                        var tmpCoord = this.cubes4D.get(new Coordinate4D(x, y, z, w));
                        if (tmpCoord != null) {
                            System.out.print(tmpCoord ? "#" : ".");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    private void generateNeighborCubes3D() {
        this.yMax++;
        this.zMax++;
        var cubesToAdd = new HashMap<Coordinate3D, Boolean>();
        for (Coordinate3D coordinate3D : this.cubes3D.keySet()) {
            for (var x = coordinate3D.x - 1; x <= coordinate3D.x + 1; x++) {
                for (var y = coordinate3D.y - 1; y <= coordinate3D.y + 1; y++) {
                    for (var z = coordinate3D.z - 1; z <= coordinate3D.z + 1; z++) {
                        if (coordinate3D.sameCoordinates(x, y, z)) continue;
                        var tmpCoord = new Coordinate3D(x, y, z);
                        if (this.cubes3D.get(tmpCoord) == null) {
                            cubesToAdd.put(tmpCoord, false);
                        }
                    }
                }
            }
        }
        this.cubes3D.putAll(cubesToAdd);
    }

    private void generateNeighborCubes4D() {
        this.yMax++;
        this.zMax++;
        var cubesToAdd = new HashMap<Coordinate4D, Boolean>();
        for (Coordinate4D coordinate4D : this.cubes4D.keySet()) {
            for (var x = coordinate4D.x - 1; x <= coordinate4D.x + 1; x++) {
                for (var y = coordinate4D.y - 1; y <= coordinate4D.y + 1; y++) {
                    for (var z = coordinate4D.z - 1; z <= coordinate4D.z + 1; z++) {
                        for (var w = coordinate4D.w - 1; w <= coordinate4D.w + 1; w++) {
                            if (coordinate4D.sameCoordinates(x, y, z, w)) {
                                continue;
                            }
                            var tmpCoord = new Coordinate4D(x, y, z, w);
                            if (this.cubes4D.get(tmpCoord) == null) {
                                cubesToAdd.put(tmpCoord, false);
                            }
                        }
                    }
                }
            }
        }
        this.cubes4D.putAll(cubesToAdd);
    }

    private void doCycle3D() {
        var tmpCubes = new HashMap<Coordinate3D, Boolean>();
        for (Coordinate3D coordinate3D : this.cubes3D.keySet()) {
            var numActiveNeighbors = 0;
            for (Coordinate3D coordinate3D1 : coordinate3D.getNeighborCubes()) {
                var active = this.cubes3D.get(coordinate3D1);
                if (active != null && active) {
                    numActiveNeighbors++;
                }
            }
            if (this.cubes3D.get(coordinate3D)) {
                switch (numActiveNeighbors) {
                    case 2:
                    case 3:
                        break;
                    default:
                        tmpCubes.put(new Coordinate3D(coordinate3D), false);
                        break;
                }
            } else {
                if (numActiveNeighbors == 3) {
                    tmpCubes.put(new Coordinate3D(coordinate3D), true);
                }
            }
        }
        this.cubes3D.putAll(tmpCubes);
    }

    private void doCycle4D() {
        var tmpCubes = new HashMap<Coordinate4D, Boolean>();
        for (Coordinate4D coordinate4D : this.cubes4D.keySet()) {
            var numActiveNeighbors = 0;
            for (Coordinate4D coordinate4D1 : coordinate4D.getNeighborCubes()) {
                var active = this.cubes4D.get(coordinate4D1);
                if (active != null && active) {
                    numActiveNeighbors++;
                }
            }
            if (this.cubes4D.get(coordinate4D)) {
                switch (numActiveNeighbors) {
                    case 2:
                    case 3:
                        break;
                    default:
                        tmpCubes.put(new Coordinate4D(coordinate4D), false);
                        break;
                }
            } else {
                if (numActiveNeighbors == 3) {
                    tmpCubes.put(new Coordinate4D(coordinate4D), true);
                }
            }
        }
        this.cubes4D.putAll(tmpCubes);
    }

    private record Coordinate3D(int x, int y, int z) {
        public Coordinate3D(@NotNull Coordinate3D coordinate3D) {
            this(coordinate3D.x, coordinate3D.y, coordinate3D.z);
        }

        private @NotNull List<Coordinate3D> getNeighborCubes() {
            var res = new ArrayList<Coordinate3D>();
            for (var x = -1; x <= 1; x++) {
                var neighborX = this.x + x;
                for (var y = -1; y <= 1; y++) {
                    var neighborY = this.y + y;
                    for (var z = -1; z <= 1; z++) {
                        var neighborZ = this.z + z;
                        if (this.x != neighborX ||
                                this.y != neighborY ||
                                this.z != neighborZ) {
                            res.add(new Coordinate3D(neighborX, neighborY, neighborZ));
                        }
                    }
                }
            }
            return res;
        }

        public boolean sameCoordinates(int x, int y, int z) {
            return this.x == x &&
                    this.y == y &&
                    this.z == z;
        }
    }

    private record Coordinate4D(int x, int y, int z, int w) {
        public Coordinate4D(@NotNull Coordinate4D coordinate4D) {
            this(coordinate4D.x, coordinate4D.y, coordinate4D.z, coordinate4D.w);
        }

        private @NotNull List<Coordinate4D> getNeighborCubes() {
            var res = new ArrayList<Coordinate4D>();
            for (var x = -1; x <= 1; x++) {
                var neighborX = this.x + x;
                for (var y = -1; y <= 1; y++) {
                    var neighborY = this.y + y;
                    for (var z = -1; z <= 1; z++) {
                        var neighborZ = this.z + z;
                        for (var w = -1; w <= 1; w++) {
                            var neighborW = this.w + w;
                            if (this.x != neighborX ||
                                    this.y != neighborY ||
                                    this.z != neighborZ ||
                                    this.w != neighborW) {
                                res.add(new Coordinate4D(neighborX, neighborY, neighborZ, neighborW));
                            }
                        }
                    }
                }
            }
            return res;
        }

        public boolean sameCoordinates(int x, int y, int z, int w) {
            return this.x == x &&
                    this.y == y &&
                    this.z == z &&
                    this.w == w;
        }
    }
}
