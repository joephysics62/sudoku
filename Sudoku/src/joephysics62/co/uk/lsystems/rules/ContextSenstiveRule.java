package joephysics62.co.uk.lsystems.rules;

import java.util.List;
import java.util.Stack;

public abstract class ContextSenstiveRule<T> implements Rule<T> {

  private final T _match;

  protected abstract T predecessor();
  protected abstract T successor();
  protected abstract T popper();
  protected abstract T pusher();
  protected abstract List<T> ignorable();

  protected ContextSenstiveRule(final T match) {
    _match = match;
  }

  @Override
  public final boolean matches(final int index, final List<T> input) {
    if (!_match.equals(input.get(index))) {
      return false;
    }
    if (predecessor() != null) {
      if (index < 1) {
        return false;
      }
      final Stack<Void> stack = new Stack<>();
      for (int i = index - 1; i >= 0; i--) {
        final T temp = input.get(i);
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
    }
    if (successor() != null) {
      if (index + 1 > input.size() - 1) {
        return false;
      }
      final Stack<Void> stack = new Stack<>();
      for (int i = index + 1; i < input.size(); i++) {
        final T temp = input.get(i);
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
    }
    return false;
  }
}
