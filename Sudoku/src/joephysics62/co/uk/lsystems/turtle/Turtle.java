package joephysics62.co.uk.lsystems.turtle;


public interface Turtle {
	int moveUnits();
	boolean draw();
	TurtleTurn angleChange();
	StackChange stackChange();
	char id();

	public static Turtle left(final char id) {
		return new SimpleTurtle(id, 0, false, StackChange.NONE, TurtleTurn.LEFT);
	}

	public static Turtle right(final char id) {
		return new SimpleTurtle(id, 0, false, StackChange.NONE, TurtleTurn.RIGHT);
	}

	public static Turtle push(final char id) {
		return new SimpleTurtle(id, 0, false, StackChange.PUSH, TurtleTurn.NONE);
	}

	public static Turtle pop(final char id) {
		return new SimpleTurtle(id, 0, false, StackChange.POP, TurtleTurn.NONE);
	}

	public static Turtle draw(final char id) {
		return new SimpleTurtle(id, 1, true, StackChange.NONE, TurtleTurn.NONE);
	}

  public static Turtle move(final char id) {
    return new SimpleTurtle(id, 1, false, StackChange.NONE, TurtleTurn.NONE);
  }

	public static Turtle identity(final char id) {
		return new SimpleTurtle(id, 0, false, StackChange.NONE, TurtleTurn.NONE);
	}
}