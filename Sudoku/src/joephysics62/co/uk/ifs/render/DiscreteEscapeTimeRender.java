package joephysics62.co.uk.ifs.render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Point3D;
import joephysics62.co.uk.ifs.IFSRender;
import joephysics62.co.uk.ifs.IteratedFunctionSystem;

public class DiscreteEscapeTimeRender implements IFSRender {

  private static final double SCALE = 200;
  private final int _maxDepth;
  private final double _escapeRadius;

  public DiscreteEscapeTimeRender(final int maxDepth, final double escapeRadius) {
    _maxDepth = maxDepth;
    _escapeRadius = escapeRadius;
  }

  @Override
  public BufferedImage render(final int height, final IteratedFunctionSystem ifs) {
    final int width = 3 * height / 2;

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final double initX = (x - 0.4 * width) / SCALE;
        final double initY = (y - 0.2 * width) / SCALE;
        final Point3D start = new Point3D(initX, initY, 0);
        final Optional<Integer> escapeTime = findLongestEscapeTime(ifs, start);
        final int c = (int) Math.min(255.0, (255.0 / _maxDepth) * escapeTime.orElse(0));
        final Color color = new Color(c, c, c);
        bi.setRGB(x, y, color.getRGB());
      }
    }
    return bi;
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

  private Optional<Integer> findLongestEscapeTime(final IteratedFunctionSystem ifs, final Point3D start) {
    final Set<Integer> escapeTimes = new LinkedHashSet<>();
    recursiveEscapeTimes(ifs, 0, start, escapeTimes, true);
    return escapeTimes.stream().collect(Collectors.maxBy(Integer::compare));
  }

}
