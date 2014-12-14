package joephysics62.co.uk.lsystems.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import joephysics62.co.uk.lsystems.LSystemGenerator;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;
import joephysics62.co.uk.lsystems.turtle.TurtleListener;

public class LSystem2dWriter {
  private final LSystemGenerator _generator;

  public LSystem2dWriter(final LSystemGenerator generator) {
    _generator = generator;
  }

  public void writeGif(final TurtleLSystem lsystem, final int iterations, final String fileName, final int imageSize) throws IOException {
    final BufferedImage bi = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
    final TurtleListener graphics2dListener = new Graphics2DListener(bi.createGraphics());
    final LSystemTurtleInterpreter interpreter = new LSystemTurtleInterpreter(graphics2dListener, lsystem.angle(), 0.1, 1.0);
    interpreter.interpret(_generator.generate(lsystem, iterations));
    ImageIO.write(bi, "GIF", new File(fileName));
  }

//  public static void angleAnimatedGif(final String fileName, final List<Turtle> turtleMoves, final int frames) throws IOException {
//    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
//      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 1000 / 24, true);
//
//      for (int i = 0; i < frames; i++) {
//        final double angle = 360 * i / frames;
//        final BufferedImage bi = createBufferedImage(turtleMoves, angle, 700);
//        gifSequenceWriter.writeToSequence(bi);
//      }
//      gifSequenceWriter.close();
//    }
//  }

}
