package joephysics62.co.uk.lsystems;

import java.util.List;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.turtle.Turtle;

public class LSystemGenerator {

  private static final int MAX_ELEMENTS = 50000;

  public List<Turtle> generate(final LSystem lsystem, final int limit) {
		List<Turtle> current = lsystem.axiom();
		for (int i = 0; i < limit; i++) {
		  if (current.size() > MAX_ELEMENTS) {
		    System.err.println("Max size " + MAX_ELEMENTS + " exceeded, stop iterating at depth " + i);
		    return current;
		  }
			current = current.stream().map(lsystem::applyRule).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return current;
	}


}
