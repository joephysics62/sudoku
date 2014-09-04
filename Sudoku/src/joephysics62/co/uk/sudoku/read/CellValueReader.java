package joephysics62.co.uk.sudoku.read;

public interface CellValueReader<T> {
  T parseCellValue(String value);
}
