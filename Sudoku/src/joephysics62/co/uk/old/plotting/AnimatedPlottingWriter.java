package joephysics62.co.uk.old.plotting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import joephysics62.co.uk.old.lsystems.animation.GifSequenceWriter;

public final class AnimatedPlottingWriter<T> {

  private final int _numFrames;
  private final int _framesPerSecond;
  private ImageBuilder<T> _imageBuilder;

  public AnimatedPlottingWriter(final int numFrames, final int framesPerSecond) {
    _numFrames = numFrames;
    _framesPerSecond = framesPerSecond;
  }

  public void setImageBuilder(final ImageBuilder<T> imageBuilder) {
    _imageBuilder = imageBuilder;
  }

  public void write(final String fileName) throws IOException {
    final int timeBetweenFrames = 1000 / _framesPerSecond;
    try (final ImageOutputStream outputStream = new FileImageOutputStream(new File(fileName))) {
      final GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, timeBetweenFrames, true);
      for (int i = 0; i < _numFrames; i++) {
        final long startTime = System.currentTimeMillis();
        final BufferedImage bi = _imageBuilder.createImage(i / (1.0 * _numFrames));
        gifSequenceWriter.writeToSequence(bi);
        System.out.println("Done FRAME " + i + " (" + (System.currentTimeMillis() - startTime)  + "ms)");
      }
    }
  }

}
