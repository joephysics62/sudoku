package joephysics62.co.uk.sudoku.creator;

public class CreationSpec {
  private final int _maxGivens;
  private final int _maxVarConstraints;
  private final boolean _removeInSymmetricPairs;

  public CreationSpec(final int maxGivens, final int maxVarConstraints, final boolean removeInSymmetricPairs) {
    _maxGivens = maxGivens;
    _maxVarConstraints = maxVarConstraints;
    _removeInSymmetricPairs = removeInSymmetricPairs;
  }

  public int getMaxGivens() {
    return _maxGivens;
  }
  public int getMaxVarConstraints() {
    return _maxVarConstraints;
  }
  public boolean isRemoveInSymmetricPairs() {
    return _removeInSymmetricPairs;
  }
}
