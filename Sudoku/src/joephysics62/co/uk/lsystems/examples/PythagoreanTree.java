package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.turtle.SimpleTurtleMove;
import joephysics62.co.uk.lsystems.turtle.StackChange;
import joephysics62.co.uk.lsystems.turtle.TurtleMove;
import joephysics62.co.uk.lsystems.turtle.TurtleTurn;

import com.google.common.primitives.Chars;

public class PythagoreanTree implements LSystem {
	private final Map<Character, String> _map = new LinkedHashMap<>();
	private final Map<Character, TurtleMove> _moveKey = new LinkedHashMap<>();

	public PythagoreanTree() {

		_moveKey.put('[', new SimpleTurtleMove('[', 0, StackChange.PUSH, TurtleTurn.LEFT));
		_moveKey.put(']', new SimpleTurtleMove(']', 0, StackChange.POP, TurtleTurn.RIGHT));
		_moveKey.put('0', new SimpleTurtleMove('0', 1, StackChange.NONE, TurtleTurn.NONE));
		_moveKey.put('1', new SimpleTurtleMove('1', 1, StackChange.NONE, TurtleTurn.NONE));

		_map.put('0', "1[0]0");
		_map.put('1', "11");
	}

	@Override
	public List<TurtleMove> axiom() {
		return Arrays.asList(_moveKey.get('0'));
	}

	@Override
	public List<TurtleMove> applyRule(final TurtleMove input) {
		if (_map.containsKey(input.id())) {
			return Chars.asList(_map.get(input.id()).toCharArray()).stream().map(_moveKey::get).collect(Collectors.toList());
		}
		return Arrays.asList(input);
	}

}
