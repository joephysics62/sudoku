package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class IslandsAndLakes extends CharacterMapLSystem {

	public IslandsAndLakes() {
		super(
				Arrays.asList(Turtle.draw('F'), Turtle.move('f'), Turtle.left('-'), Turtle.right('+')),
				Arrays.asList(
				    RewriteRule.of('f', "ffffff"),
				    RewriteRule.of('F', "F+f-FF+F+FF+Ff+FF-f+FF-F-FF-Ff-FFF")
				),
				"F+F+F+F"
		);
	}

}
