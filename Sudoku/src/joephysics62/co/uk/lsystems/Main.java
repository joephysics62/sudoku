package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.lsystems.examples.CharacterMapLSystem;
import joephysics62.co.uk.lsystems.turtle.DoubleProvider;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 6;

		final Map<String, Double> weightedStochasticTo = new LinkedHashMap<>();
		weightedStochasticTo.put("F[+F]F[-F]F", 1.0);
    weightedStochasticTo.put("F[+F]", 2.0);
    weightedStochasticTo.put("F[-F]F", 2.0);

		final LSystem lsystem = new CharacterMapLSystem(
		    new TurtleMoves(Turtle.draw('F'), Turtle.push('['), Turtle.pop(']'), Turtle.left('-'), Turtle.right('+')),
		    new StochasticRewriteSystem('F', weightedStochasticTo),
		    "F") {
    };

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
    final List<Turtle> turtleMoves = lSystemGenerator.generate(lsystem, iterations);

		final DoubleProvider angleProvider = DoubleProvider.fixed(Math.PI / 6);
		final DoubleProvider lengthProvider = DoubleProvider.fixed(1.0);

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lengthProvider, angleProvider);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
