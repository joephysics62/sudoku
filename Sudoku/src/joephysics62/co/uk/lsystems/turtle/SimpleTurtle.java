package joephysics62.co.uk.lsystems.turtle;


public class SimpleTurtle implements Turtle {
	private final char _id;
	private final int _moveUnits;
	private final StackChange _stackChange;
	private final TurtleTurn _turn;

	public SimpleTurtle(final char id, final int moveUnits, final StackChange stackChange, final TurtleTurn turn) {
		_id = id;
		_moveUnits = moveUnits;
		_stackChange = stackChange;
		_turn = turn;
	}

	@Override
	public int moveUnits() {
		return _moveUnits;
	}

	@Override
	public TurtleTurn angleChange() {
		return _turn;
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