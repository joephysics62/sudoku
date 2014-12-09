package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.lsystems.examples.PythagoreanTree;
import joephysics62.co.uk.lsystems.turtle.DoubleProvider;
import joephysics62.co.uk.lsystems.turtle.StochasticDoubleProvider;
import joephysics62.co.uk.lsystems.turtle.TurtleMove;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 7;
		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final List<TurtleMove> turtleMoves = lSystemGenerator.generate(new PythagoreanTree(), iterations);

		final DoubleProvider angleProvider = new StochasticDoubleProvider(Math.PI / 7, 1.0);
		final DoubleProvider lengthProvider = new StochasticDoubleProvider(350.0 / Math.pow(2.0, iterations), 4);

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lengthProvider, angleProvider);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
