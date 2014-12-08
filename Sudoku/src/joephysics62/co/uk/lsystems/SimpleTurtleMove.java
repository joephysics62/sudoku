package joephysics62.co.uk.lsystems;

class SimpleTurtleMove implements TurtleMove {
	private char _id;
	private int _moveUnits;
	private StackChange _stackChange;
	private double _angleChange;

	public SimpleTurtleMove(final char id, final int moveUnits, final StackChange stackChange, final double angleChange) {
		_id = id;
		_moveUnits = moveUnits;
		_stackChange = stackChange;
		_angleChange = angleChange;
	}

	@Override
	public int moveUnits() {
		return _moveUnits;
	}

	@Override
	public double angleChange() {
		return _angleChange;
	}

	@Override
	public StackChange stackChange() {
		return _stackChange;
	}

	@Override
	public char id() {
		return _id;
	}
}