package aoc2021.day15;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Dijkstra;
import utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Day15 extends Day {

	public Day15() {
		super(2021, 15);
	}

	public Dijkstra.Graph<Point> generateNewGraph(@NotNull List<String> list) {
		var riskLevels = new int[list.size()][list.get(0).length()];
		var nodesMap = new HashMap<Point, Dijkstra.Node<Point>>();

		for (var i = 0; i < list.size(); i++) {
			var line = list.get(i).toCharArray();
			Point tmpPoint;
			for (var j = 0; j < list.get(i).length(); j++) {
				riskLevels[i][j] = Integer.parseInt(String.valueOf(line[j]));
				tmpPoint = new Point(i, j);
				nodesMap.put(tmpPoint, new Dijkstra.Node<>(tmpPoint));
			}
		}
		var graph = new Dijkstra.Graph<Point>();
		for (var i = 0; i < riskLevels.length; i++) {
			Dijkstra.Node<Point> startNode;
			Point tmpPoint;
			for (var j = 0; j < riskLevels[i].length; j++) {
				tmpPoint = new Point(i, j);
				startNode = nodesMap.get(tmpPoint);
				Dijkstra.Node<Point> destNode;
				for (var adjacent : tmpPoint.getAdjacent(riskLevels.length, riskLevels[i].length, false)) {
					destNode = nodesMap.get(adjacent);
					startNode.addDestination(destNode, riskLevels[adjacent.x()][adjacent.y()]);
				}
				if (i == 0 && j == 0) graph.setStartNode(startNode);
				if (i == riskLevels.length - 1 && j == riskLevels[i].length - 1) graph.setEndNode(startNode);
			}
		}
		graph.addNodes(nodesMap.values());
		return graph;
	}

	@Override
	public String resultPartOne() {
		var graph = this.generateNewGraph(this.input);
		Dijkstra.calculateShortestPathFromSource(graph, graph.getStartNode());
		return graph.getEndNode().getDistance() + "";
	}

	@Override
	public String resultPartTwo() {
		var graph = this.generateNewGraph(this.generateBigInputString());
		Dijkstra.calculateShortestPathFromSource(graph, graph.getStartNode());
		return graph.getEndNode().getDistance() + "";
	}

	@Contract(" -> new")
	private @NotNull List<String> generateBigInputString() {
		var cols = new String[5];
		for (var i = 0; i < cols.length; i++) {
			cols[i] = this.incrementEveryone(this.input, i) + this.incrementEveryone(this.input, i + 1)
					+ this.incrementEveryone(this.input, i + 2) + this.incrementEveryone(this.input, i + 3)
					+ this.incrementEveryone(this.input, i + 4);
		}
		var superInput = cols[0];
		for (var i = 1; i < cols.length; i++) {
			superInput = this.concatLines(superInput, cols[i]);
		}
		return new ArrayList<>(Arrays.asList(superInput.split("\n")));
	}

	private @NotNull String incrementEveryone(@NotNull List<String> incre, int increment) {
		var riskLevel = new int[incre.size()][];
		for (var i = 0; i < incre.size(); i++) {
			var s = incre.get(0);
			var split1 = s.split("");
			riskLevel[i] = new int[split1.length];
			for (var j = 0; j < split1.length; j++) {
				var s1 = split1[j];
				riskLevel[i][j] = Integer.parseInt(s1);
				for (var k = 0; k < increment; k++) {
					riskLevel[i][j] = riskLevel[i][j] % 9 + 1;
				}
			}
		}

		var sb = new StringBuilder();
		for (int[] ints : riskLevel) {
			for (int anInt : ints) {
				sb.append(anInt);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private @NotNull String concatLines(@NotNull String original, @NotNull String addRight) {
		var split = original.split("\n");
		var splitRight = addRight.split("\n");
		var concat = IntStream.range(0, split.length)
		                      .mapToObj(i -> split[i] + splitRight[i])
		                      .toArray(String[]::new);
		return String.join("\n", concat);
	}

}
