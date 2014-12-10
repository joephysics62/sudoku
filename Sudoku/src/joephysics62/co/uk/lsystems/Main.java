package joephysics62.co.uk.lsystems;

import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.lsystems.examples.SierpinskiGasket;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Main {
	public static void main(final String[] args) throws IOException {
		final int iterations = 8;

//		final Map<String, Double> weightedStochasticTo = new LinkedHashMap<>();
//		weightedStochasticTo.put("F[+F]F[-F]F", 1.0);
//    weightedStochasticTo.put("F[+F]", 2.0);
//    weightedStochasticTo.put("F[-F]F", 2.0);
//
//		final LSystem lsystem = new CharacterMapLSystem(
//		    new TurtleMoves(Turtle.draw('F'), Turtle.push('['), Turtle.pop(']'), Turtle.left('-'), Turtle.right('+')),
//		    new StochasticRewriteSystem('F', weightedStochasticTo),
//		    "F", 23.0) {
//    };
		final LSystem lsystem = new SierpinskiGasket();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
    final List<Turtle> turtleMoves = lSystemGenerator.generate(lsystem, iterations);

		//Pictures
		LSystemGenerator.writeGif("C:\\Users\\joe\\Pictures\\outStatic.gif", turtleMoves, lsystem.angleDegrees(), 600);
		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
