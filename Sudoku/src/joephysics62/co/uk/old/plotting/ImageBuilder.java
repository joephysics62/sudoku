package joephysics62.co.uk.old.plotting;

import java.awt.image.BufferedImage;
import java.util.function.Predicate;

public abstract class ImageBuilder<T> {

  private final ImageScale _imageScale;
  private Predicate<T> _predicate;
  private final ColorProvider<T> _colorProvider;

  public ImageBuilder(final ImageScale imageScale, final ColorProvider<T> colorProvider) {
    _imageScale = imageScale;
    _colorProvider = colorProvider;
  }

  public void setPlotPredicate(final Predicate<T> predicate) {
    _predicate = predicate;
  }

  public final BufferedImage createImage(final double animationProgress) {
    final BufferedImage bi = new BufferedImage(_imageScale.getWidth(), _imageScale.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < _imageScale.getWidth(); x++) {
      for (int y = 0; y < _imageScale.getHeight(); y++) {
        final T value = valueForPixel(_imageScale.dataX(x), _imageScale.dataY(y), animationProgress);
        if (null == _predicate || _predicate.test(value)) {
          bi.setRGB(x, y, _colorProvider.getColouring(value).getRGB());
        }
      }
    }
    return bi;
  }

  protected abstract T valueForPixel(final double dataX, final double dataY, double animationProgress);

}
