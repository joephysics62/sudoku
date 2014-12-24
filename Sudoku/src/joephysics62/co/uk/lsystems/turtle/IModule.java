package joephysics62.co.uk.lsystems.turtle;

public interface IModule {

  Character getId();

  double[] getParameters();

  void apply(Turtle turtle);

}