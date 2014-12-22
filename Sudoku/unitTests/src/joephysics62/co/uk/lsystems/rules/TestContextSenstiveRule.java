package joephysics62.co.uk.lsystems.rules;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.Utils;

import org.junit.Assert;
import org.junit.Test;

public class TestContextSenstiveRule {

  private static class SuccessorRuleForTesting extends ContextSensitiveRule<Character> {

    protected SuccessorRuleForTesting() { super('A'); }
    @Override public List<Character> replacement() { return Arrays.asList('B'); }
    @Override protected Character predecessor() { return null; }
    @Override protected Character successor() { return 'B'; }
    @Override protected Character popper() { return ']'; }
    @Override protected Character pusher() { return '['; }
    @Override protected List<Character> ignorable() { return Arrays.asList('+', '-'); }
  }

  private static class PredecessorRuleForTesting extends ContextSensitiveRule<Character> {

    protected PredecessorRuleForTesting() { super('A'); }
    @Override public List<Character> replacement() { return Arrays.asList('B'); }
    @Override protected Character predecessor() { return 'B'; }
    @Override protected Character successor() { return null; }
    @Override protected Character popper() { return ']'; }
    @Override protected Character pusher() { return '['; }
    @Override protected List<Character> ignorable() { return Arrays.asList('+', '-'); }
  }

  private void runTest(final Rule<Character> rule, final String input, final Integer... matches) {
    final List<Character> chars = Utils.toChars(input);
    final List<Integer> matchList = Arrays.asList(matches);
    for (int i = 0; i < chars.size(); i++) {
      Assert.assertEquals("Failure at char = " + i, matchList.contains(i), rule.matches(i, chars));
    }
  }

  @Test
  public void testSuccessorRule() {
    final Rule<Character> rule = new SuccessorRuleForTesting();
    runTest(rule, "");
    runTest(rule, "AB", 0);
    runTest(rule, "BA");
    runTest(rule, "A");
    runTest(rule, "BB");
    runTest(rule, "BAB", 1);

    runTest(rule, "BA+B", 1);
    runTest(rule, "BA-B", 1);
    runTest(rule, "BA-+-B", 1);

    runTest(rule, "[A]B");
    runTest(rule, "A[A]B", 0);
    runTest(rule, "A[B]A", 0);
    runTest(rule, "A[[B]]A", 0);

    runTest(rule, "AB[A]A", 0);
    runTest(rule, "A[A]A");
    runTest(rule, "A[AB]B", 0, 2);
    runTest(rule, "A[AB[A][AB]]B", 0, 2, 8);

    runTest(rule, "A[+A]A[-A]A[+A]B", 10);
    runTest(rule, "A[+A]A[-A]B[+A]B", 5);
    runTest(rule, "A[+A]B[-A]B[+A]B", 0);
  }

  @Test
  public void testPredecessorRule() {
    final Rule<Character> rule = new PredecessorRuleForTesting();
    runTest(rule, "");
    runTest(rule, "A");
    runTest(rule, "B");
    runTest(rule, "AB");
    runTest(rule, "BA", 1);
    runTest(rule, "BAA", 1);
    runTest(rule, "BBA", 2);
    runTest(rule, "BAB", 1);
    runTest(rule, "BABA", 1, 3);

    runTest(rule, "A+B");
    runTest(rule, "B-A", 2);
    runTest(rule, "B+A", 2);
    runTest(rule, "B+-A", 3);

    runTest(rule, "A[BA]", 3);
    runTest(rule, "AB[A]", 3);
    runTest(rule, "AB[A]A", 3, 5);
    runTest(rule, "AB[AA][AA]ABA", 3, 7, 10, 12);

    runTest(rule, "B[+A]A[-A]A[+A]A", 3, 5);
    runTest(rule, "B[+B]B[-A]A[+A]A", 8, 10);
    runTest(rule, "B[+B]B[-B]B[+A]A", 13, 15);
    runTest(rule, "B[+B]B[-B]B[+B]B");
  }

}
