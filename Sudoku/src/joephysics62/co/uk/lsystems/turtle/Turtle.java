package joephysics62.co.uk.lsystems.turtle;


public interface Turtle {
	int moveUnits();
	TurtleTurn angleChange();
	StackChange stackChange();
	char id();

	public static Turtle left(final char id) {
		return new SimpleTurtle(id, 0, StackChange.NONE, TurtleTurn.LEFT);
	}

	public static Turtle right(final char id) {
		return new SimpleTurtle(id, 0, StackChange.NONE, TurtleTurn.RIGHT);
	}

	public static Turtle push(final char id) {
		return new SimpleTurtle(id, 0, StackChange.PUSH, TurtleTurn.NONE);
	}

	public static Turtle pop(final char id) {
		return new SimpleTurtle(id, 0, StackChange.POP, TurtleTurn.NONE);
	}

	public static Turtle forward(final char id) {
		return new SimpleTurtle(id, 1, StackChange.NONE, TurtleTurn.NONE);
	}

	public static Turtle identity(final char id) {
		return new SimpleTurtle(id, 0, StackChange.NONE, TurtleTurn.NONE);
	}
}