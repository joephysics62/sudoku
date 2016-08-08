package com.fenton.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;

import com.fenton.lyapunov.GenerationParameters;

public class LyapunovHome extends WebPage {
  private static final long serialVersionUID = -2160699419531791694L;

  protected IModel<GenerationParameters> params = Model.of(new GenerationParameters());

  public LyapunovHome(final PageParameters parameters) {
    add(new Label("title", "Lyapunov Generator"));

    final Form<?> form = new Form<Void>("form");

    form.add(new TextField<>("lyapunovString", new PropertyModel<>(params, "lyapunovString")));
    form.add(new TextField<>("startValue", new PropertyModel<>(params, "startValue")));

    form.add(new TextField<>("maxIterations", new PropertyModel<>(params, "maxIterations")));
    form.add(new TextField<>("frequency", new PropertyModel<>(params, "frequency")));
    form.add(new TextField<>("xmin", new PropertyModel<>(params, "xmin")));
    form.add(new TextField<>("xmax", new PropertyModel<>(params, "xmax")));
    form.add(new TextField<>("ymin", new PropertyModel<>(params, "ymin")));
    form.add(new TextField<>("ymax", new PropertyModel<>(params, "ymax")));
    form.add(new TextField<>("threshold", new PropertyModel<>(params, "threshold")));
    form.add(new TextField<>("redPhase", new PropertyModel<>(params, "redPhase")));
    form.add(new TextField<>("greenPhase", new PropertyModel<>(params, "greenPhase")));
    form.add(new TextField<>("bluePhase", new PropertyModel<>(params, "bluePhase")));


    add(form);

    add(new NonCachingImage("mmFigure", new AbstractReadOnlyModel<DynamicImageResource>(){
      private static final long serialVersionUID = -5506990275775097377L;

      @Override public DynamicImageResource getObject() {
        final DynamicImageResource dir = new DynamicImageResource() {
          private static final long serialVersionUID = 8045959870591042005L;

          @Override protected byte[] getImageData(final Attributes attributes) {
            return params.getObject().getImageData();
          }
        };
        dir.setFormat("image/png");
        return dir;
      }
    }));

  }

}
