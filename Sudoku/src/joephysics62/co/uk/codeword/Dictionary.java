package joephysics62.co.uk.codeword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Dictionary {

  private final Multimap<Integer, String> _words = HashMultimap.create();

  public Dictionary() throws IOException, URISyntaxException {
    final File file = new File(getClass().getResource("sowpods.txt").toURI());
    try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        final String word = line.trim().toUpperCase();
        _words.put(word.length(), word);
      }
    }
  }

  public List<String> matches(final String regex, final int wordLength, final int maxMatches) {
    final Pattern pattern = Pattern.compile(regex);
    final List<String> out = new ArrayList<>();
    for (final String word : _words.get(wordLength)) {
      if (pattern.matcher(word).matches()) {
        out.add(word);
        if (out.size() >= maxMatches) {
          return out;
        }
      }
    }
    return out;
  }
}
