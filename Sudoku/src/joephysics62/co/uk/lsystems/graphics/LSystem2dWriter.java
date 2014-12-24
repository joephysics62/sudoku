package joephysics62.co.uk.lsystems.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;

import joephysics62.co.uk.lsystems.LSystem;
import joephysics62.co.uk.lsystems.LSystemTurtleInterpreter;

public class LSystem2dWriter {

  public void writeGif(final LSystem lsystem, final int iterations, final String fileName, final int imageSize) throws IOException {
    final BufferedImage bi = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

    final LSystemTurtleInterpreter interpreter = new LSystemTurtleInterpreter();
    final List<Line3D> lines = interpreter.interpret(lsystem.generate(iterations));
    final double minX = lines.stream().map(i -> Math.min(i.getStart().getX(), i.getEnd().getX())).collect(Collectors.minBy(Double::compare)).orElse(0.0);
    final double maxX = lines.stream().map(i -> Math.max(i.getStart().getX(), i.getEnd().getX())).collect(Collectors.maxBy(Double::compare)).orElse(10.0);
    final double minZ = lines.stream().map(i -> Math.min(i.getStart().getZ(), i.getEnd().getZ())).collect(Collectors.minBy(Double::compare)).orElse(0.0);
    final double maxZ = lines.stream().map(i -> Math.max(i.getStart().getZ(), i.getEnd().getZ())).collect(Collectors.maxBy(Double::compare)).orElse(10.0);

    final int margin = imageSize / 20;
    final int plotSize = imageSize - 2 * margin;
    final double scale = plotSize / Math.max(maxX - minX, maxZ - minZ);
    final Graphics2D g2d = bi.createGraphics();
    for (final Line3D line3d : lines) {
      final Color color = lsystem.indexedColour(line3d.getColour());
      final Point3D start = line3d.getStart();
      final Point3D end = line3d.getEnd();
      g2d.setColor(new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue()));
      final int drawXStart = margin + (int) ((start.getX() - minX) * scale);
      final int drawZStart = margin + (int) (plotSize - (start.getZ() - minZ) * scale);
      final int drawXEnd = margin + (int) ((end.getX() - minX) * scale);
      final int drawZEnd = margin + (int) (plotSize - (end.getZ() - minZ) * scale);
      g2d.drawLine(drawXStart, drawZStart, drawXEnd, drawZEnd);
    }
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
