package tsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InvalidInputException, InvalidStructureException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter name of file to read");
        String name = scan.next();
        System.out.println("Please enter population size");
        int populationSize = scan.nextInt();
        System.out.println("Please enter parameter k for tournament selection");
        int parameterK = scan.nextInt();
        int[][] distances = FileOperator.readFileToArray("resources\\" + name + ".txt");

        int minDistance;
        int[] minDistanceTour;

        Population population = new Population(distances.length, populationSize, distances);
        population.makePopulation();

        minDistance = getMin(population.countLengths(), population);
        System.out.println("Initial min result:             " + minDistance);
        minDistanceTour = population.getIndividuals().get(population.minDistanceIndex);

        while (System.in.available() == 0) {
            population.tournament_select(parameterK);
            System.out.println("Actual result: " + minDistance);
            population.pmxCrossing(40);
            population.mutate(95);

            int actualMinDist = getMin(population.countLengths(), population);
            if (actualMinDist < minDistance) {
                minDistance = actualMinDist;
                minDistanceTour = population.getIndividuals().get(population.minDistanceIndex);
            }
        }

        System.out.println("Best found result: ");
        System.out.println(Arrays.toString(minDistanceTour));
        System.out.println(minDistance);

        PrintWriter writer = new PrintWriter("resources\\wynik.txt", StandardCharsets.UTF_8);

        for (int j = 0; j < minDistanceTour.length; j++) {
            writer.print(minDistanceTour[j]);
            if (j < minDistanceTour.length - 1) {
                writer.print("-");
            }
        }

        writer.print(" " + minDistance);
        writer.close();

    }

    public static int getMin(int[] array, Population population) {
        int minindex = 0;
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minindex = i;
            }
        }
        population.minDistanceIndex = minindex;
        return minValue;
    }
}
