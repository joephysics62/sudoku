package joephysics62.co.uk.lsystems;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.lsystems.animation.GifSequenceWriter;
import joephysics62.co.uk.lsystems.turtle.DoubleProvider;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class LSystemGenerator {

  List<Turtle> generate(final LSystem lsystem, final int limit) {
		List<Turtle> current = lsystem.axiom();
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



	public static void writeGif(final String fileName, final List<Turtle> turtleMoves,
			final DoubleProvider lengthProvider, final DoubleProvider angleProvider) throws IOException {
		final BufferedImage bi = createBufferedImage(turtleMoves, lengthProvider, angleProvider);
		ImageIO.write(bi, "GIF", new File(fileName));
	}

	public static void angleAnimatedGif(final String fileName, final List<Turtle> turtleMoves, final DoubleProvider lengthProvider, final int frames) throws IOException {
		try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
			final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

			for (int i = 0; i < frames; i++) {
				final double angle = Math.PI * 2 * i / frames;
				final BufferedImage bi = createBufferedImage(turtleMoves, lengthProvider, () -> angle);
				gifSequenceWriter.writeToSequence(bi);
			}
			gifSequenceWriter.close();
		}
	}

	private static BufferedImage createBufferedImage(final List<Turtle> turtleMoves,
		  final DoubleProvider lenghProvider, final DoubleProvider angleProvider) {
		final BufferedImage bi = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = bi.createGraphics();
		writeToGraphics(turtleMoves, lenghProvider, angleProvider, g2d);
		return bi;
	}

	private static class Line {
	  private final Coord _from;
    private final Coord _to;

    private Line(final Coord from, final Coord to) {
      _from = from;
      _to = to;
	  }
    public Coord getFrom() {
      return _from;
    }
    public Coord getTo() {
      return _to;
    }
	}

	private static void writeToGraphics(final List<Turtle> turtle, final DoubleProvider lengthProvider, final DoubleProvider angleProvider, final Graphics2D g2d) {
		final List<Line> toDraw = getLines(turtle, lengthProvider, angleProvider);
		// needs to fit into 700, 700
		final double xscale = maxX - minX;
    final double yscale = maxY - minY;
    final double scale = Math.max(xscale, yscale);
		g2d.setColor(Color.GREEN);

    System.out.println(String.format("%s, %s -> %s, %s", minX, maxX, minY, maxY));

		for (final Line line : toDraw) {
		  final int x1 = (int) (700 / scale * (-minX + line.getFrom()._x));
		  final int y1 = (int) (700 / scale * (-minY + line.getFrom()._y));
		  final int x2 = (int) (700 / scale * (-minX + line.getTo()._x));
		  final int y2 = (int) (700 / scale * (-minY + line.getTo()._y));
		  //System.out.println(String.format("%s, %s -> %s, %s", x1, y1, x2, y2));
 		  g2d.drawLine(x1, y1, x2, y2);
    }
	}

	private static double minX = 0;
	private static double maxX = 0;
	private static double minY = 0;
	private static double maxY = 0;

  private static List<Line> getLines(final List<Turtle> generate,
      final DoubleProvider lengthProvider, final DoubleProvider angleProvider) {
    Coord current = new Coord(0, 0);
		double angle = 0;
		final Stack<Coord> coordStack = new Stack<Coord>();
		final Stack<Double> angleStack = new Stack<Double>();
		final List<Line> toDraw = new ArrayList<>();
		for (final Turtle move : generate) {
			if (move.moveUnits() != 0) {
				final double elementLength = lengthProvider.nextDouble();
				final double nextX = current._x + move.moveUnits() * elementLength * Math.sin(angle);
				final double nextY = current._y + move.moveUnits() * elementLength * Math.cos(angle);

				if (nextX > maxX) {
				  maxX = nextX;
				}
				if (nextX < minX) {
				  minX = nextX;
				}
				if (nextY > maxY) {
				  maxY = nextY;
				}
				if (nextY < minY) {
				  minY = nextY;
				}
				final Coord next = new Coord(nextX, nextY);
				if (move.draw()) {
				  toDraw.add(new Line(current, next));
				}
				current = next;
			}
			switch (move.stackChange()) {
			case PUSH:
				coordStack.push(current);
				angleStack.push(angle);
				break;
			case POP:
				current = coordStack.pop();
				angle = angleStack.pop();
				break;
			default:
				break;
			}
			switch (move.angleChange()) {
			case LEFT:
				angle += angleProvider.nextDouble();
				break;
			case RIGHT:
				angle -= angleProvider.nextDouble();
				break;
			default:
				break;
			}
		}
    return toDraw;
  }

}
