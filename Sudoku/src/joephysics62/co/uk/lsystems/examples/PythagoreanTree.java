package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.SimpleTurtle;
import joephysics62.co.uk.lsystems.turtle.StackChange;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleTurn;

public class PythagoreanTree extends CharacterMapLSystem {
	private static final String AXIOM = "0";

	private static final List<RewriteRule> REWRITE_RULES = Arrays.asList(RewriteRule.of('0', "1[0]0"), RewriteRule.of('1', "11"));

	private static final List<Turtle> MOVES = Arrays.asList(
			new SimpleTurtle('[', 0, false, StackChange.PUSH, TurtleTurn.LEFT),
			new SimpleTurtle(']', 0, false, StackChange.POP, TurtleTurn.RIGHT),
			Turtle.draw('0'),
			Turtle.draw('1')
			);

	public PythagoreanTree() {
		super(MOVES, REWRITE_RULES, AXIOM);
	}

}
