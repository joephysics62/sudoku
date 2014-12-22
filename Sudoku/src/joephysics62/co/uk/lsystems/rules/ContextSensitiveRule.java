package joephysics62.co.uk.lsystems.rules;

import java.util.List;
import java.util.Stack;

import joephysics62.co.uk.lsystems.TurtleElement;

public abstract class ContextSensitiveRule implements Rule {

  private final Character _match;

  protected abstract Character predecessor();
  protected abstract Character successor();
  protected abstract Character popper();
  protected abstract Character pusher();
  protected abstract List<Character> ignorable();

  protected ContextSensitiveRule(final Character matchId) {
    _match = matchId;
  }

  private final boolean predecessorSatisfied(final int index, final List<TurtleElement> input) {
    if (predecessor() == null) {
      return true;
    }
    if (index < 1) {
      return false;
    }
    final Stack<Void> stack = new Stack<>();
    for (int i = index - 1; i >= 0; i--) {
      final Character temp = input.get(i).getId();
      if (temp.equals(pusher())) {
        if (!stack.isEmpty()) {
          stack.pop();
        }
        continue;
      }
      if (temp.equals(popper())) {
        stack.push(null);
        continue;
      }
      if (!ignorable().contains(temp)) {
        final boolean isPredecessor = temp.equals(predecessor());
        if (stack.isEmpty()) {
          return isPredecessor;
        }
        else if (isPredecessor) {
          return true;
        }
      }
    }
    return false;
  }

  private final boolean successorSatisfied(final int index, final List<TurtleElement> input) {
    if (successor() == null) {
      return true;
    }
    if (index + 1 > input.size() - 1) {
      return false;
    }
    final Stack<Void> stack = new Stack<>();
    for (int i = index + 1; i < input.size(); i++) {
      final Character temp = input.get(i).getId();
      if (temp.equals(pusher())) {
        stack.push(null);
        continue;
      }
      if (temp.equals(popper())) {
        if (stack.isEmpty()) {
          return false;
        }
        stack.pop();
        continue;
      }
      if (!ignorable().contains(temp)) {
        final boolean isSuccessor = temp.equals(successor());
        if (stack.isEmpty()) {
          return isSuccessor;
        }
        else if (isSuccessor) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public final boolean matches(final int index, final List<TurtleElement> input) {
    if (!_match.equals(input.get(index).getId())) {
      return false;
    }
    return predecessorSatisfied(index, input) && successorSatisfied(index, input);
  }
}
