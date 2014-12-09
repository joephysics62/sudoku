package joephysics62.co.uk.lsystems.turtle;

public interface DoubleProvider {
	double nextDouble();

	public static DoubleProvider fixed(final double fixed) {
		return () -> fixed;
	}
}
