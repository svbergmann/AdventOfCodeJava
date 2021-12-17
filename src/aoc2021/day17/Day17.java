package aoc2021.day17;

import utils.Day;

public class Day17 extends Day {

	private final int minX;
	private final int maxX;
	private final int minY;
	private final int maxY;

	public Day17() {
		super(2021, 17);
		var line = this.example.get(0);
		var xValues = line.split(":")[1].split(",")[0].split("=")[1].split("..");
		this.minX = Integer.parseInt(xValues[0]);
		this.maxX = Integer.parseInt(xValues[1]);
		var yValues = line.split(":")[1].split(",")[1].split("=")[1].split("..");
		this.minY = Integer.parseInt(yValues[0]);
		this.maxY = Integer.parseInt(yValues[1]);
	}

	@Override
	public String resultPartOne() {
		return null;
	}


	@Override
	public String resultPartTwo() {
		return null;
	}

	private static class Probe {
		private final int startX;
		private final int startY;
		private int xVelocity;
		private int yVelocity;
		private int currentX;
		private int currentY;

		public Probe(int x, int y, int xVelocity, int yVelocity) {
			this.startX = x;
			this.startY = y;
			this.xVelocity = xVelocity;
			this.yVelocity = yVelocity;
			this.currentX = x;
			this.currentY = y;
		}

		private void doStep() {
			this.currentX += this.xVelocity;
			this.currentY += this.yVelocity;
			if (this.xVelocity != 0) this.xVelocity = this.xVelocity < 0 ? +1 : -1;
			this.yVelocity -= 1;
		}

		private void computeHighestPoint() {
			var lastX = this.currentX;
			var lastY = this.currentY;
			do {
				this.doStep();
			} while (lastY < this.currentY);
		}

		private boolean isInsideArea(int minx, int maxX, int minY, int maxY) {

		}
	}
}
