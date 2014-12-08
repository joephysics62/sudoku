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

public class LSystemGenerator {
	List<TurtleMove> generate(LSystem lsystem, int limit) {
		List<TurtleMove> current = lsystem.axiom();
		for (int i = 0; i < limit; i++) {
			current = current.stream().map(lsystem::applyRule).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return current;
	}
	
	private static class Coord {
		private double _x;
		private double _y;

		private Coord(double x, double y) {
			_x = x;
			_y = y;
		}
	}
	
	public static void main(String[] args) throws IOException {
		final int iterations = 9;
		LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final List<TurtleMove> turtleMoves = lSystemGenerator.generate(new PythagoreanTree(), iterations);
		
	    BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = bi.createGraphics();
	    final double elementLength = 300.0 / Math.pow(2.0, iterations);
		writeToGraphics(elementLength, turtleMoves, g2d);
		
		ImageIO.write(bi, "GIF", new File("out.gif"));
	}

	private static void writeToGraphics(final double elementLength, final List<TurtleMove> generate, Graphics2D g2d) {
	    g2d.setColor(Color.GREEN);
	    Coord c = new Coord(0, 0);
	    double angle = 0;
	    Stack<Coord> coordStack = new Stack<Coord>();
	    Stack<Double> angleStack = new Stack<Double>();
	    for (final TurtleMove move : generate) {
	    	if (move.moveUnits() != 0) {
	    		double nextX = c._x + move.moveUnits() * elementLength * Math.sin(angle);
	    		double nextY = c._y + move.moveUnits() * elementLength * Math.cos(angle);
	    		final Coord next = new Coord(nextX, nextY);
	    		drawSegment(g2d, c, next);
	    		c = next;
	    	}
	    	if (move.doPush()) {
	    		coordStack.push(c);
	    		angleStack.push(angle);
	    	}
	    	else if (move.doPop()) {
	    		c = coordStack.pop();
	    		angle = angleStack.pop();
	    	}
	    	if (move.angleChange() != 0) {
	    		angle += move.angleChange();
	    	}
		}
	}
	
	private static void drawSegment(Graphics2D g2d, Coord from, final Coord to) {
		g2d.drawLine(250 + (int) from._x, 400 - (int) from._y, 250 + (int) to._x, 400 - (int) to._y);	
	}
	
}
