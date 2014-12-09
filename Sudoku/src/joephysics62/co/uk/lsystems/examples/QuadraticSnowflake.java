package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class QuadraticSnowflake extends CharacterMapLSystem {

	public QuadraticSnowflake() {
		super(
				Arrays.asList(Turtle.draw('F'), Turtle.left('-'), Turtle.right('+')),
				Arrays.asList(RewriteRule.of('F', "F+F-F-F+F")),
				"-F"
				);
	}

}
