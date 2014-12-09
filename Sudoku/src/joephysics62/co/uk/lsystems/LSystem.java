package joephysics62.co.uk.lsystems;

import java.util.List;

import joephysics62.co.uk.lsystems.turtle.TurtleMove;

public interface LSystem {

	List<TurtleMove> axiom();

	List<TurtleMove> applyRule(TurtleMove input);

}