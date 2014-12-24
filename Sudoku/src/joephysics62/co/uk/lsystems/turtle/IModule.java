package joephysics62.co.uk.lsystems.turtle;

public interface IModule {

  Character getId();

  void apply(Turtle turtle);

  double[] getParameters();

}