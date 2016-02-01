package joephysics62.co.uk.kenken;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CoordinateDeserializer extends JsonDeserializer<Coordinate> {

  @Override
  public Coordinate deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
    final String asString = p.readValueAs(String.class);
    final String[] split = asString.split(" ");
    return Coordinate.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
  }

}
