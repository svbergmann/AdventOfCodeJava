package aoc2021.day12;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.*;


public class Day12 extends Day {

    private final HashSet<Cave> caves;

    public Day12() {
        super(2021, 12);
        this.caves = new HashSet<>();
        for (var s : this.example) {
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
        }
    }

    @Override
    public String resultPartOne() {
        Cave startCave = null;
        for (var cave : this.caves) {
            if (cave.name.equals("start")) startCave = cave;
        }
        var res = this.getAllPaths(startCave);

        return res.toString();
    }

    private HashSet<ArrayList<Cave>> getAllPaths(Cave root) {
        var st = new Stack<Cave>();
        var res = new HashSet<ArrayList<Cave>>();
        st.push(root);

        while (!st.isEmpty()) {
            var top = st.peek();
            if (top.connections.isEmpty() || top.visited) {
                st.pop();
            } else {
                for (var connection : top.connections) {
                    if (connection != null) {
                        if (connection.name.equals("end")) {
                            st.push(connection);
                            var tmpList = new ArrayList<>(st);
                            res.add(tmpList);
                            st.pop();
                            System.out.println(tmpList);
                            break;
                        } else if (!connection.visited) {
                            st.push(connection);
                            if (!connection.bigCave) connection.visited = true;
                            break;
                        } else {
                            st.push(connection);
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    @Override
    public String resultPartTwo() {
        return null;
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
            this.bigCave = Character.isUpperCase(name.toCharArray()[0]);
        }

        public void addConnection(Cave cave) {
            this.connections.add(cave);
        }

        public boolean canMove(Cave cave) {
            return this.connections.contains(cave);
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
            return "Cave{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
