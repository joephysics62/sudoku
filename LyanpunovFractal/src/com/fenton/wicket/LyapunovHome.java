package com.fenton.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LyapunovHome extends WebPage {
  private static final long serialVersionUID = -2160699419531791694L;

  public LyapunovHome(final PageParameters parameters) {
    add(new Label("message", "Hello World, Wicket"));
  }

}
