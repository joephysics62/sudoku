package com.fenton.wicket;

import java.io.IOException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;

import com.fenton.lyapunov.LyapunovGenerator;
import com.fenton.lyapunov.LyapunovRenderer;
import com.fenton.lyapunov.Plot;

public class LyapunovHome extends WebPage {
  private static final long serialVersionUID = -2160699419531791694L;

  protected String lyapunovString = "AB";
  protected int maxIterations = 50;
  protected int frequency = 100;
  protected double xmin = 2;
  protected double xmax = 4;
  protected double ymin = 2;
  protected double ymax = 4;
  protected double threshold = 2.5;

  protected int redPhase = 20;
  protected int greenPhase = 0;
  protected int bluePhase = 80;



  public LyapunovHome(final PageParameters parameters) {
    add(new Label("title", "Lyapunov Generator"));

    final Form<?> form = new Form<Void>("form");

    final IModel<String> lyapunovStringModel = new PropertyModel<>(this, "lyapunovString");
    final IModel<Integer> maxIterationsModel = new PropertyModel<>(this, "maxIterations");
    final IModel<Integer> frequencyModel = new PropertyModel<>(this, "frequency");

    final IModel<Double> xminModel = new PropertyModel<>(this, "xmin");
    final IModel<Double> xmaxModel = new PropertyModel<>(this, "xmax");
    final IModel<Double> yminModel = new PropertyModel<>(this, "ymin");
    final IModel<Double> ymaxModel = new PropertyModel<>(this, "ymax");

    final IModel<Double> thresholdModel = new PropertyModel<>(this, "threshold");

    final IModel<Integer> redPhaseModel = new PropertyModel<>(this, "redPhase");
    final IModel<Integer> greenPhaseModel = new PropertyModel<>(this, "greenPhase");
    final IModel<Integer> bluePhaseModel = new PropertyModel<>(this, "bluePhase");



    form.add(new TextField<>("lyapunovString", lyapunovStringModel));
    form.add(new TextField<>("maxIterations", maxIterationsModel));
    form.add(new TextField<>("frequency", frequencyModel));
    form.add(new TextField<>("xmin", xminModel));
    form.add(new TextField<>("xmax", xmaxModel));
    form.add(new TextField<>("ymin", yminModel));
    form.add(new TextField<>("ymax", ymaxModel));
    form.add(new TextField<>("threshold", thresholdModel));
    form.add(new TextField<>("redPhase", redPhaseModel));
    form.add(new TextField<>("greenPhase", greenPhaseModel));
    form.add(new TextField<>("bluePhase", bluePhaseModel));


    add(form);

    add(new NonCachingImage("mmFigure", new AbstractReadOnlyModel<DynamicImageResource>(){
      private static final long serialVersionUID = -5506990275775097377L;

      @Override public DynamicImageResource getObject() {
        final DynamicImageResource dir = new DynamicImageResource() {
          private static final long serialVersionUID = 8045959870591042005L;

          @Override protected byte[] getImageData(final Attributes attributes) {
            final Plot plot = new Plot(500, 500,
                                       xminModel.getObject(), xmaxModel.getObject(),
                                       yminModel.getObject(), ymaxModel.getObject());
            final LyapunovGenerator lyapunovGenerator = new LyapunovGenerator(lyapunovStringModel.getObject(), maxIterationsModel.getObject());
            plot.generateData(lyapunovGenerator);
            final LyapunovRenderer renderer = new LyapunovRenderer(
                frequencyModel.getObject(),
                thresholdModel.getObject(),
                redPhaseModel.getObject(),
                greenPhaseModel.getObject(),
                bluePhaseModel.getObject());
            try {
              return plot.writeToBytes(renderer);
            }
            catch (final IOException e) {
              throw new RuntimeException(e);
            }
          }
        };
        dir.setFormat("image/png");
        return dir;
      }
    }));

  }

}
