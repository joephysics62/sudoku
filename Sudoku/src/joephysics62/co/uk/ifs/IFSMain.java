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
import joephysics62.co.uk.plotting.ImageScale;

public class IFSMain {

  public static void main(final String[] args) throws Exception {
    final String fileName = "C:\\Users\\joe\\Pictures\\ifs.gif";

    final IFSRender ifsRenderer = new ChaosGame(50000000, Point3D.ZERO);
    //final IFSRender ifsRenderer = new DiscreteEscapeTimeRender(15, 10);

    final ImageScale imageScale = new ImageScale(600, 1.5, -3, 1, -2);


    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);

      final int numFrames = 240;
      for (int i = 0; i < numFrames; i++) {
        final double q = 360 * i / numFrames;
        final double aDiff = 0.1 * Math.cos(Math.toRadians(270 + q));
        final double bDiff = 0.1 * Math.sin(Math.toRadians(270 + q));
        final IteratedFunctionSystem ifs = new Sierpinski(-0.5 + aDiff - 0.1, -0.5 + bDiff, 0.5);
        final long startTime = System.currentTimeMillis();
        final BufferedImage bi = ifsRenderer.render(ifs, imageScale);
        System.out.println("FRAME " + (i+1) + " " + q + " " + (System.currentTimeMillis() - startTime) + "ms");
        final Graphics2D g2d = bi.createGraphics();
        g2d.drawString("Q = " + q, 10, 10);
        gifSequenceWriter.writeToSequence(bi);
      }
      gifSequenceWriter.close();
    }
  }

}
