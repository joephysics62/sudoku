package joephysics62.co.uk.lsystems.graphics;

import javafx.scene.Group;
import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.LSystemTurtleInterpreter;
import joephysics62.co.uk.lsystems.examples.MonopodialTrees;

public class LSystem3dViewer extends Simple3dViewer {

  private static final LSystem LSYSTEM = new MonopodialTrees();
  private static final int ITERATIONS = 10;

  @Override
  protected Group createContentGroup() {
    final Group output = new Group();

    final LSystemTurtleInterpreter turtleInterpreter = new LSystemTurtleInterpreter();
    for (final Line3D line3d : turtleInterpreter.interpret(LSYSTEM.generate(ITERATIONS))) {
      final double radius = line3d.getWidth() / 2;
      output.getChildren().add(new ConnectingCylinder(line3d.getStart(), line3d.getEnd(), LSYSTEM.indexedColour(line3d.getColour()), radius));
    }
    return output;
  }

  public static void main(final String[] args) {
    launch(args);
  }

}
