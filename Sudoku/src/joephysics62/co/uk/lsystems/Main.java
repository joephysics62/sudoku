package joephysics62.co.uk.lsystems;

import java.io.IOException;

import joephysics62.co.uk.lsystems.examples.BushExample3d;
import joephysics62.co.uk.lsystems.graphics.LSystem2dWriter;

public class Main {
	public static void main(final String[] args) throws IOException {
		final LSystem lsystem = new BushExample3d();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final LSystem2dWriter writer2d = new LSystem2dWriter(lSystemGenerator);
		writer2d.writeGif(lsystem, 2, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
