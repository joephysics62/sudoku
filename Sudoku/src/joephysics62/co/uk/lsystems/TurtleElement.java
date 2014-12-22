package joephysics62.co.uk.lsystems;

public class TurtleElement {
  private final Character _id;
  private final double _parameter;

  public TurtleElement(final Character id, final double parameter) {
    _id = id;
    _parameter = parameter;
  }

  public Character getId() {
    return _id;
  }
  public double getParameter() {
    return _parameter;
  }
}
