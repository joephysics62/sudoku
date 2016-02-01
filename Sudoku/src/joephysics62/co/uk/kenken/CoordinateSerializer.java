package joephysics62.co.uk.kenken;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CoordinateSerializer extends JsonSerializer<Coordinate> {

  @Override
  public void serialize(final Coordinate value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException, JsonProcessingException {
    gen.writeObject(value.getRow() + " " + value.getCol());
  }

}
