package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.RewriteRule;
import joephysics62.co.uk.lsystems.turtle.Turtle;

import com.google.common.primitives.Chars;

public abstract class CharacterMapLSystem implements LSystem {

	private final Map<Character, String> _rewriteMap = new LinkedHashMap<>();
	private final Map<Character, Turtle> _moveMap = new LinkedHashMap<>();
	private final String _axiomString;

	public CharacterMapLSystem(final List<Turtle> moves, final List<RewriteRule> rewriteRules, final String axiomString) {
		_axiomString = axiomString;
		for (final RewriteRule rewriteRule : rewriteRules) {
			_rewriteMap.put(rewriteRule.getFrom(), rewriteRule.getTo());
		}
		for (final Turtle move : moves) {
			_moveMap.put(move.id(), move);
		}
	}

	@Override
	public final List<Turtle> axiom() {
		return stringAsMoves(_axiomString);
	}

	@Override
	public final List<Turtle> applyRule(final Turtle input) {
		if (_rewriteMap.containsKey(input.id())) {
			return stringAsMoves(_rewriteMap.get(input.id()));
		}
		return Arrays.asList(input);
	}

	private List<Turtle> stringAsMoves(final String string) {
		return Chars.asList(string.toCharArray()).stream().map(_moveMap::get).collect(Collectors.toList());
	}

}
