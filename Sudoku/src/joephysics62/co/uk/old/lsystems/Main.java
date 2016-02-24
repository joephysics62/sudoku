package joephysics62.co.uk.old.lsystems;

import java.io.IOException;

import joephysics62.co.uk.old.lsystems.examples.RowOfTrees;
import joephysics62.co.uk.old.lsystems.graphics.LSystem2dWriter;

public class Main {
	public static void main(final String[] args) throws IOException {
		final LSystem lsystem = new RowOfTrees();

		final LSystem2dWriter writer2d = new LSystem2dWriter();
		writer2d.writeGif(lsystem, 30, "C:\\Users\\joe\\Pictures\\outStatic.gif", 600);

		//angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}
}
