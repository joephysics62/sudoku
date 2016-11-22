package com.fenton.suguru;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SuguruMain {

  public static void main(final String[] args) throws IOException, URISyntaxException {
    final Path suguruFile = Paths.get(SuguruMain.class.getResource("suguru.txt").toURI());
    final Suguru suguru = Suguru.fromFile(suguruFile);
    System.out.println(suguru);
  }

}
