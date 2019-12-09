package Day2;

import utils.ReadAndSaveUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> intCode = new ArrayList<>();
        String fileInput = ReadAndSaveUtils.returnFileContent("src/Day2/IntcodeInput");
        String[] strings = fileInput.split(",");
        for(String string: strings) {
            intCode.add(Integer.parseInt(string));
        }

        System.out.println(intCode);

        for (int i = 0; i < intCode.size(); ) {
            if (intCode.get(i) == 1) {
                intCode.set(intCode.get(i+3), intCode.get(i + 1) + intCode.get(i + 2));
                i += 4;
            } else if (intCode.get(i) == 2) {
                intCode.set(intCode.get(i + 3), intCode.get(i + 1) * intCode.get(i + 2));
                i += 4;
            } else if (intCode.get(i) == 99) {
                i++;
            } else {
                throw new IllegalArgumentException("Zahl passt nicht in das Pattern!");
            }
        }

        System.out.println(intCode);
    }

}
