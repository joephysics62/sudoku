package joephysics62.co.uk.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Charsets;

public class SvgBuilder {
  private final Document _htmlDoc;
  private Element _svg;

  public SvgBuilder(final Document htmlDoc) {
    _htmlDoc = htmlDoc;
  }

  public SvgBuilder addCss(final Path cssFile) throws DOMException, IOException {
    final Element head = (Element) _htmlDoc.getElementsByTagNameNS(Namespaces.XHTML_NS, "head").item(0);
    final Element style = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "style");
    final String cssContent = new String(Files.readAllBytes(cssFile), Charsets.UTF_8);
    style.setTextContent(cssContent.replaceAll("\\r", ""));
    head.appendChild(style);
    return this;
  }

  public SvgBuilder addSvg(final int width, final int height) {
    final Element html = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "html");
    _htmlDoc.appendChild(html);
    final Element head = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "head");
    html.appendChild(head);
    final Element body = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "body");
    html.appendChild(body);
    _svg = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "svg");
    _svg.setAttribute("width", Integer.toString(width));
    _svg.setAttribute("height", Integer.toString(height));
    body.appendChild(_svg);
    return this;
  }

  public SvgBuilder addLine(final int x1, final int y1, final int x2, final int y2) {
    final Element line = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "line");
    line.setAttribute("x1", Integer.toString(x1));
    line.setAttribute("y1", Integer.toString(y1));
    line.setAttribute("x2", Integer.toString(x2));
    line.setAttribute("y2", Integer.toString(y2));
    _svg.appendChild(line);
    return this;
  }

  public SvgBuilder addText(final String textString, final int x, final int y, final int fontSize) {
    final Element text = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "text");
    text.setTextContent(textString);
    text.setAttribute("x", Integer.toString(x));
    text.setAttribute("y", Integer.toString(y));
    text.setAttribute("font-size", Integer.toString(fontSize));
    _svg.appendChild(text);
    return this;
  }

  public SvgBuilder addRectangle(final int width, final int height, final int x, final int y) {
    final Element rect = _htmlDoc.createElementNS(Namespaces.XHTML_NS, "rect");
    rect.setAttribute("width", Integer.toString(width));
    rect.setAttribute("height", Integer.toString(height));
    rect.setAttribute("x", Integer.toString(x));
    rect.setAttribute("y", Integer.toString(y));
    _svg.appendChild(rect);
    return this;
  }

  public void write(final File file) throws TransformerException {
    XMLHelpers.writeDocument(_htmlDoc, file);
  }

  public static SvgBuilder newBuilder() throws ParserConfigurationException {
    final Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    return new SvgBuilder(newDocument);
  }
}
