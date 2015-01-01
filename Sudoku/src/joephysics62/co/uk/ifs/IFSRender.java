package joephysics62.co.uk.ifs;

import java.awt.image.BufferedImage;

import joephysics62.co.uk.plotting.ImageScale;

public interface IFSRender {
  BufferedImage render(IteratedFunctionSystem ifs, ImageScale imageScale);
}
