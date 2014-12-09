package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class DragonCurve extends CharacterMapLSystem {

	public DragonCurve() {
		super(
				Arrays.asList(
						Turtle.draw('F'),
						Turtle.left('-'),
						Turtle.right('+'),
						Turtle.identity('X'),
						Turtle.identity('Y')
						),
						Arrays.asList(RewriteRule.of('X', "X+YF"), RewriteRule.of('Y', "FX-Y")),
						"FX"
				);
	}

}
