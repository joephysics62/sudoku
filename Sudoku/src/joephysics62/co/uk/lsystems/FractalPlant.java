package joephysics62.co.uk.lsystems;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FractalPlant implements LSystem {

	private final SimpleTurtleMove _push;
	private final SimpleTurtleMove _pop;
	private final SimpleTurtleMove _forward;
	private final SimpleTurtleMove _left;
	private final SimpleTurtleMove _right;
	private final SimpleTurtleMove _noop;

	private final Map<TurtleMove, List<TurtleMove>> _map = new LinkedHashMap<>();
	
	public FractalPlant(double angle) {
		_push = new SimpleTurtleMove('[', 0, StackChange.PUSH, 0);
		_pop = new SimpleTurtleMove(']', 0, StackChange.POP, 0);
		_forward = new SimpleTurtleMove('F', 1, StackChange.NONE, 0);
		_left = new SimpleTurtleMove('+', 0, StackChange.NONE, angle);
		_right = new SimpleTurtleMove('-', 0, StackChange.NONE, -angle);
		_noop = new SimpleTurtleMove('X', 0, StackChange.NONE, 0);

		_map.put(_forward, Arrays.asList(_forward, _forward));
		_map.put(_noop, Arrays.asList(
				_forward, _right, _push, _push, _noop, _pop, _left, _noop, _pop, 
				_left, _forward, _push, _left, _forward, _noop, _pop, _right, _noop));
	}

	@Override
	public List<TurtleMove> axiom() {
		return Arrays.asList(_noop);
	}

	@Override
	public List<TurtleMove> applyRule(TurtleMove input) {
		if (_map.containsKey(input)) {
			return _map.get(input);
		}
		return Arrays.asList(input);
	}

}
