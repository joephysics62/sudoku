package joephysics62.co.uk.lsystems.turtle;

import joephysics62.co.uk.lsystems.turtle.modules.Draw;
import joephysics62.co.uk.lsystems.turtle.modules.Identity;
import joephysics62.co.uk.lsystems.turtle.modules.IncrementColour;
import joephysics62.co.uk.lsystems.turtle.modules.Left;
import joephysics62.co.uk.lsystems.turtle.modules.Move;
import joephysics62.co.uk.lsystems.turtle.modules.Narrow;
import joephysics62.co.uk.lsystems.turtle.modules.PitchDown;
import joephysics62.co.uk.lsystems.turtle.modules.PitchUp;
import joephysics62.co.uk.lsystems.turtle.modules.Pop;
import joephysics62.co.uk.lsystems.turtle.modules.Push;
import joephysics62.co.uk.lsystems.turtle.modules.Right;
import joephysics62.co.uk.lsystems.turtle.modules.RollLeft;
import joephysics62.co.uk.lsystems.turtle.modules.RollLeftFlat;
import joephysics62.co.uk.lsystems.turtle.modules.RollRight;
import joephysics62.co.uk.lsystems.turtle.modules.UTurn;
import joephysics62.co.uk.lsystems.turtle.modules.Width;

public abstract class Module implements IModule {
  private final Character _id;
  private final double[] _parameters;

  public Module(final Character id, final double... parameters) {
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

  private static final Module PUSH = new Push();
  private static final Module POP = new Pop();
  private static final Module ROLL_LEFT_FLAT = new RollLeftFlat();
  private static final Module UTURN = new UTurn();
  private static final Module COLOUR_CHANGE = new IncrementColour();

  public static Module push() { return PUSH; }
  public static Module pop() { return POP; }
  public static Module rollLeftFlat() { return ROLL_LEFT_FLAT; }
  public static Module drawf(final double distance) { return draw('F', distance); }
  public static Module draw(final char id, final double distance) { return new Draw(id, distance); }
  public static Module move(final char id, final double distance) { return new Move(id, distance); }

  public static Module left(final double angle) { return new Left(angle); }
  public static Module right(final double angle) { return new Right(angle); }

  public static Module pitchUp(final double angle) { return new PitchUp(angle); }
  public static Module pitchDown(final double angle) { return new PitchDown(angle); }

  public static Module rollLeft(final double angle) { return new RollLeft(angle); }
  public static Module rollRight(final double angle) { return new RollRight(angle); }

  public static Module width(final double width) { return new Width(width); }
  public static Module narrow(final double factor) { return new Narrow(factor); }

  public static Module uturn() { return UTURN; }

  public static Module identity(final char id, final double... parameters) { return new Identity(id, parameters); }

  public static Module incrementColour() { return COLOUR_CHANGE; }

}
