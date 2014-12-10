package joephysics62.co.uk.lsystems.turtle;


public interface Turtle {
	int moveUnits();
	boolean draw();
	Turn angleChange();
	StackChange stackChange();
	char id();

	public static Turtle left(final char id) { return new TurtleTurn(id, Turn.LEFT); }
	public static Turtle right(final char id) { return new TurtleTurn(id, Turn.RIGHT); }
	public static Turtle up(final char id) { return new TurtleTurn(id, Turn.UP); }
	public static Turtle down(final char id) { return new TurtleTurn(id, Turn.DOWN); }
  public static Turtle rollLeft(final char id) { return new TurtleTurn(id, Turn.ROLL_LEFT); }
  public static Turtle rollRight(final char id) { return new TurtleTurn(id, Turn.ROLL_RIGHT); }

	public static Turtle push(final char id) { return new SimpleTurtle(id, 0, false, StackChange.PUSH, Turn.NONE); }
	public static Turtle pop(final char id) { return new SimpleTurtle(id, 0, false, StackChange.POP, Turn.NONE); }

	public static Turtle draw(final char id) { return new SimpleTurtle(id, 1, true, StackChange.NONE, Turn.NONE); }
  public static Turtle move(final char id) { return new SimpleTurtle(id, 1, false, StackChange.NONE, Turn.NONE); }

	public static Turtle identity(final char id) { return new SimpleTurtle(id, 0, false, StackChange.NONE, Turn.NONE); }
}