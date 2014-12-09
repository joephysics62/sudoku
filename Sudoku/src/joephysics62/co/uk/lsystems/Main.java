package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.lsystems.examples.FractalTreeC;
import joephysics62.co.uk.lsystems.turtle.DoubleProvider;
import joephysics62.co.uk.lsystems.turtle.StochasticDoubleProvider;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 4;
		final LSystem lsystem = new FractalTreeC();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
    final List<Turtle> turtleMoves = lSystemGenerator.generate(lsystem, iterations);

		//final DoubleProvider angleProvider = DoubleProvider.fixed(22.5 * Math.PI / 180);
		final double base = 22.5 * Math.PI / 180;
		final DoubleProvider angleProvider = new StochasticDoubleProvider(base, base * 4);
		final DoubleProvider lengthProvider = DoubleProvider.fixed(1.0);

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lengthProvider, angleProvider);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
