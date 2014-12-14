package joephysics62.co.uk.lsystems;

import java.io.IOException;

import joephysics62.co.uk.lsystems.examples.QuadraticSnowflake;
import joephysics62.co.uk.lsystems.graphics.LSystem2dWriter;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class Main {
	public static void main(final String[] args) throws IOException {
		final TurtleLSystem lsystem = new QuadraticSnowflake();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final LSystem2dWriter writer2d = new LSystem2dWriter(lSystemGenerator);
		writer2d.writeGif(lsystem, 6, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
