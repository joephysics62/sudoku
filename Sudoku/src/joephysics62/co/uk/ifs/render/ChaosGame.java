package joephysics62.co.uk.ifs.render;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javafx.geometry.Point3D;
import joephysics62.co.uk.ifs.IFSRender;
import joephysics62.co.uk.ifs.IteratedFunctionSystem;

public class ChaosGame implements IFSRender {

  private final int _iterations;
  private final Point3D _start;
  private static final double SCALE = 200;

  public ChaosGame(final int iterations, final Point3D start) {
    _iterations = iterations;
    _start = start;
  }

  @Override
  public BufferedImage render(final int height, final IteratedFunctionSystem ifs) {
    final int width = (int) (1.5 * height);
    Point3D curr = _start;

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < _iterations; i++) {
      final int x = (int) (SCALE * curr.getX()) + width / 2;
      final int y = (int) (SCALE * curr.getY()) + height / 2;
      if (x > 0 && y > 0 && x < width && y < height) {
        final Color current = new Color(bi.getRGB(x, y));
        final Color newC = new Color(simulateAlpha(current.getRed()), simulateAlpha(current.getGreen()), simulateAlpha(current.getBlue()));
        bi.setRGB(x, y, newC.getRGB());
      }
      curr = ifs.transform(curr);
    }
    return bi;
  }

  private int simulateAlpha(final int input) {
    return Math.min(255, input + 50);
  }

}
