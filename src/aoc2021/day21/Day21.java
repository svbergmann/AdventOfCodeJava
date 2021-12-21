package aoc2021.day21;

import utils.Day;

public class Day21 extends Day {
    public Day21() {
        super(2021, 21);
    }

    @Override
    public String resultPartOne() {
        var player1 = new Player(Integer.parseInt(this.input.get(0).split(":")[1].trim()), 0);
        var player2 = new Player(Integer.parseInt(this.input.get(1).split(":")[1].trim()), 0);
        var dice = new DeterministicDice(1, 100);
        var times = 0;
        while (true) {
            times = dice.roll() + dice.roll() + dice.roll();
            player1.move(times);
            System.out.println("Player 1 moves to space " + player1.space + " for a total score of " + player1.score + ".");
            if (player1.score >= 1000) break;
            times = dice.roll() + dice.roll() + dice.roll();
            player2.move(times);
            System.out.println("Player 2 moves to space " + player2.space + " for a total score of " + player2.score + ".");
            if (player2.score >= 1000) break;
        }
        var losingPlayer = (player1.score < player2.score) ? player1 : player2;
        return losingPlayer.score + " * " + dice.count + " = " + losingPlayer.score * dice.count;
    }

    @Override
    public String resultPartTwo() {
        return null;
    }

    private static class Player {
        private final int startSpace;
        private int score;
        private int space;

        public Player(int startSpace, int startScore) {
            this.startSpace = startSpace;
            this.space = startSpace;
            this.score = startScore;
        }

        public int move(int times) {
            for (var i = 0; i < times; i++) {
                this.space++;
                if (this.space > 10) this.space = 1;
            }
            this.score += this.space;
            return this.score;
        }
    }

    private static class DeterministicDice {
        private final int numberOfSides;
        private final int start;
        private int value;
        private int count;

        public DeterministicDice(int start, int numberOfSides) {
            this.value = start;
            this.count = 0;
            this.numberOfSides = numberOfSides;
            this.start = start;
        }

        public int roll() {
            if (this.count == 0) {
                this.count++;
                return this.value;
            }
            this.value++;
            this.count++;
            if (this.value > this.numberOfSides) this.value = this.start;
            return this.value;
        }
    }
}
