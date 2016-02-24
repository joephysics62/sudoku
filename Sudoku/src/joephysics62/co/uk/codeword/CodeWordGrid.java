package joephysics62.co.uk.codeword;

import java.util.ArrayList;
import java.util.List;

public class CodeWordGrid {

  private final Integer[][] _grid;
  private final List<Word> _horizontals;
  private final List<Word> _verticals;

  public CodeWordGrid(final Integer[][] grid) {
    _grid = grid;
    _horizontals = findWords(grid, _grid.length, _grid[0].length, new GridGetter() {
      @Override
      public Integer get(final Integer[][] grid, final int first, final int second) {
        return grid[first][second];
      }
    });
    _verticals = findWords(grid, _grid[0].length, _grid.length, new GridGetter() {
      @Override
      public Integer get(final Integer[][] grid, final int first, final int second) {
        return grid[second][first];
      }
    });
  }

  private List<Word> findWords(final Integer[][] grid, final int height, final int width, final GridGetter gridGetter) {
    final List<Word> words = new ArrayList<>();
    for (int firstIndex = 0; firstIndex < height; firstIndex++) {
      List<Integer> currentWord = null;
      for (int secondIndex = 0; secondIndex < width; secondIndex++) {
        final Integer cell = gridGetter.get(grid, firstIndex, secondIndex);
        if (cell != null) {
          if (currentWord == null) {
            currentWord = new ArrayList<Integer>();
          }
          currentWord.add(cell);
        }
        if (currentWord != null && (cell == null || secondIndex == width - 1)) {
          if (currentWord.size() > 1) {
            words.add(new Word(currentWord));
          }
          currentWord = null;
        }
      }
    }
    return words;
  }

  private interface GridGetter {
    Integer get(Integer[][] grid, int first, int second);
  }

  public List<Word> getHorizontals() {
    return _horizontals;
  }

  public List<Word> getVerticals() {
    return _verticals;
  }

  public List<Word> getAllWords() {
    final List<Word> allWords = new ArrayList<Word>();
    allWords.addAll(getHorizontals());
    allWords.addAll(getVerticals());
    return allWords;
  }

  public Integer[][] getGrid() {
    return _grid;
  }

  public Integer getKey(final int row, final int col) {
    return _grid[row][col];
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (final Integer[] keys : _grid) {
      for (final Integer key : keys) {
        if (null == key) {
          sb.append("   ");
        }
        else {
          sb.append(key < 10 ? "  " : " ");
          sb.append(key);
        }
      }
      sb.append(String.format("%n"));
    }
    return sb.toString();
  }

}
