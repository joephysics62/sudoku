package joephysics62.co.uk.ifs;

import java.awt.image.BufferedImage;

public interface IFSRender {
  BufferedImage render(int imageHeight, final double minY, final double maxY, final double minX, IteratedFunctionSystem ifs);
}
