package joephysics62.co.uk.ifs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javafx.geometry.Point3D;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.ifs.examples.Sierpinski;
import joephysics62.co.uk.ifs.render.ChaosGame;
import joephysics62.co.uk.lsystems.animation.GifSequenceWriter;

public class IFSMain {

  public static void main(final String[] args) throws Exception {
    final String fileName = "C:\\Users\\joe\\Pictures\\ifs.gif";

    final IFSRender ifsRenderer = new ChaosGame(2500000, Point3D.ZERO);
    //final IFSRender ifsRenderer = new DiscreteEscapeTimeRender(15, 10);


    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

      final int numFrames = 960;
      for (int i = 0; i < numFrames; i++) {
        final double q = 360.0 * i / numFrames;
        final double aDiff = 0.5 * Math.cos(Math.toRadians(q));
        final double bDiff = 0.5 * Math.sin(Math.toRadians(q));
        final IteratedFunctionSystem ifs = new Sierpinski(Math.sqrt(3) / 4.0 + aDiff - 1, 0.25 + bDiff);
        final long startTime = System.currentTimeMillis();
        final BufferedImage bi = ifsRenderer.render(ifs, -2, 2, 600, -2);
        System.out.println("FRAME " + (i+1) + " " + q + " " + (System.currentTimeMillis() - startTime) + "ms");
        final Graphics2D g2d = bi.createGraphics();
        g2d.drawString("Q = " + q, 10, 10);
        gifSequenceWriter.writeToSequence(bi);
      }
      gifSequenceWriter.close();
    }
  }

}
