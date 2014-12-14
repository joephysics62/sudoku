package joephysics62.co.uk.lsystems.graphics;

import java.awt.Graphics2D;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.turtle.TurtleListener;

public class Graphics2DListener implements TurtleListener {

  private final Graphics2D _g2d;

  public Graphics2DListener(final Graphics2D g2d) {
    _g2d = g2d;
  }

  @Override
  public void drawLine(final Point3D start, final Point3D end, final Color color, final double width) {
    _g2d.setColor(new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue()));
    _g2d.drawLine((int) start.getX(), (int) start.getZ(), (int) end.getX(), (int) end.getZ());
  }

}
