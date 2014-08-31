package joephysics62.co.uk.sudoku.parse;

public interface CellValueReader<T> {
  T parseCellValue(String value);
}
