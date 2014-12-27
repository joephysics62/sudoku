package joephysics62.co.uk.ifs;

import java.awt.image.BufferedImage;

public interface IFSRender {
  BufferedImage render(IteratedFunctionSystem ifs, double minY, double maxY, int imageHeight, double minX);
}
