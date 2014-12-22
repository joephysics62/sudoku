package joephysics62.co.uk.lsystems.graphics;

import javafx.scene.Group;
import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.LSystemTurtleInterpreter;
import joephysics62.co.uk.lsystems.examples.BushExample3d;

public class LSystem3dViewer extends Simple3dViewer {

  private static final LSystem LSYSTEM = new BushExample3d();
  private static final int ITERATIONS = 7;

  @Override
  protected Group createContentGroup() {
    final Group output = new Group();

    final LSystemTurtleInterpreter turtleInterpreter = new LSystemTurtleInterpreter(LSYSTEM);
    for (final Line3D line3d : turtleInterpreter.interpret(LSYSTEM.generate(ITERATIONS))) {
      final double radius = line3d.getWidth() / 2;
      output.getChildren().add(new ConnectingCylinder(line3d.getStart(), line3d.getEnd(), line3d.getColour(), radius));
    }
    return output;
  }

  public static void main(final String[] args) {
    launch(args);
  }

}
