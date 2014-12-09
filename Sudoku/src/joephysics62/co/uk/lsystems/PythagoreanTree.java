package joephysics62.co.uk.lsystems;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PythagoreanTree implements LSystem {

	private final SimpleTurtleMove _pushTurnLeft;
	private final SimpleTurtleMove _popTurnRight;
	private final SimpleTurtleMove _stick;
	private final SimpleTurtleMove _leaf;

	private final Map<TurtleMove, List<TurtleMove>> _map = new LinkedHashMap<>();
	
	public PythagoreanTree() {
		_pushTurnLeft = new SimpleTurtleMove('[', 0, StackChange.PUSH, TurtleTurn.LEFT);
		_popTurnRight = new SimpleTurtleMove(']', 0, StackChange.POP, TurtleTurn.RIGHT);
		_stick = new SimpleTurtleMove('0', 1, StackChange.NONE, TurtleTurn.NONE);
		_leaf = new SimpleTurtleMove('1', 1, StackChange.NONE, TurtleTurn.NONE);
		_map.put(_stick, Arrays.asList(_leaf, _pushTurnLeft, _stick, _popTurnRight, _stick));
		_map.put(_leaf, Arrays.asList(_leaf, _leaf));
	}

	@Override
	public List<TurtleMove> axiom() {
		return Arrays.asList(_stick);
	}

	@Override
	public List<TurtleMove> applyRule(TurtleMove input) {
		if (_map.containsKey(input)) {
			return _map.get(input);
		}
		return Arrays.asList(input);
	}

}
