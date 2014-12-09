package joephysics62.co.uk.lsystems.turtle;


public interface TurtleMove {
	int moveUnits();
	TurtleTurn angleChange();
	StackChange stackChange();
	char id();
}