package Day2;

import utils.ReadAndSaveUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> intCode = new ArrayList<>();
        String fileInput = ReadAndSaveUtils.returnFileContent("src/Day2/IntcodeInput");
        String[] strings = fileInput.split(",");
        for (String string : strings) {
            intCode.add(Integer.parseInt(string));
        }

        intCode.set(1, 12);
        intCode.set(2, 2);
        int[] intCodeArray = new int[intCode.size()];
        for (int i = 0; i < intCode.size(); i++) {
            intCodeArray[i] = intCode.get(i);
        }

        for (int i = 0; i < intCodeArray.length; i += 4) {
            if (intCodeArray[i] == 1) {
                intCodeArray[intCodeArray[i + 3]] = intCodeArray[intCodeArray[i + 1]] + intCodeArray[intCodeArray[i + 2]];
            } else if (intCodeArray[i] == 2) {
                intCodeArray[intCodeArray[i + 3]] = intCodeArray[intCodeArray[i + 1]] * intCodeArray[intCodeArray[i + 2]];
            } else if (intCodeArray[i] == 99) {
                System.out.println(i);
                System.out.println(intCodeArray[0]);
                break;
            } else {
                throw new IllegalArgumentException("Zahl passt nicht in das Pattern!");
            }
        }
    }
}
