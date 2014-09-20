package joephysics62.co.uk.sudoku.read.html;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LocalHTMLEntityResolver implements EntityResolver {
  @Override
  public InputSource resolveEntity(String entityName, String entityLocation) throws SAXException, IOException {
    if ("-//W3C//DTD XHTML 1.0 Transitional//EN".equals(entityName)) {
      return new InputSource(this.getClass().getResourceAsStream("resources/xhtml1-transitional.dtd"));
    }
    if ("-//W3C//ENTITIES Latin 1 for XHTML//EN".equals(entityName)) {
      return new InputSource(this.getClass().getResourceAsStream("resources/xhtml-lat1.ent"));
    }
    if ("-//W3C//ENTITIES Latin 1 for XHTML//EN".equals(entityName)) {
      return new InputSource(this.getClass().getResourceAsStream("resources/xhtml-lat1.ent"));
    }
    if ("-//W3C//ENTITIES Special for XHTML//EN".equals(entityName)) {
      return new InputSource(this.getClass().getResourceAsStream("resources/xhtml-special.ent"));
    }
    if ("-//W3C//ENTITIES Symbols for XHTML//EN".equals(entityName)) {
      return new InputSource(this.getClass().getResourceAsStream("resources/xhtml-symbol.ent"));
    }
    throw new UnsupportedOperationException("'" + entityName + "' '" + entityLocation + "'");
  }
  private LocalHTMLEntityResolver() {
  }
  public static LocalHTMLEntityResolver newResolver() {
    return new LocalHTMLEntityResolver();
  }
}
