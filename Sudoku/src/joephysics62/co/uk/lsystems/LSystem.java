package joephysics62.co.uk.lsystems;

import java.util.List;


public interface LSystem<T> {

  public static final int MAX_ELEMENTS = 50000;

	List<T> axiom();

	List<T> applyRule(int index, List<T> current);

  List<T> generate(int iterations);

}