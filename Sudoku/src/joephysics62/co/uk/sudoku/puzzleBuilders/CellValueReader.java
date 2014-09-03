package joephysics62.co.uk.sudoku.puzzleBuilders;

public interface CellValueReader<T> {
  T parseCellValue(String value);
}
