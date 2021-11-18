package aoc2019.Day3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Wire {

	private final Set<Position> positions = new HashSet<>();

	private final ArrayList<Position> positionsAsArrayList = new ArrayList<>();

	public Wire(WirePiece[] wirePieces) {
		for (WirePiece wirePiece : wirePieces) {
			positions.addAll(Arrays.asList(wirePiece.getPositions()));
		}
	}

	public Wire(WirePiece[] wirePieces, boolean array) {
		for (WirePiece wirePiece : wirePieces) {
			positionsAsArrayList.addAll(Arrays.asList(wirePiece.getPositions()));
		}
	}

	@Override
	public String toString() {
		return "Wire{"
				+ "positions="
				+ positions
				+ ", positionsAsArrayList="
				+ positionsAsArrayList
				+ '}';
	}

	public ArrayList<Position> getPositionsAsArrayList() {
		return positionsAsArrayList;
	}

	public Set<Position> getPositions() {
		return positions;
	}
}
