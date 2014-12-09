package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.SimpleTurtleMove;
import joephysics62.co.uk.lsystems.turtle.StackChange;
import joephysics62.co.uk.lsystems.turtle.TurtleMove;
import joephysics62.co.uk.lsystems.turtle.TurtleTurn;

public class PythagoreanTree extends CharacterMapLSystem {
	private static final String AXIOM = "0";

	private static final List<RewriteRule> REWRITE_RULES = Arrays.asList(RewriteRule.of('0', "1[0]0"), RewriteRule.of('1', "11"));

	private static final List<TurtleMove> MOVES = Arrays.asList(
			new SimpleTurtleMove('[', 0, StackChange.PUSH, TurtleTurn.LEFT),
			new SimpleTurtleMove(']', 0, StackChange.POP, TurtleTurn.RIGHT),
			new SimpleTurtleMove('0', 1, StackChange.NONE, TurtleTurn.NONE),
			new SimpleTurtleMove('1', 1, StackChange.NONE, TurtleTurn.NONE)
			);

	public PythagoreanTree() {
		super(MOVES, REWRITE_RULES, AXIOM);
	}

}
