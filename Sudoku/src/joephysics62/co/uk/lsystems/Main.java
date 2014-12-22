package joephysics62.co.uk.lsystems;

import java.io.IOException;

import joephysics62.co.uk.lsystems.examples.ContextSensitive2dBushB;
import joephysics62.co.uk.lsystems.graphics.LSystem2dWriter;

public class Main {
	public static void main(final String[] args) throws IOException {
		final LSystem lsystem = new ContextSensitive2dBushB();

		final LSystem2dWriter writer2d = new LSystem2dWriter();
		writer2d.writeGif(lsystem, 30, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
