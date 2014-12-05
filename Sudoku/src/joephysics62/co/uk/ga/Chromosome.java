package joephysics62.co.uk.ga;

import java.util.Random;


public class Chromosome {
	private int a;
	private int b;
	private int c;
	private int d;

	public Chromosome(final int a, final int b, final int c, final int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public double fitness() {
		return 1.0 / (1.0 + Math.abs(a + 2 * b + 3 * c + 4 * d - 30));
	}
	
	@Override
	public String toString() {
		return String.format("Chromosome(%s, %s, %s, %s)", a, b, c, d);
	}

	public Chromosome cross(final Chromosome right) {
		Random r = new Random();
		int cut = r.nextInt(3) + 1;
		if (cut == 1) {
			return new Chromosome(a, right.b, right.c, right.d);
		}
		if (cut == 2) {
			return new Chromosome(a, b, right.c, right.d);
		}
		if (cut == 3) {
			return new Chromosome(a, b, c, right.d);
		}
		throw new RuntimeException();
	}
}
