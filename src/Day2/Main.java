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

        //Day2

        int result = 0;
        int nounResult=0;
        int verbResult=0;
        for(int noun=0; noun <100; noun++) {
            for(int verb=0; verb<100; verb++) {
                intCodeArray[1] = noun;
                intCodeArray[2] = verb;
                for (int i = 0; i < intCodeArray.length; i += 4) {
                    if (intCodeArray[i] == 1) {
                        intCodeArray[intCodeArray[i + 3]] = intCodeArray[intCodeArray[i + 1]] + intCodeArray[intCodeArray[i + 2]];
                    } else if (intCodeArray[i] == 2) {
                        intCodeArray[intCodeArray[i + 3]] = intCodeArray[intCodeArray[i + 1]] * intCodeArray[intCodeArray[i + 2]];
                    } else if (intCodeArray[i] == 99) {
                        result = intCodeArray[0];
                        nounResult = noun;
                        verbResult = verb;
                        break;
                    } else {
                        break;
                    }
                }
                if(result == 19690720) {
                    break;
                } else {
                    for (int i = 0; i < intCode.size(); i++) {
                        intCodeArray[i] = intCode.get(i);
                    }
                }
            }
            if(result == 19690720) {
                break;
            }
        }

        System.out.println(100 * nounResult + verbResult);
    }
}
