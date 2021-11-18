package aoc2020.day4;

import lombok.Data;
import lombok.Getter;
import utils.Day;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Day4 extends Day {

	private final ArrayList<Passport> passports;

	public Day4() {
		super(2020, 4);
		this.passports = new ArrayList<>();
		ArrayList<ArrayList<String>> splittedLines = new ArrayList<>();
		int splittedLinesCounter = 0;
		for (String value : this.input) {
			if (value.isEmpty()) {
				splittedLinesCounter++;
				continue;
			}
			try {
				if (splittedLines.get(splittedLinesCounter) == null) {
					splittedLines.add(new ArrayList<>());
				}
			} catch (IndexOutOfBoundsException e) {
				splittedLines.add(new ArrayList<>());
			}
			for (String s : value.split("\\s")) {
				splittedLines.get(splittedLinesCounter).add(s);
			}
		}
		splittedLines.forEach(
				strings -> {
					Passport passport = new Passport();
					strings.forEach(
							s -> {
								String attribute = s.split(":")[0];
								String value = s.split(":")[1];
								if (attribute.matches("byr")) {
									try {
										passport.byr = Integer.parseInt(value);
									} catch (NumberFormatException e) {
										Logger.getGlobal().severe("Cannot parse byr: " + value + " to int.");
									}
								} else if (attribute.matches("iyr")) {
									try {
										passport.iyr = Integer.parseInt(value);
									} catch (NumberFormatException e) {
										Logger.getGlobal().severe("Cannot parse iyr: " + value + " to int.");
									}
								} else if (attribute.matches("eyr")) {
									try {
										passport.eyr = Integer.parseInt(value);
									} catch (NumberFormatException e) {
										Logger.getGlobal().severe("Cannot parse eyr: " + value + " to int.");
									}
								} else if (attribute.matches("hgt")) {
									passport.hgt = value;
								} else if (attribute.matches("hcl")) {
									passport.hcl = value;
								} else if (attribute.matches("ecl")) {
									passport.ecl = value;
								} else if (attribute.matches("pid")) {
									passport.pid = value;
								} else if (attribute.matches("cid")) {
									try {
										passport.cid = Integer.parseInt(value);
									} catch (NumberFormatException e) {
										Logger.getGlobal().severe("Cannot parse cid: " + value + " to int.");
									}
								}
							});
					this.passports.add(passport);
				});
	}

	@Override
	public String resultPartOne() {
		int counter = 0;
		for (Passport p : this.passports) {
			if (p.isPassportValidPartOne()) {
				counter++;
			}
		}
		return String.valueOf(counter);
	}

	@Override
	public String resultPartTwo() {
		int counter = 0;
		for (Passport p : this.passports) {
			if (p.isPassportValidPartTwo()) {
				System.out.println(p);
				counter++;
			}
		}
		return String.valueOf(counter);
	}

	@Override
	public int number() {
		return 4;
	}

	@Data
	private static class Passport {
		private int byr;
		private int iyr;
		private int eyr;
		private String hgt;
		private String hcl;
		private String ecl;
		private String pid;
		private int cid;

		public boolean isPassportValidPartOne() {
			return this.byr != 0
					&& this.iyr != 0
					&& this.eyr != 0
					&& this.hgt != null
					&& this.hcl != null
					&& this.ecl != null
					&& this.pid != null;
		}

		public boolean isPassportValidPartTwo() {
			if (this.isPassportValidPartOne()) {
				if (this.byr < 1920 || this.byr > 2002) {
					return false;
				}
				if (this.iyr < 2010 || this.iyr > 2020) {
					return false;
				}
				if (this.eyr < 2020 || this.eyr > 2030) {
					return false;
				}
				if (this.hgt.endsWith("cm")) {
					try {
						int height = Integer.parseInt(this.hgt.split("cm")[0]);
						if (height < 150 || height > 193) {
							return false;
						}
					} catch (NumberFormatException e) {
						return false;
					}
				} else if (this.hgt.endsWith("in")) {
					try {
						int height = Integer.parseInt(this.hgt.split("in")[0]);
						if (height < 59 || height > 76) {
							return false;
						}
					} catch (NumberFormatException e) {
						return false;
					}
				} else {
					return false;
				}
				if (!this.hcl.startsWith("#")) {
					return false;
				} else {
					String sub = this.hcl.substring(1);
					if (!(sub.matches("[0-9a-f]{6}"))) {
						return false;
					}
				}
				if (!EyeColor.matches(this.ecl)) {
					return false;
				}
				return this.pid.matches("[0-9]{9}");
			}
			return false;
		}

		enum EyeColor {
			amb("amb"),
			blu("blu"),
			brn("brn"),
			gry("gry"),
			grn("grn"),
			hzl("hzl"),
			oth("oth");

			@Getter
			private final String text;

			EyeColor(String text) {
				this.text = text;
			}

			static boolean matches(String text) {
				boolean matches = false;
				for (EyeColor eyeColor : values()) {
					if (eyeColor.getText().equals(text)) {
						matches = true;
						break;
					}
				}
				return matches;
			}
		}
	}
}
