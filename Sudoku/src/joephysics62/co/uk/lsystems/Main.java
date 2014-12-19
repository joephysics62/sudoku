package joephysics62.co.uk.lsystems;

import java.io.IOException;

import joephysics62.co.uk.lsystems.examples.SimpleStochasticPlant;
import joephysics62.co.uk.lsystems.graphics.LSystem2dWriter;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class Main {
	public static void main(final String[] args) throws IOException {
		final TurtleLSystem<Character> lsystem = new SimpleStochasticPlant();

		final LSystem2dWriter writer2d = new LSystem2dWriter();
		writer2d.writeGif(lsystem, 13, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
