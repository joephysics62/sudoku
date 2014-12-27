package joephysics62.co.uk.ifs.render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point3D;
import joephysics62.co.uk.ifs.IFSRender;
import joephysics62.co.uk.ifs.IteratedFunctionSystem;

public class AverageEscapeTimeRender implements IFSRender {

  private final int _maxDepth;
  private final double _escapeRadius;
  public AverageEscapeTimeRender(final int maxDepth, final double escapeRadius) {
    _maxDepth = maxDepth;
    _escapeRadius = escapeRadius;
  }

  @Override
  public BufferedImage render(final int height, final double minY, final double maxY, final double minX, final IteratedFunctionSystem ifs) {
    final int width = (int) (1.5 * height);
    final double scale = height / (maxY - minY);

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final double initX = minX + x / scale;
        final double initY = minY + y / scale;
        final Point3D start = new Point3D(initX, initY, 0);
        final double escapeTime = escapeTime(ifs, start);
        final int c = (int) Math.min(255.0, (255.0 / _maxDepth) * escapeTime);
        final Color color = new Color(c, c, c);
        bi.setRGB(x, y, color.getRGB());
      }
    }
    return bi;
  }

  private Double escapeTime(final IteratedFunctionSystem ifs, final Point3D start) {
    final List<Integer> escapeTimes = new ArrayList<>();
    recursiveEscapeTimes(ifs, 0, start, escapeTimes, false);
    return escapeTimes.stream().collect(Collectors.averagingInt(c -> c));
  }
  private void recursiveEscapeTimes(final IteratedFunctionSystem ifs, final int depth, final Point3D curr, final Collection<Integer> escapeTimes, final boolean stopWhenMaxHit) {
    if (depth > _maxDepth) {
      return;
    }
    if (stopWhenMaxHit && escapeTimes.contains(_maxDepth)) {
      return;
    }
    final double distance = curr.distance(Point3D.ZERO);
    if (distance > _escapeRadius) {
      escapeTimes.add(depth);
      return;
    }
    for (int f = 0; f < ifs.numTransforms(); f++) {
      final Point3D next = ifs.inverseTransform(f, curr);
      recursiveEscapeTimes(ifs, depth + 1, next, escapeTimes, stopWhenMaxHit);
    }
  }

}
