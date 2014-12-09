package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

public class FractalPlant extends CharacterMapLSystem {

	public FractalPlant() {
		super(
		    new TurtleMoves(
						Turtle.push('['),
						Turtle.pop(']'),
						Turtle.draw('F'),
						Turtle.left('-'),
						Turtle.right('+'),
						Turtle.identity('X')
						),
						new DeterministicRewriteSystem(Rewrite.of('F', "FF"), Rewrite.of('X', "F-[[X]+X]+F[+FX]-X")),
						"X"
				);

	}

}
