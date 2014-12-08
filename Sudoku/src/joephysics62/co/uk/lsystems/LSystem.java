package joephysics62.co.uk.lsystems;

import java.util.List;

public interface LSystem {

	List<TurtleMove> axiom();

	List<TurtleMove> applyRule(TurtleMove input);

}