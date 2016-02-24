package joephysics62.co.uk.old.sudokuNew;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.stream.DoubleStream;

public class GridDrawer {

  private final int _rows;
  private final int _cols;
  private final int _height;
  private final double _marginFraction;
  private final Color _drawColour;

  public GridDrawer(final int rows, final int cols, final int height, final double marginFraction, final Color drawColour) {
    _rows = rows;
    _cols = cols;
    _height = height;
    _marginFraction = marginFraction;
    _drawColour = drawColour;
  }

  public BufferedImage draw() {
    final double marginSize = _height * _marginFraction;
    final double plotHeight = _height - 2 * marginSize;
    final double cellSize = plotHeight / _rows;
    final double widthAsDouble = cellSize * _cols + 2 * marginSize;

    final BufferedImage bi = new BufferedImage((int) widthAsDouble, _height, BufferedImage.TYPE_INT_RGB);
    final Graphics2D graphics = (Graphics2D) bi.getGraphics();
    graphics.setBackground(Color.WHITE);
    graphics.fillRect(0, 0, (int) widthAsDouble, _height);
    graphics.setColor(_drawColour);

    DoubleStream.iterate(marginSize, r -> r + cellSize).limit(_rows + 1).mapToInt(y -> (int) y)
      .forEach(y -> graphics.drawLine((int) marginSize, y, (int) (widthAsDouble - marginSize), y));
    DoubleStream.iterate(marginSize, r -> r + cellSize).limit(_cols + 1).mapToInt(x -> (int) x)
      .forEach(x -> graphics.drawLine(x, (int) marginSize, x, (int) (_height - marginSize)));
    return bi;
  }
}
