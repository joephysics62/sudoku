package joephysics62.co.uk.lsystems.turtle;


public class SimpleTurtle implements Turtle {
	private final char _id;
	private final int _moveUnits;
	private final StackChange _stackChange;
	private final TurtleTurn _turn;
  private final boolean _draw;

	public SimpleTurtle(final char id, final int moveUnits, final boolean draw, final StackChange stackChange, final TurtleTurn turn) {
		_id = id;
		_moveUnits = moveUnits;
    _draw = draw;
		_stackChange = stackChange;
		_turn = turn;
	}

	@Override public int moveUnits() { return _moveUnits; }
	@Override public boolean draw() { return _draw; }
	@Override public TurtleTurn angleChange() { return _turn; }
	@Override public StackChange stackChange() { return _stackChange; }
	@Override public char id() { return _id; }
}