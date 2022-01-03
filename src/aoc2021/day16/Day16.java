package aoc2021.day16;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.List;

public class Day16 extends Day {
    public Day16() {
        super(2021, 16);
    }

    @Override
    public String resultPartOne() {
        var parent = this.processPackageString(this.hexToBin(this.example.get(0)), null);
        System.out.println(parent);
        return parent.getVersionSum() + "";

    }

    @Override
    public String resultPartTwo() {
        return null;
    }

    private Package processPackageString(String s, Package parent) {
        var packetVersion = Integer.parseInt(s.substring(0, 3), 2);
        var packetTypeID = Integer.parseInt(s.substring(3, 6), 2);
        var i = 0;
        boolean first = parent == null;
        if (first) parent = new Package(packetVersion, packetTypeID);
        while (i < s.length()) {
            var tmpPackage = new Package(packetVersion, packetTypeID);
            label:
            switch (packetTypeID) {
                case 4 -> {
                    if (s.length() - i <= 6) break;
                    packetVersion = Integer.parseInt(s.substring(i, i += 3), 2);
                    packetTypeID = Integer.parseInt(s.substring(i, i += 3), 2);
                    var stringToBeProcessed = new StringBuilder(s.substring(i));
                    var binNumber = "";
                    var j = 0;
                    while (j < stringToBeProcessed.length()) {
                        if (stringToBeProcessed.charAt(j) == '1') {
                            binNumber += stringToBeProcessed.substring(j += 1, j += 4);
                            i += 5;
                        } else {
                            binNumber += stringToBeProcessed.substring(j += 1, j += 4);
                            i += 5;
                            break;
                        }
                    }
                    if (first) {
                        parent.setNumber(Integer.parseInt(binNumber, 2));
                    } else {
                        tmpPackage.setNumber(Integer.parseInt(binNumber, 2));
                        parent.addSubPackage(tmpPackage);
                    }
                }
                default -> {
                    i += 6;
                    if (s.length() - i <= 0) break;
                    switch (s.charAt(i)) {
                        case '0' -> {
                            if (s.length() - i <= 15) break label;
                            var totalLength = Integer.parseInt(s.substring(i += 1, i += 15), 2);
                            this.processPackageString(s.substring(i, i += totalLength), tmpPackage);
                        }
                        case '1' -> {
                            if (s.length() - i <= 11) break label;
                            var lengthOfSubPackagesBitString = 11;
                            var tmp = s.substring(7, 18);
                            var numberOfSubPackages = Integer.parseInt(s.substring(i += 1, i += 11), 2);
                            if (s.length() - (i + lengthOfSubPackagesBitString) <= 0) break label;
                            for (var j = 0; j < numberOfSubPackages; j++) {
                                this.processPackageString(s.substring(i, i += lengthOfSubPackagesBitString), tmpPackage);
                            }
                        }
                    }
                    parent.addSubPackage(tmpPackage);
                }
            }
        }
        return parent;
    }

    private @NotNull String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    private String getHexString(String binary) {
        return Integer.toString(Integer.parseInt(binary, 2), 16);
    }

    private static class Package {
        private final int packetVersion;
        private final int packetTypeID;
        private final List<Package> subPackages;
        private int number;

        public Package(int packetVersion, int packetTypeID) {
            this.packetVersion = packetVersion;
            this.packetTypeID = packetTypeID;
            this.subPackages = new ArrayList<>();
        }


        public boolean canContainOtherPackages() {
            return this.packetTypeID != 4;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getPacketVersion() {
            return this.packetVersion;
        }

        public int getPacketTypeID() {
            return this.packetTypeID;
        }

        public List<Package> getSubPackages() {
            return this.subPackages;
        }

        public void addSubPackage(Package subPackage) {
            this.subPackages.add(subPackage);
        }

        @Override
        public String toString() {
            return "Package{" +
                    "packetVersion=" + this.packetVersion +
                    ", packetTypeID=" + this.packetTypeID +
                    ", subPackages=" + this.subPackages +
                    ", number=" + this.number +
                    '}';
        }

        public int getVersionSum() {
            var res = 0;
            if (this.subPackages.isEmpty()) {
                return this.packetVersion;
            } else {
                for (var p : this.subPackages) {
                    res += p.getVersionSum();
                }
            }
            return res;
        }
    }
}
