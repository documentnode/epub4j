package io.documentnode.epub4j.bookprocessor;

import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.epub.BookProcessor;
import io.documentnode.epub4j.epub.EpubProcessorSupport;
import io.documentnode.minilog.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


/**
 * Uses the given xslFile to process all html resources of a Book.
 *
 * @author paul
 *
 */
public class XslBookProcessor extends HtmlBookProcessor implements
    BookProcessor {

  private final static Logger log = Logger.create(XslBookProcessor.class);

  private Transformer transformer;

  public XslBookProcessor(String xslFileName)
      throws TransformerConfigurationException {
    File xslFile = new File(xslFileName);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    transformer = transformerFactory.newTransformer(new StreamSource(xslFile));
  }

  @Override
  public byte[] processHtml(Resource resource, Book book, String encoding)
      throws IOException {
    byte[] result = null;
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbFactory.newDocumentBuilder();
      db.setEntityResolver(EpubProcessorSupport.getEntityResolver());

      Document doc = db.parse(new InputSource(resource.getReader()));

      Source htmlSource = new DOMSource(doc.getDocumentElement());
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Writer writer = new OutputStreamWriter(out, "UTF-8");
      Result streamResult = new StreamResult(writer);
      try {
        transformer.transform(htmlSource, streamResult);
      } catch (TransformerException e) {
        log.error(e.getMessage(), e);
        throw new IOException(e);
      }
      result = out.toByteArray();
      return result;
    } catch (Exception e) {
      throw new IOException(e);
    }
  }
}
