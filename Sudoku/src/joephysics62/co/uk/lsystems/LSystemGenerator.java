package joephysics62.co.uk.lsystems;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.lsystems.animation.GifSequenceWriter;
import joephysics62.co.uk.lsystems.examples.PythagoreanTree;
import joephysics62.co.uk.lsystems.turtle.TurtleMove;

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

		angleAnimatedGif("out.gif", turtleMoves, 350.0 / Math.pow(2.0, iterations), 480);
	}

	private static void writeGif(final String fileName, final List<TurtleMove> turtleMoves, final double scale, final double angle) throws IOException {
		final BufferedImage bi = createBufferedImage(turtleMoves, scale, angle);
		ImageIO.write(bi, "GIF", new File(fileName));
	}

	private static void angleAnimatedGif(final String fileName, final List<TurtleMove> turtleMoves, final double scale, final int frames) throws IOException {
		try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
			final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

			for (int i = 0; i < frames; i++) {
				final double angle = Math.PI * 2 * i / frames;
				final BufferedImage bi = createBufferedImage(turtleMoves, scale, angle);
				gifSequenceWriter.writeToSequence(bi);
			}
			gifSequenceWriter.close();
		}
	}

	private static BufferedImage createBufferedImage(final List<TurtleMove> turtleMoves, final double scale, final double angle) {
		final BufferedImage bi = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = bi.createGraphics();
		writeToGraphics(turtleMoves, scale, angle, g2d);
		return bi;
	}

	private static void writeToGraphics(final List<TurtleMove> generate, final double elementLength, final double angleRadians, final Graphics2D g2d) {
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
