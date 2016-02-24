package joephysics62.co.uk.codeword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    return _words.get(wordLength).stream()
          .filter(w -> pattern.matcher(w).matches())
          .limit(maxMatches)
          .collect(Collectors.toList());
  }
}
