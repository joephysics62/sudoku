package joephysics62.co.uk.sudoku.read.html;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HTMLTableParser {

  private final int _expectedTableHeight;
  private final int _expectedTableWidth;

  public HTMLTableParser(final int expectedTableHeight, final int expectedTableWidth) {
    _expectedTableHeight = expectedTableHeight;
    _expectedTableWidth = expectedTableWidth;
  }

  public void parseTable(final File input, final TableParserHandler handler) throws IOException {
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
    NodeList titles = asDom.getElementsByTagName("title");
    if (titles.getLength() > 0) {
      handler.title(((Element) titles.item(0)).getTextContent().trim());
    }
    NodeList rows = asDom.getElementsByTagName("tr");
    if (rows.getLength() != _expectedTableHeight) {
      throw new IOException("Expected '" + _expectedTableHeight + "' rows but got '" + rows.getLength() + "'");
    }
    for (int rowIndex = 0; rowIndex < rows.getLength(); rowIndex++) {
      Element row = (Element) rows.item(rowIndex);
      NodeList cells = row.getElementsByTagName("td");
      if (cells.getLength() != _expectedTableWidth) {
        throw new IOException();
      }
      for (int colIndex = 0; colIndex < cells.getLength(); colIndex++) {
        Element cell = (Element) cells.item(colIndex);
        Set<String> classValues = readClassValues(cell);
        NodeList childDivs = cell.getElementsByTagName("div");
        if (childDivs.getLength() == 0) {
          final String cellInput = cell.getTextContent().trim();
          handler.cell(cellInput, classValues, rowIndex, colIndex);
        }
        else {
          Map<String, String> complexCellInput = readDivs(childDivs);
          handler.cell(complexCellInput, classValues, rowIndex, colIndex);
        }
      }
    }
  }

  private Map<String, String> readDivs(NodeList childDivs) throws IOException {
    Map<String, String> complexCellInput = new LinkedHashMap<>();
    for (int i = 0; i < childDivs.getLength(); i++) {
      final Element divElem = (Element) childDivs.item(i);
      String classAttr = divElem.getAttribute("class");
      if (classAttr.isEmpty()) {
        throw new IOException();
      }
      complexCellInput.put(classAttr, divElem.getTextContent().trim());
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
