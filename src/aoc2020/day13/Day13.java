package aoc2020.day13;

import utils.Day;

import java.util.ArrayList;

public class Day13 extends Day {

	private final int earliestEstimatedTimeArriving;
	private final ArrayList<Bus> busses;

	public Day13() {
		super(2020, 13);
		this.busses = new ArrayList<>();
		this.earliestEstimatedTimeArriving = Integer.parseInt(this.input.get(0));
		for (String c : this.input.get(1).split(",")) {
			if (!c.equals("x")) {
				this.busses.add(new Bus(Integer.parseInt(c)));
			}
		}
	}

	@Override
	public String resultPartOne() {
		int i = this.earliestEstimatedTimeArriving;
		boolean found = false;
		Bus takenBus = null;
		while (!found) {
			for (Bus bus : this.busses) {
				if (bus.canDepartAtTimestamp(i)) {
					takenBus = bus;
					found = true;
					break;
				}
			}
			if (!found) {
				i++;
			}
		}
		return (i - this.earliestEstimatedTimeArriving) * takenBus.busID + "";
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	private record Bus(int busID) {
		public boolean canDepartAtTimestamp(int timestamp) {
			return timestamp % this.busID == 0;
		}
	}
}
