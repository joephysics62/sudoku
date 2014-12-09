package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.RewriteSystem;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleMoves;

import com.google.common.primitives.Chars;

public abstract class CharacterMapLSystem implements LSystem {

	private final String _axiomString;
  private final TurtleMoves _moves;
  private final RewriteSystem _rewriteRules;

	public CharacterMapLSystem(final TurtleMoves moves, final RewriteSystem rewriteRules, final String axiomString) {
		_moves = moves;
    _rewriteRules = rewriteRules;
    _axiomString = axiomString;
	}

	@Override
	public final List<Turtle> axiom() {
		return stringAsMoves(_axiomString);
	}

	@Override
	public final List<Turtle> applyRule(final Turtle input) {
		if (_rewriteRules.hasKey(input.id())) {
			return stringAsMoves(_rewriteRules.ruleForKey(input.id()).getTo());
		}
		return Arrays.asList(input);
	}

	private List<Turtle> stringAsMoves(final String string) {
		return Chars.asList(string.toCharArray()).stream().map(_moves::byId).collect(Collectors.toList());
	}

}
