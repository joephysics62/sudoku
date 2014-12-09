package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class DragonCurve extends CharacterMapLSystem {

	public DragonCurve() {
		super(
				new TurtleMoves(
						Turtle.draw('F'),
						Turtle.left('-'),
						Turtle.right('+'),
						Turtle.identity('X'),
						Turtle.identity('Y')
						),
			   new DeterministicRewriteSystem(Rewrite.of('X', "X+YF"), Rewrite.of('Y', "FX-Y")),
						"FX"
				);
	}

}
