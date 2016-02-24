package joephysics62.co.uk.old.ifs;

import java.awt.image.BufferedImage;

import joephysics62.co.uk.old.plotting.ImageScale;

public interface IFSRender {
  BufferedImage render(IteratedFunctionSystem ifs, ImageScale imageScale);
}
