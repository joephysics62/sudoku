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

  private static final int ITERATIONS = 25000;
  private static final float SCALE = 50;
  private static final Point3D START = Point3D.ZERO;
  private static final Color COLOUR = new Color(70, 140, 70, 90);

  public static void main(final String[] args) throws Exception {
    final String fileName = "fern.gif";

    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

      final int numFrames = 480;
      for (int i = 0; i < numFrames; i++) {
        final double xy = -0.5 + i / numFrames;
        final BufferedImage bi = createFern(600, new BarnsleyFern(xy));
        System.out.println("FRAME " + (i+1));
        gifSequenceWriter.writeToSequence(bi);
      }
      gifSequenceWriter.close();
    }

    //final BufferedImage bi = createFern(imageSize, xy);
    //ImageIO.write(bi, "GIF", new File(fileName));

  }

  private static BufferedImage createFern(final int imageSize, final IteratedFunctionSystem ifs) {
    final BufferedImage bi = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
    final Graphics2D g2d = bi.createGraphics();
    g2d.setColor(COLOUR);

    Point3D curr = START;
    for (int i = 0; i < ITERATIONS; i++) {
      final int x = (int) (SCALE * curr.getX()) + imageSize / 2;
      final int y = (int) (SCALE * curr.getY());
      if (x > 0 && y > 0 && x < imageSize && y < imageSize) {
        g2d.drawLine(x, y, x, y);
      }
      curr = ifs.transform(curr);
    }
    return bi;
  }
}
