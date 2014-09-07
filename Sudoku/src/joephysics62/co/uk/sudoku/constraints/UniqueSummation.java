package joephysics62.co.uk.sudoku.constraints;

import java.util.List;

import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class UniqueSummation implements Constraint {

  private final Integer _sumValue;
  private final List<Coord> _coords;

  // look up table?
  // size 1
  // i = asBitwise(i)
  // size 2
  // puzzle size = p
  // sum size = N
  // no values below 1+2+...N = N(N+1)/2
  // no values above p + (p-1) + p-2... = N(p+(1-N)/2)
  // size N  -> no values below N+1, no values about 2p-1
  // 1 = {}
  // 2 = {}
  // 3 -> {3}
  // 4 -> {5}
  // 5 -> {6,9}
  // 6 -> {10,17}
  // 7 -> {18,24,33}
  // 8 -> {20,34,65}
  // 9 -> {36,66,129}
  // N=1
  //N=0 {{0}}
  //N=1 {{1},{2},{4},{8},{16},{32},{64},{128},{256}}
  //N=2 {{0},{0},{3},{5},{6,9},{10,17},{18,24,33},{36,66,129},...,}
  //N=3 {{0},{0},{0},{0},{0},{7}
  // sum=2 {2}
  // etc..
  // N=2
  //S=1 {}
  //S=2 {}
  //S=3 {3}
  //S=4 {5}

  public UniqueSummation(Integer sumValue, List<Coord> coords) {
    _sumValue = sumValue;
    _coords = coords;
  }

  public static UniqueSummation of(Integer sumValue, List<Coord> coords) {
    return new UniqueSummation(sumValue, coords);
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean forSolvedCell(CellGrid cellGrid, int solvedCell) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Coord> getCells() {
    return _coords;
  }

}
