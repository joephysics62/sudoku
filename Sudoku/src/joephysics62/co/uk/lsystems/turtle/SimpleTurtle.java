package joephysics62.co.uk.lsystems.turtle;


public class SimpleTurtle implements Turtle {
	private final char _id;
	private final int _moveUnits;
	private final StackChange _stackChange;
	private final Turn _turn;
  private final boolean _draw;
  private final WidthChange _narrow;

  public SimpleTurtle(final char id, final int moveUnits, final boolean draw, final StackChange stackChange, final Turn turn) {
    this(id, moveUnits, draw, stackChange, turn, WidthChange.NONE);
  }

	public SimpleTurtle(final char id, final int moveUnits, final boolean draw, final StackChange stackChange, final Turn turn, final WidthChange narrow) {
		_id = id;
		_moveUnits = moveUnits;
    _draw = draw;
		_stackChange = stackChange;
		_turn = turn;
    _narrow = narrow;
	}

	@Override public int moveUnits() { return _moveUnits; }
	@Override public boolean draw() { return _draw; }
	@Override public Turn angleChange() { return _turn; }
	@Override public StackChange stackChange() { return _stackChange; }
	@Override public WidthChange getWidthChange() { return _narrow; }
	@Override public char id() { return _id; }
}