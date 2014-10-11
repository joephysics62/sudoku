package joephysics62.co.uk.sudoku.read.html;

import java.util.Map;
import java.util.Set;

public interface TableParserHandler {

  void cell(String cellInput, Set<String> classes, int rowIndex, int colIndex);
  void cell(Map<String, String> complexCellInput, Set<String> classes, int rowIndex, int colIndex);

  void title(String title);


}
