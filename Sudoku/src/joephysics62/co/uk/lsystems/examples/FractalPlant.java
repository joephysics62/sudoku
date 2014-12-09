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

public class FractalPlant implements LSystem {

	private final Map<Character, String> _map = new LinkedHashMap<>();
	private final Map<Character, TurtleMove> _moveKey = new LinkedHashMap<>();

	public FractalPlant() {
		_moveKey.put('[', new SimpleTurtleMove('[', 0, StackChange.PUSH, TurtleTurn.NONE));
		_moveKey.put(']', new SimpleTurtleMove('[', 0, StackChange.POP, TurtleTurn.NONE));
		_moveKey.put('F', new SimpleTurtleMove('F', 1, StackChange.NONE, TurtleTurn.NONE));
		_moveKey.put('-', new SimpleTurtleMove('-', 0, StackChange.NONE, TurtleTurn.LEFT));
		_moveKey.put('+', new SimpleTurtleMove('+', 0, StackChange.NONE, TurtleTurn.RIGHT));
		_moveKey.put('X', new SimpleTurtleMove('X', 0, StackChange.NONE, TurtleTurn.NONE));

		_map.put('F', "FF");
		_map.put('X', "F-[[X]+X]+F[+FX]-X");
	}

	@Override
	public List<TurtleMove> axiom() {
		return Arrays.asList(_moveKey.get('X'));
	}

	@Override
	public List<TurtleMove> applyRule(final TurtleMove input) {
		if (_map.containsKey(input.id())) {
			return Chars.asList(_map.get(input.id()).toCharArray()).stream().map(_moveKey::get).collect(Collectors.toList());
		}
		return Arrays.asList(input);
	}

}
