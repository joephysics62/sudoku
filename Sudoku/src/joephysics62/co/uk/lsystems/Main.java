package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.lsystems.examples.IslandsAndLakes;
import joephysics62.co.uk.lsystems.turtle.DoubleProvider;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 2;
		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final List<Turtle> turtleMoves = lSystemGenerator.generate(new IslandsAndLakes(), iterations);

		final DoubleProvider angleProvider = DoubleProvider.fixed(Math.PI / 2);
		final DoubleProvider lengthProvider = DoubleProvider.fixed(35.0 / Math.pow(2.0, iterations));

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lengthProvider, angleProvider);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
