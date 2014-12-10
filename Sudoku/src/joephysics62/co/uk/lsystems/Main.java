package joephysics62.co.uk.lsystems;

import java.io.IOException;

import joephysics62.co.uk.lsystems.examples.SimpleStochasticPlant;

public class Main {
	public static void main(final String[] args) throws IOException {
		final LSystem lsystem = new SimpleStochasticPlant();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
    lSystemGenerator.writeGif(lsystem, 6, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
