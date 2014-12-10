package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.lsystems.examples.SimpleStochasticPlant;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 6;

		final LSystem lsystem = new SimpleStochasticPlant();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
    final List<Turtle> turtleMoves = lSystemGenerator.generate(lsystem, iterations);

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lsystem.angleDegrees(), 600);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
