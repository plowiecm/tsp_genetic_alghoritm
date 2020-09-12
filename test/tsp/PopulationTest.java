package tsp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PopulationTest {
    int[][] distances = new int[][]{};

    @Before
    public void init() throws IOException, InvalidInputException, InvalidStructureException {
        distances = FileOperator.readFileToArray("resources\\berlin52.txt");

        Assert.assertEquals(52, distances.length);
    }

    @Test
    public void testIndividual() {
        Population population = new Population(52, 5, distances);
        int[] individual = population.makeIndividual();

        Assert.assertNotEquals(individual[1], individual[2]);
    }

    @Test
    public void testRandomPopulation() {
        Population population = new Population(52, 11, distances);
        population.makePopulation();

        Assert.assertNotEquals(population.getIndividuals().get(1), population.getIndividuals().get(10));
        Assert.assertNotEquals(population.getIndividuals().get(2), population.getIndividuals().get(4));
        Assert.assertNotEquals(population.getIndividuals().get(5), population.getIndividuals().get(7));
    }

    @Test
    public void testLengths() {
        int[][] owndistances = {
                {0, 1, 2, 3},
                {1, 0, 3, 4},
                {2, 3, 0, 5},
                {3, 4, 5, 0}
        };
        Population population = new Population(4, 2, owndistances);
        int[] individual = {1, 0, 2, 3};
        int[] individual2 = {2, 3, 1, 0};
        population.addIndividual(individual);
        population.addIndividual(individual2);


        population.countLengths();
        Assert.assertEquals(population.getLengths()[0], 12);
        Assert.assertEquals(population.getLengths()[1], 12);   //5+4+1+2
    }


    @Test
    public void testTournamentSelection() {
        Population population = new Population(52, 20, distances);
        population.makePopulation();
        population.countLengths();

        population.tournament_select(3);
        Assert.assertEquals(population.getLengths().length, 20);
    }


    @Test
    public void testRouletteWheelSelection() {
        Population population = new Population(52, 20, distances);
        population.makePopulation();
        population.countLengths();

        Population newPopulation = population.roulette_wheel_select();
        Assert.assertEquals(newPopulation.getLengths().length, 20);
    }


    @Test
    public void testSelectionLowerDistancesTournament() {
        Population population = new Population(52, 20, distances);
        population.makePopulation();
        population.countLengths();

        int sum = 0;
        for (int i = 0; i < 20; i++) {
            sum += population.getLengths()[i];
        }
        int sred = sum / 20;

        population.tournament_select(2);
        population.countLengths();


        int sum2 = 0;
        for (int i = 0; i < 20; i++) {
            sum2 += population.getLengths()[i];
        }
        int sred2 = sum2 / 20;

        Assert.assertTrue(sred > sred2);
    }
}
