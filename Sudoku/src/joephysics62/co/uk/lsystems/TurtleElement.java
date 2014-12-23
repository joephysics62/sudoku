package joephysics62.co.uk.lsystems;

public class TurtleElement {
  private final Character _id;
  private final double[] _parameters;

  private TurtleElement(final Character id, final double... parameters) {
    _id = id;
    _parameters = parameters;
  }

  public Character getId() {
    return _id;
  }
  public double[] getParameters() {
    return _parameters;
  }

  private static final TurtleElement PUSH = new TurtleElement('[');
  private static final TurtleElement POP = new TurtleElement(']');
  private static final TurtleElement ROLL_LEFT_FLAT = new TurtleElement('$');
  private static final TurtleElement UTURN = new TurtleElement('|');

  public static TurtleElement push() { return PUSH; }
  public static TurtleElement pop() { return POP; }
  public static TurtleElement rollLeftFlat() { return ROLL_LEFT_FLAT; }
  public static TurtleElement drawf(final double distance) { return new TurtleElement('F', distance); }

  public static TurtleElement left(final double angle) { return new TurtleElement('+', angle); }
  public static TurtleElement right(final double angle) { return new TurtleElement('-', angle); }

  public static TurtleElement pitchUp(final double angle) { return new TurtleElement('^', angle); }
  public static TurtleElement pitchDown(final double angle) { return new TurtleElement('&', angle); }

  public static TurtleElement rollLeft(final double angle) { return new TurtleElement('/', angle); }
  public static TurtleElement rollRight(final double angle) { return new TurtleElement('\\', angle); }

  public static TurtleElement width(final double width) { return new TurtleElement('£', width); }
  public static TurtleElement narrow(final double factor) { return new TurtleElement('!', factor); }

  public static TurtleElement uturn() { return UTURN; }

  public static TurtleElement create(final char id, final double... parameters) { return new TurtleElement(id, parameters); }

}
