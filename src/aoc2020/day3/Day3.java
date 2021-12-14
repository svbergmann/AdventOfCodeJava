package aoc2020.day3;

import utils.Day;

import java.math.BigInteger;
import java.util.ArrayList;

public class Day3 extends Day {

	private final boolean[][] geology;
	private final int sizeOfLines;
	private final int sizeOfArray;

	public Day3() {
		super(2020, 3);
		ArrayList<String> arrayList = this.input;
		this.sizeOfLines = arrayList.size() * 10;
		this.sizeOfArray = arrayList.size();
		// Geology array, where true means a tree an false means a free space
		this.geology = new boolean[this.sizeOfArray][this.sizeOfLines];
		for (int i = 0; i < this.geology.length; i++) {
			char[] tmp2 = arrayList.get(i)
			                       .toCharArray();
			char[] tmp = new char[this.sizeOfLines];
			int tmp2Counter = 0;
			for (int k = 0; k < this.sizeOfLines; k++) {
				tmp[k] = tmp2[tmp2Counter];
				tmp2Counter++;
				if (tmp2Counter == tmp2.length) {
					tmp2Counter = 0;
				}
			}
			for (int j = 0; j < this.sizeOfLines; j++) {
				if (tmp[j] == '#') {
					this.geology[i][j] = true;
				}
			}
		}
	}

	@Override
	public String resultPartOne() {
		return "There are " + this.numberOfTrees(1, 3) + " trees.";
	}

	@Override
	public String resultPartTwo() {
		BigInteger one = new BigInteger(this.numberOfTrees(1, 1) + "");
		BigInteger two = new BigInteger(this.numberOfTrees(1, 3) + "");
		BigInteger three = new BigInteger(this.numberOfTrees(1, 5) + "");
		BigInteger four = new BigInteger(this.numberOfTrees(1, 7) + "");
		BigInteger five = new BigInteger(this.numberOfTrees(2, 1) + "");
		return one.multiply(two)
		          .multiply(three)
		          .multiply(four)
		          .multiply(five)
		          .toString();
	}

	private int numberOfTrees(int down, int right) {
		int counter = 0;
		int j = right;
		for (int i = down; i < this.sizeOfArray && j < this.sizeOfLines; i += down) {
			if (this.geology[i][j]) {
				counter++;
			}
			j += right;
		}
		return counter;
	}
}
