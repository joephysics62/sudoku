package joephysics62.co.uk.lsystems;

public class RewriteRule {

	private final char _from;
	private final String _to;

	private RewriteRule(final char from, final String to) {
		_from = from;
		_to = to;
	}

	public static RewriteRule of(final char from, final String to) {
		return new RewriteRule(from, to);
	}

	public char getFrom() {
		return _from;
	}
	public String getTo() {
		return _to;
	}
}
