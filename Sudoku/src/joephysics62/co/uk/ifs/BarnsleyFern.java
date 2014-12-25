package joephysics62.co.uk.ifs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;

import javax.imageio.ImageIO;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class BarnsleyFern {

  private static Affine from2d(final double xx, final double xy, final double yx, final double yy, final double tx, final double ty) {
    return new Affine(xx, xy, 0, tx,
                      yx, yy, 0, ty,
                      0 , 0 , 1, 0);
  }

  public static void main(final String[] args) throws Exception {
    final int imageSize = 600;
    final String fileName = "fern.gif";
    final BufferedImage bi = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
    final Graphics2D g2d = bi.createGraphics();

    final float scale = 50;
    final Point3D start = new Point3D(0, 0, 0);

    final Affine a0 = from2d(0, 0, 0, 0.16, 0, 0);
    final Affine a1 = from2d(0.85, 0.04, -0.04, 0.85, 0, 1.6);
    final Affine a2 = from2d(0.2, -.26, .23, .22, 0, 1.6);
    final Affine a3 = from2d(-.15, .28, .26, 0.24, 0, 0.44);
    final double[] weights = new double[] {0.01, .85, .07, .07};
    final List<Affine> affines = Arrays.asList(a0, a1, a2, a3);
    final IntegerDistribution eid = new EnumeratedIntegerDistribution(IntStream.range(0, weights.length).toArray(), weights);

    Point3D curr = start;
    g2d.setColor(new Color(70, 140, 70, 90));
    for (int i = 0; i < 50000; i++) {
      final int x = (int) (scale * curr.getX()) + imageSize / 2;
      final int y = (int) (scale * curr.getY());
      if (x > 0 && y > 0 && x < imageSize && y < imageSize) {
        g2d.drawLine(x, y, x, y);
      }
      curr = affines.get(eid.sample()).transform(curr);
    }
    ImageIO.write(bi, "GIF", new File(fileName));

  }
}
