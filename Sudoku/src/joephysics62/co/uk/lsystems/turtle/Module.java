package joephysics62.co.uk.lsystems.turtle;

public class Module implements IModule {
  private final Character _id;
  private final double[] _parameters;

  private Module(final Character id, final double... parameters) {
    _id = id;
    _parameters = parameters;
  }

  @Override
  public Character getId() {
    return _id;
  }
  @Override
  public double[] getParameters() {
    return _parameters;
  }

  private static final Module PUSH = new Module('[');
  private static final Module POP = new Module(']');
  private static final Module ROLL_LEFT_FLAT = new Module('$');
  private static final Module UTURN = new Module('|');

  public static Module push() { return PUSH; }
  public static Module pop() { return POP; }
  public static Module rollLeftFlat() { return ROLL_LEFT_FLAT; }
  public static Module drawf(final double distance) { return new Module('F', distance); }

  public static Module left(final double angle) { return new Module('+', angle); }
  public static Module right(final double angle) { return new Module('-', angle); }

  public static Module pitchUp(final double angle) { return new Module('^', angle); }
  public static Module pitchDown(final double angle) { return new Module('&', angle); }

  public static Module rollLeft(final double angle) { return new Module('/', angle); }
  public static Module rollRight(final double angle) { return new Module('\\', angle); }

  public static Module width(final double width) { return new Module('£', width); }
  public static Module narrow(final double factor) { return new Module('!', factor); }

  public static Module uturn() { return UTURN; }

  public static Module create(final char id, final double... parameters) { return new Module(id, parameters); }

}
