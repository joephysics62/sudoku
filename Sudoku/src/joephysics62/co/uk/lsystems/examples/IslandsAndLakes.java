package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class IslandsAndLakes extends CharacterMapLSystem {

	public IslandsAndLakes() {
		super(
		    new TurtleMoves(Turtle.draw('F'), Turtle.move('f'), Turtle.left('-'), Turtle.right('+')),
		    new DeterministicRewriteSystem(
				    Rewrite.of('f', "ffffff"),
				    Rewrite.of('F', "F+f-FF+F+FF+Ff+FF-f+FF-F-FF-Ff-FFF")
				),
				"F+F+F+F",
				90.0
		);
	}

}
