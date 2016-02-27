package joephysics62.co.uk.hidato;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.xml.Namespaces;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Hidato {
  private final Set<Coordinate> _grid;
  private final BiMap<Integer, Coordinate> _path;
  private final Integer _height;
  private final Integer _width;

  private Hidato(final Set<Coordinate> grid, final BiMap<Integer, Coordinate> path) {
    _path = path;
    _grid = Collections.unmodifiableSet(grid);
    _height = max(grid, Coordinate::getRow);
    _width = max(grid, Coordinate::getCol);
  }

  private int max(final Set<Coordinate> grid, final Function<Coordinate, Integer> func) {
    return grid.stream().map(func).max(Integer::compare).get();
  }

  public HidatoSolution solve() {
    final List<BiMap<Integer, Coordinate>> solns = new ArrayList<>();
    recurse(_path.get(1), _path, solns);
    if (solns.isEmpty()) {
      return new HidatoSolution(Optional.empty(), SolutionType.NONE);
    }
    return new HidatoSolution(Optional.of(new Hidato(_grid, solns.get(0))),
                              solns.size() > 1 ? SolutionType.MULTIPLE : SolutionType.UNIQUE);
  }

  private void recurse(final Coordinate coord, final BiMap<Integer, Coordinate> current, final List<BiMap<Integer, Coordinate>> solns) {
    if (solns.size() > 2) {
      return;
    }
    if (current.size() == _grid.size()) {
      solns.add(current);
      return;
    }
    final Integer nextValue = current.inverse().get(coord) + 1;
    if (current.containsKey(nextValue)) {
      final Coordinate coordOfNext = current.get(nextValue);
      if (coord.arounds().anyMatch(coordOfNext::equals)) {
        recurse(coordOfNext, current, solns);
      }
      return;
    }
    coord.arounds()
         .filter(_grid::contains)
         .filter(c -> !current.containsValue(c))
         .forEach(c -> {
           final HashBiMap<Integer, Coordinate> copy = HashBiMap.create(current);
           copy.put(nextValue, c);
           recurse(c, copy, solns);
         });
  }

  public static Hidato read(final Path file) throws IOException {
    final Set<Coordinate> grid = new LinkedHashSet<>();
    final BiMap<Integer, Coordinate> path = HashBiMap.create();
    PuzzleReader.read(file, (cell, coord) -> {
      if ("//".equals(cell)) {
        return;
      }
      grid.add(coord);
      if (cell.matches("[0-9]+")) {
        final Integer intValue = Integer.valueOf(cell);
        path.put(intValue, coord);
      }
    });
    return new Hidato(grid, path);
  }

  public void write(final PrintStream out) {
    final BiMap<Coordinate, Integer> inverse = _path.inverse();
    PuzzleWriter.newWriter(_height, _width)
                .writeToStream(out, coord -> {
                  final Integer integer = inverse.get(coord);
                  if (integer == null) {
                    return "//";
                  }
                  if (integer < 10) {
                    return " " + integer;
                  }
                  return integer.toString();
                });
  }

  public void render(final File htmlFile) throws SAXException, IOException, ParserConfigurationException, TransformerException {
    final File templateFile = Paths.get("templates", "hidato.html").toFile();
    final Document templateDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(templateFile);

    final int cellSize = 50;
    final int gridHeight = _height * cellSize;
    final int gridWidth = _width * cellSize;

    final NodeList bodyElements = templateDoc.getElementsByTagName("body");
    final Element body = (Element) bodyElements.item(0);
    final Element svg = templateDoc.createElementNS(Namespaces.XHTML_NS, "svg");
    svg.setAttribute("width", Integer.toString(gridWidth + cellSize));
    svg.setAttribute("height", Integer.toString(gridHeight + cellSize));
    body.appendChild(svg);

    IntStream.rangeClosed(0, _height).forEach(x -> {
      final Element rowLine = templateDoc.createElementNS(Namespaces.XHTML_NS, "line");
      rowLine.setAttribute("x1", Integer.toString(0));
      rowLine.setAttribute("y1", Integer.toString(x * cellSize));
      rowLine.setAttribute("x2", Integer.toString(gridWidth));
      rowLine.setAttribute("y2", Integer.toString(x * cellSize));
      svg.appendChild(rowLine);
    });
    IntStream.rangeClosed(0, _width).forEach(x -> {
      final Element colLine = templateDoc.createElementNS(Namespaces.XHTML_NS, "line");
      colLine.setAttribute("x1", Integer.toString(x * cellSize));
      colLine.setAttribute("y1", Integer.toString(0));
      colLine.setAttribute("x2", Integer.toString(x * cellSize));
      colLine.setAttribute("y2", Integer.toString(gridHeight));
      svg.appendChild(colLine);
    });
    final BiMap<Coordinate, Integer> inverse = _path.inverse();
    for (int row = 1; row <= _height; row++) {
      for (int col = 1; col <= _width; col++) {
        final Coordinate coord = Coordinate.of(row, col);
        if (_grid.contains(coord)) {
          final Integer value = inverse.get(coord);
          if (value != null) {
            final int fontSize = 7 * cellSize / 10;
            final int verticalOffset = cellSize / 4;
            final int horizontalOffset = value < 10 ? cellSize / 3 : cellSize / 8;
            final Element text = templateDoc.createElementNS(Namespaces.XHTML_NS, "text");
            text.setTextContent(Integer.toString(value));
            text.setAttribute("x", Integer.toString((col - 1) * cellSize + horizontalOffset));
            text.setAttribute("y", Integer.toString(row * cellSize - verticalOffset));
            text.setAttribute("font-size", Integer.toString(fontSize));
            svg.appendChild(text);
          }
        }
        else {
          final Element nonGridSquare = templateDoc.createElementNS(Namespaces.XHTML_NS, "rect");
          nonGridSquare.setAttribute("width", Integer.toString(cellSize));
          nonGridSquare.setAttribute("height", Integer.toString(cellSize));
          nonGridSquare.setAttribute("x", Integer.toString((col - 1) * cellSize));
          nonGridSquare.setAttribute("y", Integer.toString((row - 1) * cellSize));
          svg.appendChild(nonGridSquare);
        }
      }
    }

    final Transformer transformer = TransformerFactory.newInstance().newTransformer();
    final Result output = new StreamResult(htmlFile);
    final Source input = new DOMSource(templateDoc);

    transformer.transform(input, output);
  }
}
