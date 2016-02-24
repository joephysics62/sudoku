package joephysics62.co.uk.old.ifs.render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Point3D;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.ifs.IFSRender;
import joephysics62.co.uk.old.ifs.IteratedFunctionSystem;
import joephysics62.co.uk.old.plotting.ImageScale;

public class DiscreteEscapeTimeRender implements IFSRender {

  private final int _maxDepth;
  private final double _escapeRadius;

  public DiscreteEscapeTimeRender(final int maxDepth, final double escapeRadius) {
    _maxDepth = maxDepth;
    _escapeRadius = escapeRadius;
  }

  @Override
  public BufferedImage render(final IteratedFunctionSystem ifs, final ImageScale imageScale) {
    final int width = imageScale.getWidth();
    final int height = imageScale.getHeight();
    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final Point3D start = new Point3D(imageScale.dataX(x), imageScale.dataY(y), 0);
        final double escapeTime = escapeTime(ifs, start, Coord.of(x, y));
        final int c = (int) Math.min(255.0, (255.0 / _maxDepth) * escapeTime);
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

  private double escapeTime(final IteratedFunctionSystem ifs, final Point3D start, final Coord pixelCoord) {
    final Set<Integer> escapeTimes = new LinkedHashSet<>();
    recursiveEscapeTimes(ifs, 0, start, escapeTimes, true);
    return escapeTimes.stream().collect(Collectors.maxBy(Integer::compare)).orElse(0);
  }

}
