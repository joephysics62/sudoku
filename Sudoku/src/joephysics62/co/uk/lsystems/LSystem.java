package joephysics62.co.uk.lsystems;

import java.util.List;

import joephysics62.co.uk.lsystems.rules.Rule;


public interface LSystem<T> {

  public static final int MAX_ELEMENTS = 50000;

	List<T> axiom();

	List<Rule<T>> rules();

  List<T> generate(int iterations);

}