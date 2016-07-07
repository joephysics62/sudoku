package joephysics62.co.uk.backtrackingsudoku;

public enum Operator {
  ADD("+"),
  SUBTRACT("-"),
  MULTIPLY("x"),
  DIVIDE("/");

  private final String _operatorStr;

  private Operator(final String operatorStr) {
    _operatorStr = operatorStr;
  }

  public static Operator fromString(final String operatorStr) {
    if (operatorStr == null) {
      return ADD;
    }
    for (final Operator operator : Operator.values()) {
      if (operator._operatorStr.equals(operatorStr)) {
        return operator;
      }
    }
    throw new IllegalArgumentException("Unsupported operator '" + operatorStr + "'");
  }
}
