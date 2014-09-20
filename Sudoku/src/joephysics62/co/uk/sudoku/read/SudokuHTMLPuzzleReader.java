package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.html.LocalHTMLEntityResolver;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SudokuHTMLPuzzleReader implements PuzzleReader {

  private final int _outerSize;
  private final ArrayPuzzleBuilder _sudokuBuilder;

  public SudokuHTMLPuzzleReader(final int subTableHeight, final int subTableWidth, final int outerSize) {
    _outerSize = outerSize;
    _sudokuBuilder = new SudokuBuilder(outerSize, subTableHeight, subTableWidth);
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    try {
      final Integer[][] givens = new Integer[_outerSize][_outerSize];
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
      documentBuilder.setEntityResolver(LocalHTMLEntityResolver.newResolver());
      if (!input.exists() || input.isDirectory()) {
        throw new IOException();
      }
      Document asDom = documentBuilder.parse(input);
      NodeList rows = asDom.getElementsByTagName("tr");
      if (rows.getLength() != _outerSize) {
        throw new IOException();
      }
      for (int rowIndex = 0; rowIndex < rows.getLength(); rowIndex++) {
        Element row = (Element) rows.item(rowIndex);
        NodeList cells = row.getElementsByTagName("td");
        if (cells.getLength() != _outerSize) {
          throw new IOException();
        }
        for (int colIndex = 0; colIndex < cells.getLength(); colIndex++) {
          Element cell = (Element) cells.item(colIndex);
          final String cellInput = cell.getTextContent().trim();
          givens[rowIndex][colIndex] = cellInput.isEmpty() ? null : Cell.fromString(cellInput, _outerSize);
        }
      }
      _sudokuBuilder.addGivens(givens);
      return _sudokuBuilder.build();
    }
    catch (ParserConfigurationException | SAXException e) {
      throw new IOException(e);
    }
  }

}
