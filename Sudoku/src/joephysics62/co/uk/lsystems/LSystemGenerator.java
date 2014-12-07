package joephysics62.co.uk.lsystems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class LSystemGenerator {
	List<Character> generate(LSystem lsystem, int limit) {
		List<Character> current = lsystem.axiom();
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
	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		LSystemGenerator lSystemGenerator = new LSystemGenerator();
		final List<Character> generate = lSystemGenerator.generate(new PythagoreanTree(), 7);
		System.out.println(generate.stream().map(String::valueOf).collect(Collectors.joining()));
		
		JFrame frame = new JFrame();
		
		final double elementLength = 2.0;

	    frame.add(new JComponent() {
	    	@Override
	    	public void paint(Graphics g) {
	    	    Graphics2D g2d = (Graphics2D) g;
	    	    g2d.setBackground(Color.WHITE);
	    	    Coord c = new Coord(0, 0);
	    	    double angle = 0;
	    	    Stack<Coord> coordStack = new Stack<Coord>();
	    	    Stack<Double> angleStack = new Stack<Double>();
	    	    double rotateAngle = Math.PI / 8;
	    	    for (final Character character : generate) {
					switch (character) {
					case '1':
					case '0':
						double nextX = c._x + elementLength * Math.sin(angle);
						double nextY = c._y + elementLength * Math.cos(angle);
						final Coord next = new Coord(nextX, nextY);
						g2d.drawLine(250 + (int) c._x, 400 - (int) c._y, 250 + (int) next._x, 400 - (int) next._y);
						c = next;
						break;
					case '[':
						coordStack.push(c);
						angleStack.push(angle);
						angle += rotateAngle;
						break;
					case ']':
						c = coordStack.pop();
						angle = angleStack.pop();
						angle -= rotateAngle;
						break;
					default:
						break;
					}
				}
	    	}
		});
	    frame.setSize(500, 500);
	    frame.setVisible(true);
	  }
}
