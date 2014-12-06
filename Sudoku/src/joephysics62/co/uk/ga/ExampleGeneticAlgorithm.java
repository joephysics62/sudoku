package joephysics62.co.uk.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

public class ExampleGeneticAlgorithm {
	
  private static final int GENE_LENGTH = 4;
private static final double CROSSOVER_RATE = 0.75;
  private static final int POPULATION_SIZE = 6;
  private static final double MUTATION_RATE = 0.1;
  private static final int NUM_MUTATIONS = (int) (POPULATION_SIZE * GENE_LENGTH * MUTATION_RATE);
  private static final int GENERATIONS = 500;
  private static Random _random = new Random();
  
  public static void main(String[] args) {
	List<Chromosome> init = Arrays.asList(
			new Chromosome(12, 5, 23, 8),
			new Chromosome(2, 21, 18, 3),
			new Chromosome(10, 4, 13, 14),
			new Chromosome(20, 1, 10, 6),
			new Chromosome(1,  4, 13, 19),
			new Chromosome(20, 5, 17, 1)
	);
	
	List<Chromosome> parent = init;
	for (int i = 0; i < GENERATIONS; i++) {
		parent.stream().forEach(System.out::println);
		parent = nextGeneration(parent);
	}
  }

private static List<Chromosome> nextGeneration(List<Chromosome> parent) {
	final List<Chromosome> newPopulation = applyFitnessFilter(parent);
    final List<Chromosome> crossedPopulation = crossover(newPopulation);
    mutate(crossedPopulation);
	return crossedPopulation;
}

private static void mutate(final List<Chromosome> crossedPopulation) {
	for (int i = 0; i < NUM_MUTATIONS; i++) {
		int randomIndex = _random.nextInt(POPULATION_SIZE);
		int randomGene = _random.nextInt(GENE_LENGTH);
		System.out.println("Selected chromosome " + randomIndex + ", gene " + randomGene);
		Chromosome randomChromosome = crossedPopulation.get(randomIndex);
		randomChromosome.mutate(randomGene, _random.nextInt(30) + 1);
	}
}

private static List<Chromosome> crossover(final List<Chromosome> newPopulation) {
	final List<Integer> crossovers = new ArrayList<Integer>();
    final double[] crossoverSelections = _random.doubles(POPULATION_SIZE).toArray();
    int counter = 0;
    for (double d : crossoverSelections) {
		if (d <= CROSSOVER_RATE) {
			crossovers.add(counter);
		}
		counter++;
	}
    final List<Chromosome> crossedPopulation = new ArrayList<>();
    for (int leftIndex = 0; leftIndex < POPULATION_SIZE; leftIndex++) {
    	final Chromosome left = newPopulation.get(leftIndex);
    	if (crossovers.contains(leftIndex)) {
    		int rightCrossoverIndex = (crossovers.indexOf(leftIndex) + 1) % crossovers.size();
    		Integer rightIndex = crossovers.get(rightCrossoverIndex);
    		System.out.println(String.format("Crossing C%s with C%s", leftIndex, rightIndex));
			Chromosome right = newPopulation.get(rightIndex);
    		Chromosome crossed = left.cross(right, _random);
    		crossedPopulation.add(crossed);
    	}
    	else {
    		crossedPopulation.add(left);
    	}
    }
	return crossedPopulation;
}

private static List<Chromosome> applyFitnessFilter(List<Chromosome> population) {
	final double[] fitnesses = new double[POPULATION_SIZE];
	for (int i = 0; i < POPULATION_SIZE; i++) {
		fitnesses[i] = population.get(i).fitness();
	}
    EnumeratedIntegerDistribution eid = new EnumeratedIntegerDistribution(
    		IntStream.range(0, fitnesses.length).toArray(), 
    		fitnesses
    );
    int[] selected = eid.sample(POPULATION_SIZE);
    final List<Chromosome> newPopulation = new ArrayList<>(POPULATION_SIZE);
    for (int i = 0; i < POPULATION_SIZE; i++) {
    	newPopulation.add(population.get(selected[i]));
    }
	return newPopulation;
}
}
