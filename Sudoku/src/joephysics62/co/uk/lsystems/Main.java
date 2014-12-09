package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.lsystems.examples.SierpinskiGasket;
import joephysics62.co.uk.lsystems.turtle.DoubleProvider;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 5;
		final LSystem lsystem = new SierpinskiGasket();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
    final List<Turtle> turtleMoves = lSystemGenerator.generate(lsystem, iterations);

		final DoubleProvider angleProvider = DoubleProvider.fixed(Math.PI / 3);
		final DoubleProvider lengthProvider = DoubleProvider.fixed(1.0);

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lengthProvider, angleProvider);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
