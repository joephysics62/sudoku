package joephysics62.co.uk.lsystems;

import java.util.List;


public interface LSystem<T> {

  public static final int MAX_ELEMENTS = 50000;

	List<T> axiom();

	List<Rule<T>> rules();

  List<T> generate(int iterations);

}