package aoc2019.Day3;

import utils.ReadAndSaveUtils;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		String string = ReadAndSaveUtils.returnFileContent("src/aoc2019.Day3/InputWires");
		String[] bigWires = string.split("\n");
		String[] wires1 = bigWires[0].split(",");
		WirePiece[] wirePieces1 = new WirePiece[wires1.length];
		String[] wires2 = bigWires[1].split(",");
		WirePiece[] wirePieces2 = new WirePiece[wires2.length];

		generateWires(wires1, wirePieces1);
		generateWires(wires2, wirePieces2);

		Wire wire1 = new Wire(wirePieces1);
		Wire wire2 = new Wire(wirePieces2);

		Set<Position> intersection = new HashSet<>(wire1.getPositions());
		intersection.retainAll(wire2.getPositions());
		intersection.remove(new Position(0, 0));

		int minimumManhattenDistance = Integer.MAX_VALUE;
		for (Position position : intersection) {
			int temp = position.getManhattenDistanceToZero();
			if (minimumManhattenDistance > temp) {
				minimumManhattenDistance = temp;
			}
		}

		System.out.println(minimumManhattenDistance);

		// Task2
		Wire wire1AsArrayList = new Wire(wirePieces1, true);
		Wire wire2AsArrayList = new Wire(wirePieces2, true);

		int feweststeps = Integer.MAX_VALUE;
		for (Position position : intersection) {
			int temp =
					wire1AsArrayList.getPositionsAsArrayList()
					                .indexOf(position)
							+ 1
							+ wire2AsArrayList.getPositionsAsArrayList()
							                  .indexOf(position)
							+ 1;
			if (feweststeps > temp) {
				feweststeps = temp;
			}
		}

		System.out.println(feweststeps);
	}

	// Schleife, welche einen WirePiece in ein WirePieces Array packt
	// und dabei immer das letzte Positionselement vom vorherigen wirePiece als Startpunkt nimmt
	private static void generateWires(String[] wires, WirePiece[] wirePieces) {
		for (int i = 0; i < wires.length; i++) {
			if (i == 0) {
				wirePieces[i] =
						new WirePiece(
								new Position(0, 0), wires[i].charAt(0), Integer.parseInt(wires[i].substring(1)));
			} else {
				wirePieces[i] =
						new WirePiece(
								wirePieces[i - 1].getPositions()[wirePieces[i - 1].getPositions().length - 1],
								wires[i].charAt(0),
								Integer.parseInt(wires[i].substring(1)));
			}
		}
	}
}
