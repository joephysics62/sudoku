package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class FractalPlant extends CharacterMapLSystem {

	public FractalPlant() {
		super(
				Arrays.asList(
						Turtle.push('['),
						Turtle.pop(']'),
						Turtle.forward('F'),
						Turtle.left('-'),
						Turtle.right('+'),
						Turtle.identity('X')
						),
						Arrays.asList(RewriteRule.of('F', "FF"), RewriteRule.of('X', "F-[[X]+X]+F[+FX]-X")),
						"X"
				);

	}

}
