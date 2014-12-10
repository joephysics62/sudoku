package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.DeterministicRewriteSystem;
import joephysics62.co.uk.lsystems.Rewrite;
import joephysics62.co.uk.lsystems.RewriteSystem;
import joephysics62.co.uk.lsystems.turtle.SimpleTurtle;
import joephysics62.co.uk.lsystems.turtle.StackChange;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;
import joephysics62.co.uk.lsystems.turtle.TurtleTurn;

public class PythagoreanTree extends CharacterMapLSystem {
	private static final String AXIOM = "0";

	private static final RewriteSystem REWRITE_RULES = new DeterministicRewriteSystem(Rewrite.of('0', "1[0]0"), Rewrite.of('1', "11"));

	private static final TurtleMoves MOVES = new TurtleMoves(
			new SimpleTurtle('[', 0, false, StackChange.PUSH, TurtleTurn.LEFT),
			new SimpleTurtle(']', 0, false, StackChange.POP, TurtleTurn.RIGHT),
			Turtle.draw('0'),
			Turtle.draw('1')
			);

	public PythagoreanTree() {
		super(MOVES, REWRITE_RULES, AXIOM, 45.0);
	}

}
