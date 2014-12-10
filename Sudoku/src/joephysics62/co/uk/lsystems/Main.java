package joephysics62.co.uk.lsystems;

import java.io.IOException;

import joephysics62.co.uk.lsystems.examples.DragonCurve;
import joephysics62.co.uk.lsystems.graphics.LSystem2dWriter;

public class Main {
	public static void main(final String[] args) throws IOException {
		final LSystem lsystem = new DragonCurve();

		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final LSystem2dWriter writer2d = new LSystem2dWriter(lSystemGenerator);
		writer2d.writeGif(lsystem, 16, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
