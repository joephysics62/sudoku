package joephysics62.co.uk.lsystems;

public class TurtleElement {
  private final Character _id;
  private final double[] _parameters;

  public TurtleElement(final Character id, final double... parameters) {
    _id = id;
    _parameters = parameters;
  }

  public Character getId() {
    return _id;
  }
  public double[] getParameters() {
    return _parameters;
  }
}
