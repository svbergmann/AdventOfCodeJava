package aoc2019.Day4;

import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {

    ArrayList<String> possiblePasswords = new ArrayList<>();

    for (int i = 147981; i < 691424; i++) {
      possiblePasswords.add(i + "");
    }

    // Abfrage, falls die Ziffernfolge groesser wird oder falls kein Double vorhanden ist
    possiblePasswords.removeIf(string -> !testForIncrementOrSameTask2(string));

    System.out.println(possiblePasswords);

    System.out.println(possiblePasswords.size());
  }

  public static boolean testForIncrementTask1(String string) {
    return string.charAt(0) <= string.charAt(1)
        && string.charAt(1) <= string.charAt(2)
        && string.charAt(2) <= string.charAt(3)
        && string.charAt(3) <= string.charAt(4)
        && string.charAt(4) <= string.charAt(5);
  }

  public static boolean testForDoubles(String string) {
    return string.charAt(0) == string.charAt(1)
        || string.charAt(1) == string.charAt(2)
        || string.charAt(2) == string.charAt(3)
        || string.charAt(3) == string.charAt(4)
        || string.charAt(4) == string.charAt(5);
  }

  public static boolean testForIncrementOrSameTask2(String string) {
    if (testForIncrementTask1(string) && testForDoubles(string)) {
      for (int i = 0; i < 4; ) {
        char temp = string.charAt(i);
        if (temp == string.charAt(i + 1) && temp != string.charAt(i + 2)) {
          return true;
        } else if (temp != string.charAt(i + 1)) {
          i++;
        } else {
          do {
            i++;
          } while (string.charAt(i) == temp && i < 4);
        }
      }
      return string.charAt(4) == string.charAt(5) && string.charAt(4) != string.charAt(3);
    } else {
      return false;
    }
  }
}
