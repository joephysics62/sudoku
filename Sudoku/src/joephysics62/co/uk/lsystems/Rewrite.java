package joephysics62.co.uk.lsystems;

public class Rewrite {

	private final char _from;
	private final String _to;

	private Rewrite(final char from, final String to) {
		_from = from;
		_to = to;
	}

	public static Rewrite of(final char from, final String to) {
		return new Rewrite(from, to);
	}

	public char getFrom() {
		return _from;
	}
	public String getTo() {
		return _to;
	}
}
