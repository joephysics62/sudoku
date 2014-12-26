package joephysics62.co.uk.ifs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Point3D;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.lsystems.animation.GifSequenceWriter;

public class IFSRenderer {

  private static final int ITERATIONS = 200000;
  private static final float SCALE = 500;
  private static final Point3D START = Point3D.ZERO;

  public static void main(final String[] args) throws Exception {
    final String fileName = "C:\\Users\\joe\\Pictures\\ifs.gif";

    final IFSRenderer ifsRenderer = new IFSRenderer();

    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

      final int numFrames = 240;
      for (int i = 0; i < numFrames; i++) {
        final double q = -0.5 + 1.0 * i / (1.0 * numFrames);
        final IteratedFunctionSystem ifs = new Sierpinski(Math.sqrt(3) / 4.0, q);
        final long startTime = System.currentTimeMillis();
        final BufferedImage bi = ifsRenderer.renderIFSEscapeTime(600, ifs);
        System.out.println("FRAME " + (i+1) + " " + q + " " + (System.currentTimeMillis() - startTime) + "ms");
        final Graphics2D g2d = bi.createGraphics();
        g2d.drawString("Q = " + q, 10, 10);
        gifSequenceWriter.writeToSequence(bi);
      }
      gifSequenceWriter.close();
    }
  }

  private int simulateAlpha(final int input) {
    return Math.min(255, input + 50);
  }

  public BufferedImage renderIFSEscapeTime(final int height, final IteratedFunctionSystem ifs) {
    final int width = (int) (1.5 * height);

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final double initX = x / SCALE;
        final double initY = y / SCALE;
        final Point3D start = new Point3D(initX, initY, 0);
        final Set<Integer> escapeTimes = new LinkedHashSet<>();
        recursiveEscapeTimes(ifs, 0, start, escapeTimes);
        final Optional<Integer> maxEscapeTime = escapeTimes.stream().collect(Collectors.maxBy(Integer::compare));
        final int c = Math.min(255, (255 / maxDepth) * maxEscapeTime.orElse(0));
        final Color color = new Color(c, c, c);
        bi.setRGB(x, y, color.getRGB());
      }
    }
    return bi;
  }

  private final double maxDistance = 2;
  private final int maxDepth = 15;

  private void recursiveEscapeTimes(final IteratedFunctionSystem ifs, final int depth, final Point3D curr, final Set<Integer> escapeTimes) {
    if (depth > maxDepth || escapeTimes.contains(maxDepth)) {
      return;
    }
    final double distance = curr.distance(Point3D.ZERO);
    if (distance > maxDistance) {
      escapeTimes.add(depth);
      return;
    }
    for (int f = 0; f < 3; f++) {
      final Point3D next = ifs.inverseTransform(f, curr);
      recursiveEscapeTimes(ifs, depth + 1, next, escapeTimes);
    }
  }

  public BufferedImage renderIFSChaosGame(final int height, final IteratedFunctionSystem ifs) {
    final int width = (int) (1.5 * height);
    Point3D curr = START;

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < ITERATIONS; i++) {
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
}
