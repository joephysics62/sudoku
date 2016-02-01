package joephysics62.co.uk.kenken;

import java.util.Collections;
import java.util.List;


public class PuzzleFile {
  private List<ArithmeticConstraint> _arithmeticraints;
  private int _height;

  public void setArithmeticConstraints(final List<ArithmeticConstraint> arithmeticConstraints) {
    _arithmeticraints = arithmeticConstraints;
  }
  public List<ArithmeticConstraint> getArithmeticConstraints() {
    return _arithmeticraints;
  }

  public int getHeight() {
    return _height;
  }
  public void setHeight(final int height) {
    _height = height;
  }

  public KenKen toPuzzle() {
    return new KenKen(_height, Collections.unmodifiableList(_arithmeticraints));
  }
}
