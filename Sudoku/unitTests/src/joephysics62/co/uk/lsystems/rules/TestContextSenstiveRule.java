package joephysics62.co.uk.lsystems.rules;

import static joephysics62.co.uk.lsystems.turtle.Module.create;
import static joephysics62.co.uk.lsystems.turtle.Module.left;
import static joephysics62.co.uk.lsystems.turtle.Module.pop;
import static joephysics62.co.uk.lsystems.turtle.Module.push;
import static joephysics62.co.uk.lsystems.turtle.Module.right;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import joephysics62.co.uk.lsystems.Utils;
import joephysics62.co.uk.lsystems.turtle.Module;

import org.junit.Assert;
import org.junit.Test;

public class TestContextSenstiveRule {

  private static Module A = create('A');
  private static Module B = create('B');
  private static Module LEFT = left(0);
  private static Module RIGHT = right(0);

  private static List<Module> SUPPORTED = Arrays.asList(A, B, LEFT, RIGHT, push(), pop());
  private static Map<Character, Module> CHAR_MAP = new LinkedHashMap<>();
  static {
    for (final Module turtleElement : SUPPORTED) {
      CHAR_MAP.put(turtleElement.getId(), turtleElement);
    }
  }

  private static class SuccessorRuleForTesting extends ContextSensitiveRule {

    protected SuccessorRuleForTesting() { super(A.getId()); }
    @Override public List<Module> replacement(final double... x) { return Arrays.asList(B); }
    @Override protected Character predecessor() { return null; }
    @Override protected Character successor() { return B.getId(); }
    @Override protected List<Character> ignorable() { return Arrays.asList(LEFT.getId(), RIGHT.getId()); }
  }

  private static class PredecessorRuleForTesting extends ContextSensitiveRule {

    protected PredecessorRuleForTesting() { super(A.getId()); }
    @Override public List<Module> replacement(final double... x) { return Arrays.asList(B); }
    @Override protected Character predecessor() { return B.getId(); }
    @Override protected Character successor() { return null; }
    @Override protected List<Character> ignorable() { return Arrays.asList(LEFT.getId(), RIGHT.getId()); }
  }

  private void runTest(final Rule rule, final String input, final Integer... matches) {
    final List<Module> elems = Utils.toChars(input).stream().map(c -> CHAR_MAP.get(c)).collect(Collectors.toList());
    final List<Integer> matchList = Arrays.asList(matches);
    for (int i = 0; i < elems.size(); i++) {
      Assert.assertEquals("Failure at char = " + i, matchList.contains(i), rule.matches(i, elems));
    }
  }

  @Test
  public void testSuccessorRule() {
    final Rule rule = new SuccessorRuleForTesting();
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
    final Rule rule = new PredecessorRuleForTesting();
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
