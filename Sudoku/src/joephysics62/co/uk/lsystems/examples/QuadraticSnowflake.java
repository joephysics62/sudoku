package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class QuadraticSnowflake extends CharacterMapLSystem {

	public QuadraticSnowflake() {
		super(
		    new TurtleMoves(Turtle.draw('F'), Turtle.left('-'), Turtle.right('+')),
		    new DeterministicRewriteSystem(Rewrite.of('F', "F+F-F-F+F")),
				"-F",
				60.0
				);
	}

}
