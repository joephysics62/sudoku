package joephysics62.co.uk.ifs;

import java.awt.Color;

import javafx.geometry.Point3D;
import joephysics62.co.uk.ifs.render.ChaosGame;
import joephysics62.co.uk.plotting.AnimatedPlottingWriter;
import joephysics62.co.uk.plotting.ColorProvider;
import joephysics62.co.uk.plotting.ImageBuilder;
import joephysics62.co.uk.plotting.ImageScale;

public class IFSMain {

  public static void main(final String[] args) throws Exception {
    final String fileName = "C:\\Users\\joe\\Pictures\\ifs.gif";

    final IFSRender ifsRenderer = new ChaosGame(50000000, Point3D.ZERO);
    //final IFSRender ifsRenderer = new DiscreteEscapeTimeRender(15, 10);

    final ImageScale imageScale = new ImageScale(600, 1.5, -3, 1, -2);

    final ColorProvider<Integer> colorProvider = new ColorProvider<Integer>() {
      @Override
      public Color getColouring(final Integer value) {
        return Color.WHITE;
      }
    };
    final ImageBuilder<Integer> imageBuilder = new ImageBuilder<Integer>(imageScale, colorProvider) {
      @Override
      protected Integer valueForPixel(final double dataX, final double dataY, final double animationProgress) {
        return null;
      }
    };

    final AnimatedPlottingWriter<Integer> plottingWriter = new AnimatedPlottingWriter<Integer>(240, 24);

//      @Override
//      protected BufferedImage createImage(final double animationProgress) {
//        final double q = 360 * animationProgress;
//        final double aDiff = 0.1 * Math.cos(Math.toRadians(270 + q));
//        final double bDiff = 0.1 * Math.sin(Math.toRadians(270 + q));
//        IteratedFunctionSystem ifs;
//        try {
//          ifs = new Sierpinski(-0.5 + aDiff - 0.1, -0.5 + bDiff, 0.5);
//        } catch (final NonInvertibleTransformException e) {
//          throw new RuntimeException(e);
//        }
//        final long startTime = System.currentTimeMillis();
//        final BufferedImage bi = ifsRenderer.render(ifs, imageScale);
//        final Graphics2D g2d = bi.createGraphics();
//        g2d.drawString("Q = " + q, 10, 10);
//        return bi;
//      }
//    };

    plottingWriter.write(fileName);

  }

}
