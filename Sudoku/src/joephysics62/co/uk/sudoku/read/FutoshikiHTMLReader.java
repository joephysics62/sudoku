package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import joephysics62.co.uk.sudoku.builder.FutoshikiBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.html.LocalHTMLEntityResolver;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FutoshikiHTMLReader implements PuzzleReader {

  private final int _puzzleSize;

  public FutoshikiHTMLReader(final int puzzleSize) {
    _puzzleSize = puzzleSize;
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    FutoshikiBuilder futoshikiBuilder = new FutoshikiBuilder(_puzzleSize);
    Integer[][] givenValues = givens(input, futoshikiBuilder);
    futoshikiBuilder.addGivens(givenValues);
    return futoshikiBuilder.build();
  }

  private Integer[][] givens(final File input, final FutoshikiBuilder futoshikiBuilder) throws IOException {
    final int expectedRowNum = 2 * _puzzleSize - 1;
    final int expectedColNum = 2 * _puzzleSize - 1;
    try {
      final Integer[][] givens = new Integer[_puzzleSize][_puzzleSize];
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
      documentBuilder.setEntityResolver(LocalHTMLEntityResolver.newResolver());
      if (!input.exists() || input.isDirectory()) {
        throw new IOException();
      }
      Document asDom = documentBuilder.parse(input);
      NodeList rows = asDom.getElementsByTagName("tr");
      if (rows.getLength() != expectedRowNum) {
        throw new IOException("Expected '" + expectedRowNum + "' rows but got '" + rows.getLength() + "'");
      }
      for (int rowIndex = 0; rowIndex < rows.getLength(); rowIndex++) {
        Element row = (Element) rows.item(rowIndex);
        NodeList cells = row.getElementsByTagName("td");
        if (cells.getLength() != expectedColNum) {
          throw new IOException();
        }
        for (int colIndex = 0; colIndex < cells.getLength(); colIndex++) {
          Element cell = (Element) cells.item(colIndex);
          final String cellInput = cell.getTextContent().trim();
          int rowNum = rowIndex / 2 + 1;
          int colNum = colIndex / 2 + 1;
          if (rowIndex % 2 == 0 && colIndex % 2 == 0) {
            givens[rowNum - 1][colNum - 1] = cellInput.isEmpty() ? null : Cell.fromString(cellInput, _puzzleSize);
          }
          else {
            if (rowIndex % 2 == 0 && colIndex % 2 == 1) {
              if (">".equals(cellInput)) {
                futoshikiBuilder.addGreaterThan(Coord.of(rowNum, colNum), Coord.of(rowNum, colNum + 1));
              }
              else if ("<".equals(cellInput)) {
                futoshikiBuilder.addGreaterThan(Coord.of(rowNum, colNum + 1), Coord.of(rowNum, colNum));
              }
            }
            if (rowIndex % 2 == 1 && colIndex % 2 == 0) {
              if (">".equals(cellInput)) {
                futoshikiBuilder.addGreaterThan(Coord.of(rowNum, colNum), Coord.of(rowNum + 1, colNum));
              }
              else if ("<".equals(cellInput)) {
                futoshikiBuilder.addGreaterThan(Coord.of(rowNum + 1, colNum), Coord.of(rowNum, colNum));
              }
            }
          }
        }
      }
      return givens;
    }
    catch (ParserConfigurationException | SAXException e) {
      throw new IOException(e);
    }
  }

}
