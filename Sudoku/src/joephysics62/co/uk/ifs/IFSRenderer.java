package joephysics62.co.uk.ifs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javafx.geometry.Point3D;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.lsystems.animation.GifSequenceWriter;

public class IFSRenderer {

  private static final int ITERATIONS = 200000;
  private static final float SCALE = 300;
  private static final Point3D START = Point3D.ZERO;

  public static void main(final String[] args) throws Exception {
    final String fileName = "C:\\Users\\joe\\Pictures\\ifs.gif";

    final IFSRenderer ifsRenderer = new IFSRenderer();

    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

      final int numFrames = 24;
      for (int i = 0; i < numFrames; i++) {
        final double q = 0.25 + 0.01 * i / (1.0 * numFrames);
        final IteratedFunctionSystem ifs = new Sierpinski(Math.sqrt(3) / 4.0 + q, 0.25 - q);
        final BufferedImage bi = ifsRenderer.renderIFS(600, ifs);
        System.out.println("FRAME " + (i+1) + " " + q);
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

  public BufferedImage renderIFS(final int height, final IteratedFunctionSystem ifs) {
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
