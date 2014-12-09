package joephysics62.co.uk.lsystems;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class LSystemGenerator {
	List<TurtleMove> generate(final LSystem lsystem, final int limit) {
		List<TurtleMove> current = lsystem.axiom();
		for (int i = 0; i < limit; i++) {
			current = current.stream().map(lsystem::applyRule).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return current;
	}

	private static class Coord {
		private final double _x;
		private final double _y;

		private Coord(final double x, final double y) {
			_x = x;
			_y = y;
		}
	}

	public static void main(final String[] args) throws IOException {
		final int iterations = 7;
		final LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final List<TurtleMove> turtleMoves = lSystemGenerator.generate(new PythagoreanTree(), iterations);

		try (final ImageOutputStream outputStream = new FileImageOutputStream(new File("out.gif"))) {
			final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

			final int divide = 480;
			for (int i = 0; i < divide; i++) {
				final BufferedImage bi = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
				final Graphics2D g2d = bi.createGraphics();
				final double elementLength = 350.0 / Math.pow(2.0, iterations);
				final double angle = Math.PI * 2 * i / divide;
				writeToGraphics(elementLength, angle, turtleMoves, g2d);
				gifSequenceWriter.writeToSequence(bi);
				//ImageIO.write(bi, "GIF", new File("temp/out" + i + ".gif"));
			}
			gifSequenceWriter.close();
		}
	}

	private static void writeToGraphics(final double elementLength, final double angleRadians, final List<TurtleMove> generate, final Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		Coord c = new Coord(0, 0);
		double angle = 0;
		final Stack<Coord> coordStack = new Stack<Coord>();
		final Stack<Double> angleStack = new Stack<Double>();
		for (final TurtleMove move : generate) {
			if (move.moveUnits() != 0) {
				final double nextX = c._x + move.moveUnits() * elementLength * Math.sin(angle);
				final double nextY = c._y + move.moveUnits() * elementLength * Math.cos(angle);
				final Coord next = new Coord(nextX, nextY);
				drawSegment(g2d, 350, 400, c, next);
				c = next;
			}
			switch (move.stackChange()) {
			case PUSH:
				coordStack.push(c);
				angleStack.push(angle);
				break;
			case POP:
				c = coordStack.pop();
				angle = angleStack.pop();
				break;
			default:
				break;
			}
			switch (move.angleChange()) {
			case LEFT:
				angle += angleRadians;
				break;
			case RIGHT:
				angle -= angleRadians;
				break;
			default:
				break;
			}
		}
	}

	private static void drawSegment(final Graphics2D g2d, final int startx, final int starty, final Coord from, final Coord to) {
		g2d.drawLine(startx + (int) from._x, starty - (int) from._y, startx + (int) to._x, starty - (int) to._y);
	}

}
