package aoc2020.day22;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Day22 extends Day {

	private Queue<Integer> playerOneQueue;
	private Queue<Integer> playerTwoQueue;
	private List<Integer> playerOneList;
	private List<Integer> playerTwoList;

	public Day22() {
		super(2020, 22);

		var playerOne = true;
		for (var s : this.input) {
			if (s.isEmpty()) continue;
			if (s.equals("Player 1:")) {
				this.playerOneQueue = new ArrayDeque<>();
				this.playerOneList = new ArrayList<>();
				continue;
			}
			if (s.equals("Player 2:")) {
				this.playerTwoQueue = new ArrayDeque<>();
				this.playerTwoList = new ArrayList<>();
				playerOne = false;
				continue;
			}
			if (playerOne) {
				this.playerOneQueue.add(Integer.parseInt(s));
				this.playerOneList.add(Integer.parseInt(s));
			} else {
				this.playerTwoQueue.add(Integer.parseInt(s));
				this.playerTwoList.add(Integer.parseInt(s));
			}
		}
	}

	@Override
	public String resultPartOne() {
		return this.gamePartOne() + "";
	}

	@Override
	public String resultPartTwo() {
		var game = this.gamePartTwo(this.playerOneList, this.playerTwoList);
		System.out.println("Player " + game.numWinner + " has won with the deck: ");
		game.winnerList.forEach(integer -> System.out.print(integer + ","));
		System.out.println();
		return this.calculateWinningScore(game.winnerList) + "";
	}

	private long gamePartOne() {
		while (!this.playerOneQueue.isEmpty() && !this.playerTwoQueue.isEmpty()) {
			if (this.playerOneQueue.peek() < this.playerTwoQueue.peek()) {
				this.playerTwoQueue.add(this.playerTwoQueue.poll());
				this.playerTwoQueue.add(this.playerOneQueue.poll());
			} else {
				this.playerOneQueue.add(this.playerOneQueue.poll());
				this.playerOneQueue.add(this.playerTwoQueue.poll());
			}
		}
		if (this.playerOneQueue.isEmpty()) return this.calculateWinningScore(2);
		return this.calculateWinningScore(1);
	}

	private long calculateWinningScore(int player) {
		long res = 0;
		if (player == 1) {
			var i = this.playerOneQueue.size();
			while (!this.playerOneQueue.isEmpty()) {
				res += (long) this.playerOneQueue.poll() * i;
				i--;
			}
		}
		if (player == 2) {
			var i = this.playerTwoQueue.size();
			while (!this.playerTwoQueue.isEmpty()) {
				res += (long) this.playerTwoQueue.poll() * i;
				i--;
			}
		}
		return res;
	}

	private long calculateWinningScore(@NotNull List<Integer> list) {
		long res = 0;
		for (var i = 0; i < list.size(); i++) {
			var tmp = (long) list.get(i);
			var factor = (list.size() - i);
			res += (tmp * factor);
		}
		return res;
	}

	private @NotNull Game gamePartTwo(@NotNull List<Integer> playerOneList, List<Integer> playerTwoList) {
		var playerOneLists = new ArrayList<List<Integer>>();
		var playerTwoLists = new ArrayList<List<Integer>>();
		while (!playerOneList.isEmpty() && !playerTwoList.isEmpty()) {
			if (playerOneLists.stream().anyMatch(list -> list.equals(playerOneList)) &&
					playerTwoLists.stream().anyMatch(list -> list.equals(playerTwoList))) {
				return new Game(1, playerOneList, playerTwoList);
			}
			playerOneLists.add(new ArrayList<>(playerOneList));
			playerTwoLists.add(new ArrayList<>(playerTwoList));
			if ((playerOneList.get(0) == playerOneList.size() - 1 || playerTwoList.get(0) == playerTwoList.size() - 1)
					&& playerOneList.get(0) <= playerOneList.size() && playerTwoList.get(0) <= playerTwoList.size()) {
				var game = this.gamePartTwo(
						new ArrayList<>(playerOneList.subList(1, playerOneList.get(0) + 1)),
						new ArrayList<>(playerTwoList.subList(1, playerTwoList.get(0) + 1))
				);
				if (game.numWinner == 1) {
					playerOneList.add(playerOneList.remove(0));
					playerOneList.add(playerTwoList.remove(0));
				} else {
					playerTwoList.add(playerTwoList.remove(0));
					playerTwoList.add(playerOneList.remove(0));
				}
				continue;
			}
			if (playerOneList.get(0) <= playerTwoList.get(0)) {
				playerTwoList.add(playerTwoList.remove(0));
				playerTwoList.add(playerOneList.remove(0));
			} else {
				playerOneList.add(playerOneList.remove(0));
				playerOneList.add(playerTwoList.remove(0));
			}
		}
		return playerOneList.isEmpty() ?
				new Game(2, playerTwoList, playerOneList) :
				new Game(1, playerOneList, playerTwoList);
	}

	private static record Game(int numWinner, List<Integer> winnerList, List<Integer> loserList) {
	}
}
