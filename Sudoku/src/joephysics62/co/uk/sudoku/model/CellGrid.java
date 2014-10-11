package joephysics62.co.uk.sudoku.model;

public interface CellGrid {

  int getCellValue(Coord coord);

  void setCellValue(int cellValues, Coord coord);

  Layout getLayout();

}