package joephysics62.co.uk.old.sudoku.creator;

public class CreationSpec {
  private final int _maxGivens;
  private final int _maxVarConstraints;
  private final boolean _removeInSymmetricPairs;
  private final int _maxDepth;

  public CreationSpec(final int maxGivens, final int maxVarConstraints, int maxDepth, final boolean removeInSymmetricPairs) {
    _maxGivens = maxGivens;
    _maxVarConstraints = maxVarConstraints;
    _maxDepth = maxDepth;
    _removeInSymmetricPairs = removeInSymmetricPairs;
  }

  public int getMaxGivens() {
    return _maxGivens;
  }
  public int getMaxVarConstraints() {
    return _maxVarConstraints;
  }
  public int getMaxDepth() {
    return _maxDepth;
  }
  public boolean isRemoveInSymmetricPairs() {
    return _removeInSymmetricPairs;
  }
}
