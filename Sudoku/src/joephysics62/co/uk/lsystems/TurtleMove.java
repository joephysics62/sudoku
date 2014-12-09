package joephysics62.co.uk.lsystems;

interface TurtleMove {
	int moveUnits();
	TurtleTurn angleChange();
	StackChange stackChange();
	char id();
}