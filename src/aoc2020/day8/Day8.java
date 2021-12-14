package aoc2020.day8;

import lombok.Data;
import utils.Day;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Day8 extends Day {

	static int accumulator = 0;
	private ArrayList<InstructionLine> instructionLines;

	public Day8() {
		super(2020, 8);
		this.instructionLines = new ArrayList<>();
		this.readInstructions();
	}

	/**
	 * Reads all instructions in the given file.
	 */
	private void readInstructions() {
		for (int i = 0; i < this.input.size(); i++) {
			String[] tmpInstruction = this.input.get(i)
			                                    .split(" ");
			this.instructionLines.add(
					new InstructionLine(i,
							Integer.parseInt(tmpInstruction[1]),
							Instruction.valueOf(tmpInstruction[0])));
		}
	}

	@Override
	public String resultPartOne() {
		this.executeInstructionsWithoutDuplicating();
		return String.valueOf(accumulator);
	}

	@Override
	public String resultPartTwo() {
		int instructionNumberChanged = -1;
		boolean changed = false;
		while (true) {
			for (int i = instructionNumberChanged + 1; i < this.instructionLines.size(); i++) {
				switch (this.instructionLines.get(i).instruction) {
					case jmp -> {
						this.instructionLines.get(i)
						                     .changeInstruction(Instruction.nop);
						instructionNumberChanged = i;
						changed = true;
					}
					case nop -> {
						this.instructionLines.get(i)
						                     .changeInstruction(Instruction.jmp);
						instructionNumberChanged = i;
						changed = true;
					}
					default -> {
					}
					// do nothing
				}
				if (changed) {
					break;
				}
			}
			if (this.executeInstructionsWithoutDuplicating()) {
				break;
			}
			this.instructionLines = new ArrayList<>();
			this.readInstructions();
			changed = false;
			accumulator = 0;
		}
		return String.valueOf(accumulator);
	}

	/**
	 * Executes all instructions and stops if a instruction would be executed twice.
	 *
	 * @return true if the program terminated correctly, false if not
	 */
	private boolean executeInstructionsWithoutDuplicating() {
		boolean terminated = true;
		for (int i = 0; i < this.instructionLines.size(); ) {
			if (this.instructionLines.get(i).visited) {
				terminated = false;
				break;
			}
			this.instructionLines.get(i).visited = true;
			i += this.instructionLines.get(i)
			                          .execute();
		}
		return terminated;
	}

	enum Instruction {
		acc("acc"), jmp("jmp"), nop("nop");

		private final String text;

		Instruction(String text) {
			this.text = text;
		}

		String getText(Instruction instruction) {
			return instruction.text;
		}
	}

	@Data
	private static class InstructionLine {
		private final int lineNumber;
		private final int value;
		private boolean visited;
		private Instruction instruction;

		InstructionLine(int lineNumber, int value, Instruction instruction) {
			this.lineNumber = lineNumber;
			this.value = value;
			this.instruction = instruction;
		}

		/**
		 * Executes the given Instruction.
		 *
		 * @return the skip value,
		 * where +1 means it will execute the next instruction
		 * and +2 will skip the next one
		 */
		int execute() {
			switch (this.instruction) {
				case acc:
					accumulator += this.value;
					return 1;
				case jmp:
					return this.value;
				case nop:
					return 1;
				default:
					Logger.getGlobal()
					      .info("No executable instruction found.");
			}
			return 0;
		}

		void changeInstruction(Instruction newInstruction) {
			this.instruction = newInstruction;
		}
	}
}
