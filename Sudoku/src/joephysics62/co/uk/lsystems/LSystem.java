package joephysics62.co.uk.lsystems;

import java.util.List;


public interface LSystem {

  public static final int MAX_ELEMENTS = 50000;

	List<Character> axiom();

	List<Character> applyRule(Character input);

	List<Character> generate(int iterations);
}