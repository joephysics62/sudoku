package joephysics62.co.uk.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLHelpers {
  public static void writeDocument(final Document doc, final File file) throws TransformerFactoryConfigurationError, TransformerException {
    final Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    final Result output = new StreamResult(file);
    final Source input = new DOMSource(doc);
    transformer.transform(input, output);
  }

  public static Document parse(final File file) throws SAXException, IOException, ParserConfigurationException {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
  }
}
