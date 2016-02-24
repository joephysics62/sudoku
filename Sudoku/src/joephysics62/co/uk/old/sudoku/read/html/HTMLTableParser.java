package joephysics62.co.uk.old.sudoku.read.html;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.grid.GridLayout;
import joephysics62.co.uk.old.grid.map.MapGrid;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HTMLTableParser {

  private final GridLayout _layout;

  public HTMLTableParser(final GridLayout layout) {
    _layout = layout;
  }

  public static class InputCell {
    private final Set<String> _classes;
    private final String _textValue;
    private final Map<String, String> _complexValue;

    public InputCell(final String textValue, final Map<String, String> complexValue, Set<String> classes) {
      _textValue = textValue;
      _complexValue = complexValue;
      _classes = classes;
    }

    public Set<String> getClasses() {
      return _classes;
    }
    public String getTextValue() {
      return _textValue;
    }
    public Map<String, String> getComplexValue() {
      return _complexValue;
    }
  }

  public Grid<InputCell> parseTable(final File input) throws IOException {
    Document asDom = getDomDoc(input);
    NodeList rows = asDom.getElementsByTagName("tr");
    if (rows.getLength() != _layout.getHeight()) {
      throw new IOException("Expected '" + _layout.getHeight() + "' rows but got '" + rows.getLength() + "'");
    }
    Grid<InputCell> mapGrid = new MapGrid<>(_layout);
    for (Coord coord : mapGrid) {
      Element row = (Element) rows.item(coord.getRow() - 1);
      NodeList cells = row.getElementsByTagName("td");
      Element domCell = (Element) cells.item(coord.getCol() - 1);
      NodeList childDivs = domCell.getElementsByTagName("div");
      final String textValue;
      Map<String, String> complexValue;
      if (childDivs.getLength() == 0) {
        textValue = domCell.getTextContent().trim();
        complexValue = Collections.emptyMap();
      }
      else {
        complexValue = readDivs(childDivs);
        textValue = "";
      }
      mapGrid.set(new InputCell(textValue, complexValue, readClassValues(domCell)), coord);
    }
    return mapGrid;
  }

  private Document getDomDoc(final File input) throws IOException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    try {
      documentBuilder = dbf.newDocumentBuilder();
    }
    catch (ParserConfigurationException e) {
      throw new IOException(e);
    }
    documentBuilder.setEntityResolver(LocalHTMLEntityResolver.newResolver());
    if (!input.exists() || input.isDirectory()) {
      throw new IOException();
    }
    Document asDom;
    try {
      asDom = documentBuilder.parse(input);
    }
    catch (SAXException e) {
      throw new IOException(e);
    }
    return asDom;
  }

  private Map<String, String> readDivs(NodeList childDivs) throws IOException {
    Map<String, String> complexCellInput = new LinkedHashMap<>();
    for (int i = 0; i < childDivs.getLength(); i++) {
      final Element divElem = (Element) childDivs.item(i);
      String classAttr = divElem.getAttribute("class");
      if (classAttr.isEmpty()) {
        complexCellInput.put("", divElem.getTextContent().trim());
      }
      else {
        complexCellInput.put(classAttr, divElem.getTextContent().trim());
      }
    }
    return complexCellInput;
  }

  private Set<String> readClassValues(Element cell) {
    final String classAttr = cell.getAttribute("class").trim();
    if (classAttr.isEmpty()) {
      return Collections.emptySet();
    }
    return Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(classAttr.split("\\s+"))));
  }
}
