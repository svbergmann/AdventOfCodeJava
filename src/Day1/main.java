package Day1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException {

        ArrayList<Integer> masses = new ArrayList<>();
        File file = new File("src/Day1/masses");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            masses.add(Integer.parseInt(scanner.nextLine()));
        }
        scanner.close();

        int[] fuel = new int[masses.size()];
        for (int i = 0; i < masses.size(); i++) {
            fuel[i] = (masses.get(i) / 3) - 2;
        }

        int fuelRemaining;
        ArrayList<Integer> fuelForFuel = new ArrayList<>();
        for (int i : fuel) {
            fuelRemaining = i;
            do {
                fuelForFuel.add((fuelRemaining / 3) - 2);
                fuelRemaining = fuelForFuel.get(fuelForFuel.size() - 1);
            } while (fuelRemaining >= 9);
        }

        long fuelSum = 0;
        for (int value : fuel) {
            fuelSum += value;
        }
        long fuelForFuelSum = 0;
        for (Integer integer : fuelForFuel) {
            fuelForFuelSum += integer;
        }

        long sum = fuelSum + fuelForFuelSum;

        System.out.println(fuelSum);
        System.out.println(fuelForFuelSum);
        System.out.println(sum);

        System.out.println(Arrays.toString(fuel));
        System.out.println(fuelForFuel.toString());
    }

}
