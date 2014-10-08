package joephysics62.co.uk.sudoku.read.html;

import java.util.Map;

public interface TableParserHandler {

  void cell(String cellInput, int rowIndex, int colIndex);
  void cell(Map<String, String> complexCellInput, int rowIndex, int colIndex);

  void title(String title);


}
