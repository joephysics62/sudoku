package joephysics62.co.uk.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

public class ExampleGeneticAlgorithm {
	
  private static final double CROSSOVER_RATE = 0.25;
  private static final int POPULATION_SIZE = 6;
  
  public static void main(String[] args) {
	List<Chromosome> population = Arrays.asList(
			new Chromosome(12, 5, 23, 8),
			new Chromosome(2, 21, 18, 3),
			new Chromosome(10, 4, 13, 14),
			new Chromosome(20, 1, 10, 6),
			new Chromosome(1,  4, 13, 19),
			new Chromosome(20, 5, 17, 1)
	);
    final double[] fitnesses = population.stream().map(Chromosome::fitness).mapToDouble(a -> a).toArray();
    EnumeratedIntegerDistribution eid = new EnumeratedIntegerDistribution(
    		IntStream.range(0, fitnesses.length).toArray(), 
    		fitnesses
    );
    
    final List<Chromosome> newPopulation = IntStream.of(eid.sample(POPULATION_SIZE)).mapToObj(population::get).collect(Collectors.toList());
    newPopulation.stream().forEach(System.out::println);
    Random random = new Random();
    final List<Integer> crossovers = new ArrayList<Integer>();
    final double[] crossoverSelections = random.doubles(POPULATION_SIZE).toArray();
    int counter = 0;
    for (double d : crossoverSelections) {
		if (d <= CROSSOVER_RATE) {
			crossovers.add(counter);
		}
		counter++;
	}
    crossovers.stream().forEach(System.out::println);
    final List<Chromosome> crossedPopulation = new ArrayList<>();
    for (int i = 0; i < POPULATION_SIZE; i++) {
    	final Chromosome left = newPopulation.get(i);
    	if (crossovers.contains(i)) {
    		Chromosome right = newPopulation.get(crossovers.get((crossovers.indexOf(i) + 1) % crossovers.size()));
    		Chromosome crossed = left.cross(right);
    		crossedPopulation.add(crossed);
    	}
    	else {
    		crossedPopulation.add(left);
    	}
    }
    crossedPopulation.stream().forEach(System.out::println);
    
  }
}
