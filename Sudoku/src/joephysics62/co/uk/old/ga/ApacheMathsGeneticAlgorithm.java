package joephysics62.co.uk.old.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.TournamentSelection;

public class ApacheMathsGeneticAlgorithm {
	
  private static final int GENE_LENGTH = 4;
  private static final double CROSSOVER_RATE = 0.75;
  private static final double MUTATION_RATE = 0.1;
  private static final int GENERATIONS = 300;
  private static final Random _random = new Random();
  
  private static class ExampleChromosome extends AbstractListChromosome<Integer> {
	private static List<Integer> COEFFS = Arrays.asList(1, 2, 3, 4);
	public ExampleChromosome(Integer... representation) throws InvalidRepresentationException {
		this(Arrays.asList(representation));
	}
	
	public ExampleChromosome(List<Integer> cs) {
		super(cs);
	}

	@Override
	public double fitness() {
		double value = -30;
		for (int i = 0; i < GENE_LENGTH; i++) {
			value += COEFFS.get(i) * getRepresentation().get(i);
		}
		return 1 / (1 + Math.abs(value));
	}

	@Override
	protected void checkValidity(List<Integer> chromosomeRepresentation) throws InvalidRepresentationException {
		if (chromosomeRepresentation.size() != GENE_LENGTH) {
			throw new RuntimeException();
		}
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> chromosomeRepresentation) {
		return new ExampleChromosome(chromosomeRepresentation);
	}
	
	@Override
	public List<Integer> getRepresentation() {
		return super.getRepresentation();
	}
	  
  }
  
  public static void main(String[] args) {
	CrossoverPolicy crossoverPolicy = new OnePointCrossover<Integer>();
	MutationPolicy mutation = new MutationPolicy() {
		
		@Override
		public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
			int geneIndex = _random.nextInt(4);
			if (original instanceof ExampleChromosome) {
				ExampleChromosome exampleOriginal = (ExampleChromosome) original;
				List<Integer> representation = exampleOriginal.getRepresentation();
				List<Integer> newRep = new ArrayList<Integer>(representation);
				newRep.set(geneIndex, _random.nextInt(30) + 1);
				return new ExampleChromosome(newRep);
			}
			throw new RuntimeException();
		}
	};
	List<Chromosome> init = Arrays.asList(
			new ExampleChromosome(12, 5, 23, 8),
			new ExampleChromosome(2, 21, 18, 3),
			new ExampleChromosome(10, 4, 13, 14),
			new ExampleChromosome(20, 1, 10, 6),
			new ExampleChromosome(1,  4, 13, 19),
			new ExampleChromosome(20, 5, 17, 1)
	);
	Population population = new ElitisticListPopulation(init, 20, 0.9);
	final GeneticAlgorithm ga = new GeneticAlgorithm(crossoverPolicy, CROSSOVER_RATE, mutation, MUTATION_RATE, new TournamentSelection(2));
	for (int i = 0; i < GENERATIONS; i++) {
		population = ga.nextGeneration(population);
		System.err.println(population.getFittestChromosome());
	}
  }

}
