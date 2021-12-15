package utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Dijkstra Algorithm.
 *
 * @param <T> generic value param
 */
public class Dijkstra<T> {

	public static <T> Graph<T> calculateShortestPathFromSource(Graph<T> graph, @NotNull Node<T> source) {
		source.setDistance(0);
		var settledNodes = new HashSet<Node<T>>();
		var unsettledNodes = new HashSet<Node<T>>();
		unsettledNodes.add(source);

		while (unsettledNodes.size() != 0) {
			var currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			currentNode.getAdjacentNodes().forEach((adjacentNode, value) -> {
				if (!settledNodes.contains(adjacentNode)) {
					CalculateMinimumDistance(adjacentNode, value, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			});
			settledNodes.add(currentNode);
		}
		return graph;
	}

	private static <T> void CalculateMinimumDistance(@NotNull Node<T> evaluationNode, Integer edgeWeigh, @NotNull Node<T> sourceNode) {
		var sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			var shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}

	private static <T> Node<T> getLowestDistanceNode(@NotNull Set<Node<T>> unsettledNodes) {
		Node<T> lowestDistanceNode = null;
		var lowestDistance = Integer.MAX_VALUE;
		for (Node<T> node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
	}


	public static class Graph<T> {

		private Set<Node<T>> nodes = new HashSet<>();

		public void addNode(Node<T> nodeA) {
			this.nodes.add(nodeA);
		}

		public Set<Node<T>> getNodes() {
			return this.nodes;
		}

		public void setNodes(Set<Node<T>> nodes) {
			this.nodes = nodes;
		}

		public void addNodes(Collection<Node<T>> values) {
			this.nodes.addAll(values);
		}

		@Override
		public String toString() {
			return "Graph{" +
					"nodes=" + this.nodes +
					'}';
		}
	}

	public static class Node<T> {

		private T value;

		private LinkedList<Node<T>> shortestPath = new LinkedList<>();

		private Integer distance = Integer.MAX_VALUE;

		private Map<Node<T>, Integer> adjacentNodes = new HashMap<>();

		public Node(T value) {
			this.value = value;
		}

		public void addDestination(Node<T> destination, int distance) {
			this.adjacentNodes.put(destination, distance);
		}

		public T getValue() {
			return this.value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public Map<Node<T>, Integer> getAdjacentNodes() {
			return this.adjacentNodes;
		}

		public void setAdjacentNodes(Map<Node<T>, Integer> adjacentNodes) {
			this.adjacentNodes = adjacentNodes;
		}

		public Integer getDistance() {
			return this.distance;
		}

		public void setDistance(Integer distance) {
			this.distance = distance;
		}

		public List<Node<T>> getShortestPath() {
			return this.shortestPath;
		}

		public void setShortestPath(LinkedList<Node<T>> shortestPath) {
			this.shortestPath = shortestPath;
		}

		@Override
		public String toString() {
			return this.value.toString();
		}
	}
}
