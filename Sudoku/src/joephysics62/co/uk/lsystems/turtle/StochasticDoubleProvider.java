package joephysics62.co.uk.lsystems.turtle;

import java.util.Random;

public final class StochasticDoubleProvider implements DoubleProvider {
	private final Random _random = new Random();
	private final double _base;
	private final double _spread;

	public StochasticDoubleProvider(final double base, final double spread) {
		_base = base;
		_spread = spread;
	}

	@Override
	public double nextDouble() {
		return _base * (1 + _spread * (_random.nextDouble() - 0.5));
	}
}