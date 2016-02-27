package joephysics62.co.uk.codeword;


public class CodeWordMain {

  private static final String FILE = "examples\\codeword\\codeWordTimes2635.txt";

  public static void main(final String[] args) throws Exception {
    final String file = FILE;
    final CodeWord puzzle = CodeWord.readFromFile(file);
    final CodeWord solved = puzzle.solve();
    System.out.println(solved.toString());
  }

}
