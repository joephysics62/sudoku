package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.SimpleTurtleMove;
import joephysics62.co.uk.lsystems.turtle.StackChange;
import joephysics62.co.uk.lsystems.turtle.TurtleTurn;

public class DragonCurve extends CharacterMapLSystem {

	public DragonCurve() {
		super(
				Arrays.asList(
						new SimpleTurtleMove('F', 1, StackChange.NONE, TurtleTurn.NONE),
						new SimpleTurtleMove('-', 0, StackChange.NONE, TurtleTurn.LEFT),
						new SimpleTurtleMove('+', 0, StackChange.NONE, TurtleTurn.RIGHT),
						new SimpleTurtleMove('X', 0, StackChange.NONE, TurtleTurn.NONE),
						new SimpleTurtleMove('Y', 0, StackChange.NONE, TurtleTurn.NONE)
						),
						Arrays.asList(RewriteRule.of('X', "X+YF"), RewriteRule.of('Y', "FX-Y")),
						"FX"
				);
	}

}
