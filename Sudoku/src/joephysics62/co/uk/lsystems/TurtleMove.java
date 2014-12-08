package joephysics62.co.uk.lsystems;

interface TurtleMove {
	int moveUnits();
	double angleChange();
	boolean doPush();
	boolean doPop();
	char id();
}