package joephysics62.co.uk.lsystems;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PythagoreanTree implements LSystem {
	
	private static class UnitForward implements TurtleMove {
		private char _id;
		private UnitForward(char id) { _id = id; }
		@Override public int moveUnits() { return 1; }
		@Override public double angleChange() { return 0; }
		@Override public boolean doPush() { return false; }
		@Override public boolean doPop() { return false; }
		@Override public char id() { return _id; }
	}
	
	private static final TurtleMove F0 = new UnitForward('0'); 
	private static final TurtleMove F1 = new UnitForward('1');
	private static final TurtleMove LB = new TurtleMove() {
		@Override public int moveUnits() { return 0; }
		@Override public char id() { return '['; }
		@Override public boolean doPush() { return true; }
		@Override public boolean doPop() { return false; }
		@Override public double angleChange() { return Math.PI / 4; }
	};
	private static final TurtleMove RB = new TurtleMove() {
		@Override public int moveUnits() { return 0; }
		@Override public char id() { return '['; }
		@Override public boolean doPush() { return false; }
		@Override public boolean doPop() { return true; }
		@Override public double angleChange() { return -Math.PI / 4; }
	};

	private static final Map<TurtleMove, List<TurtleMove>> MAP = new LinkedHashMap<>();
	static {
		MAP.put(F0, Arrays.asList(F1, LB, F0, RB, F0));
		MAP.put(F1, Arrays.asList(F1, F1));
	}
	
	@Override
	public List<TurtleMove> axiom() {
		return Arrays.asList(F0);
	}

	@Override
	public List<TurtleMove> applyRule(TurtleMove input) {
		if (MAP.containsKey(input)) {
			return MAP.get(input);
		}
		return Arrays.asList(input);
	}

}
