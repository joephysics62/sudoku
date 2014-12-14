package joephysics62.co.uk.lsystems;

import java.util.stream.Collectors;

public class LSystemGenerator {

  private static final int MAX_ELEMENTS = 50000;

  public String generate(final LSystem lsystem, final int limit) {
		String current = lsystem.axiom();
		for (int i = 0; i < limit; i++) {
		  if (current.length() > MAX_ELEMENTS) {
		    System.err.println("Max size " + MAX_ELEMENTS + " exceeded, stop iterating at depth " + i);
		    return current;
		  }
		  current = current.chars().mapToObj(c -> (char) c).map(lsystem::applyRule).collect(Collectors.joining());
		}
		return current;
	}


}
