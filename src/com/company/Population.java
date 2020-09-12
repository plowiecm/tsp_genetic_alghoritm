package com.company;

import java.util.*;

public class Population {
    private final int[][] distances;
    private final int cityCount;
    private final int individualsCount;
    private ArrayList<int[]> individuals;
    private final int[] lengths;
    private ArrayList<Integer> list;
    private final static Random rand = new Random();

    int minDistanceIndex;


    public Population(int cityCount, int individualsCount, int[][] distances) {
        this.distances = distances;
        this.cityCount = cityCount;
        this.individualsCount = individualsCount;
        this.individuals = new ArrayList<>();
        this.lengths = new int[individualsCount];
        this.minDistanceIndex = 0;

        list = new ArrayList<>();
        for (int i = 0; i < cityCount; i++) {
            list.add(i);
        }
    }


    public int[] makeIndividual() {

        Collections.shuffle(list);
        int[] tab = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            tab[i] = list.get(i);
        }
        return tab;
    }

    public void addIndividual(int[] individual) {
        this.individuals.add(individual);
    }

    public void makePopulation() {
        for (int i = 0; i < individualsCount; i++) {
            addIndividual(makeIndividual());
        }
    }

    public ArrayList<int[]> getIndividuals() {
        return individuals;
    }


    public int[] countLengths() {
        int condition = cityCount - 1;
        for (int j = 0; j < individuals.size(); j++) {
            lengths[j] = 0;
            int[] individual = individuals.get(j);
            for (int i = 0; i < condition; i++) {
                lengths[j] += distances[individual[i]][individual[i + 1]];
            }
            lengths[j] += distances[individual[cityCount - 1]][individual[0]];
        }
        return lengths;
    }

    public void tournament_select(int parameterK) {
        ArrayList<int[]> newIndividuals = new ArrayList<>();
        int m = 0;
        int minLength = getMax(lengths);
        int minRand = 0;

        while (m < individualsCount) {
            for (int i = 0; i < parameterK; i++) {
                int random = rand.nextInt(individualsCount);
                if (lengths[random] < minLength) {
                    minLength = lengths[random];
                    minRand = random;
                }
            }
            newIndividuals.add(individuals.get(minRand));
            m++;
        }

        this.individuals = newIndividuals;
    }

    public Population roulette_wheel_select() {
        Population newPopulation = new Population(cityCount, individualsCount, distances);
        int[] revertedLengths = new int[individualsCount];
        int maxLength = getMax(lengths);
        int sum = 0;

        Random r = new Random();

        for (int i = 0; i < individualsCount; i++) {
            revertedLengths[i] = maxLength - lengths[i] + 1;
            sum += revertedLengths[i];
        }

        int m = 0;
        while (m < individualsCount) {
            int los = r.nextInt(sum);
            int i = 1;
            int sum2 = 0;
            while (sum2 < los) {
                sum2 += lengths[i];
                i++;
            }
            newPopulation.individuals.add(individuals.get(i - 1));
            newPopulation.lengths[m] = lengths[i - 1];

            m++;
        }

        return newPopulation;
    }

    private static int getMax(int[] array) {
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    public void pmxCrossing(int parameter) {
        for (int i = 0; i < individualsCount; i++) {
            int random = rand.nextInt(100);
            if (random <= parameter) {
                int index2;
                while ((index2 = rand.nextInt(individualsCount)) == i) ;
                k_pmx(i, index2);
            }
        }
    }

    private void k_pmx(int parent1_index, int parent2_index) {
        int p1 = rand.nextInt(cityCount - 3) + 1; //[1, lMiast-2]
        int p2 = rand.nextInt(cityCount - 1 - p1) + p1; //[p1, lMiast-1]

        int[] child = new int[cityCount];

        for (int j = p1; j <= p2; j++) {
            child[j] = individuals.get(parent1_index)[j];
        }

        child = rewrite(child, individuals.get(parent2_index), 0, p1, p2);
        child = rewrite(child, individuals.get(parent2_index), p2 + 1, p1, p2);

        individuals.set(parent1_index, child);
    }


    public int[] rewrite(int[] child, int[] individual, int start, int p1, int p2) {

        int end = p1;
        if (start > p2) {
            end = child.length;
        }
        for (int j = start; j < end; j++) {
            int gen = individual[j];
            int poz;
            while ((poz = find(child, gen, p1, p2)) > -1) {
                gen = individual[poz];
            }
            child[j] = gen;
        }

        return child;
    }


    public static int find(int[] tab, int el, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (tab[i] == el) {
                return i;
            }
        }
        return -1;
    }


    public void mutate(int parameter) {
        for (int i = 0; i < individualsCount; i++) {
            int random = rand.nextInt(100);
            if (random <= parameter) {
                int[] child = mutateSingle(individuals.get(i));
                individuals.set(i, child);
            }

        }
    }

    private int[] mutateSingle(int[] tab) {
        int first_cut = rand.nextInt(cityCount - 2);
        int second_cut = rand.nextInt(cityCount - first_cut) + first_cut;
        int[] tmp = new int[second_cut - first_cut + 1];

        for (int i = 0, j = second_cut; i < tmp.length; i++, j--) {
            tmp[i] = tab[j];
        }

        for (int i = first_cut, j = 0; i <= second_cut; i++, j++) {
            tab[i] = tmp[j];
        }

        return tab;
    }

    public int[][] getDistances() {
        return distances;
    }

    public int getCityCount() {
        return cityCount;
    }

    public int getIndividualsCount() {
        return individualsCount;
    }

    public int[] getLengths() {
        return lengths;
    }
}



