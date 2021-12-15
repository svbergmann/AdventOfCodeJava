package aoc2021.day15;

import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Dijkstra;
import utils.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day15 extends Day {

	private Dijkstra.Graph<Point> graph;
	private Point bottomRightPosition;
	private Point topLeftPosition;
	private int heightOfMap;
	private int lengthOfMap;

	public Day15() {
		super(2021, 15);
	}

	public void generateNewInput(@NotNull List<String> list) {
		var caverns = new HashMap<Point, Integer>();
		for (var i = 0; i < list.size(); i++) {
			var line = list.get(i).toCharArray();
			for (var j = 0; j < list.get(i).length(); j++) {
				var point = new Point(j, i);
				caverns.put(point, Integer.parseInt(String.valueOf(line[j])));
				if (i == list.size() - 1 && j == line.length - 1) {
					this.bottomRightPosition = point;
					this.heightOfMap = i + 1;
					this.lengthOfMap = j + 1;
				}
				if (i == 0 && j == 0) {
					this.topLeftPosition = point;
				}
			}
		}
		var time = System.currentTimeMillis();
		this.graph = new Dijkstra.Graph<>();
		var nodes = new HashSet<Dijkstra.Node<Point>>();
		caverns.forEach((key, value) -> {
			var start = nodes.stream()
			                 .filter(node -> node.getValue().equals(key))
			                 .findFirst()
			                 .orElseGet(() -> new Dijkstra.Node<>(key));
			key.getAdjacent(this.heightOfMap, this.lengthOfMap, false).forEach(adjacentCavern -> {
				var dest = nodes.stream()
				                .filter(node -> node.getValue().equals(adjacentCavern))
				                .findFirst()
				                .orElseGet(() -> new Dijkstra.Node<>(adjacentCavern));
				start.addDestination(dest, caverns.get(adjacentCavern));
				nodes.add(dest);
			});
			nodes.add(start);
		});
		System.out.println(System.currentTimeMillis() - time + " millisec");
		this.graph.addNodes(nodes);
	}

	@Override
	public String resultPartOne() {
		this.generateNewInput(this.input);
		Dijkstra.calculateShortestPathFromSource(this.graph, this.graph.getNodes()
		                                                               .stream()
		                                                               .filter(node -> node.getValue().equals(this.topLeftPosition))
		                                                               .findFirst()
		                                                               .orElseGet(() -> new Dijkstra.Node<>(this.topLeftPosition)));
		return this.graph.getNodes().stream()
		                 .filter(pointNode -> pointNode.getValue().equals(this.bottomRightPosition))
		                 .map(Dijkstra.Node::getDistance).toList().get(0) + "";
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

}
