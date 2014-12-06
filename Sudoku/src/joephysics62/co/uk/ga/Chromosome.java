package joephysics62.co.uk.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Chromosome {
	private List<Integer> _genes;

	public Chromosome(final List<Integer> genes) {
		_genes = genes;
	}
	
	public Chromosome(Integer... genes) {
		this(Arrays.asList(genes));
	}

	public double fitness() {
		return 1.0 / (1.0 + Math.abs(_genes.get(0) + 2 * _genes.get(1) + 3 * _genes.get(2) + 4 * _genes.get(3) - 30));
	}
	
	@Override
	public String toString() {
		return String.format("C(%s), F=%s", _genes, fitness());
	}

	public Chromosome cross(final Chromosome right) {
		Random r = new Random();
		int cut = r.nextInt(_genes.size() - 1) + 1;
		List<Integer> crossed = new ArrayList<>();
		crossed.addAll(_genes.subList(0, cut));
		crossed.addAll(right._genes.subList(cut, right._genes.size()));
		return new Chromosome(crossed);
	}

	public void mutate(int geneIndex, int newValue) {
		_genes.set(geneIndex, newValue);
	}
}
