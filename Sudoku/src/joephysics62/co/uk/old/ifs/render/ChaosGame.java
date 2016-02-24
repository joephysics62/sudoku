package joephysics62.co.uk.old.ifs.render;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javafx.geometry.Point3D;
import joephysics62.co.uk.old.ifs.IFSRender;
import joephysics62.co.uk.old.ifs.IteratedFunctionSystem;
import joephysics62.co.uk.old.plotting.ImageScale;

public class ChaosGame implements IFSRender {

  private final int _iterations;
  private final Point3D _start;

  public ChaosGame(final int iterations, final Point3D start) {
    _iterations = iterations;
    _start = start;
  }

  @Override
  public BufferedImage render(final IteratedFunctionSystem ifs, final ImageScale imageScale) {
    Point3D curr = _start;
    final int width = imageScale.getWidth();
    final int height = imageScale.getHeight();
    final int[][] counts = new int[width][height];
    double maxCount = 0;

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < _iterations; i++) {
      final int x = imageScale.pixelX(curr.getX());
      final int y = imageScale.pixelY(curr.getY());
      if (imageScale.isOnPlot(x, y)) {
        final int newCount = counts[x][y] + 1;
        if (newCount > maxCount) {
          maxCount = newCount;
        }
        counts[x][y] = newCount;
      }
      curr = ifs.transform(curr);
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final int c = counts[x][y];
        final int brightness = (int) (255.0 * c / maxCount);
        final Color newC = new Color(brightness / 2, brightness, brightness / 2);
        bi.setRGB(x, y, newC.getRGB());
      }
    }
    return bi;
  }

}
