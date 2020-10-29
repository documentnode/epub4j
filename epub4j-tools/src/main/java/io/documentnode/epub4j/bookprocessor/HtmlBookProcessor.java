package io.documentnode.epub4j.bookprocessor;


import io.documentnode.epub4j.Constants;
import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.epub.BookProcessor;
import io.documentnode.epub4j.service.MediatypeService;
import io.documentnode.minilog.Logger;
import java.io.IOException;

/**
 * Helper class for BookProcessors that only manipulate html type resources.
 *
 * @author paul
 */
public abstract class HtmlBookProcessor implements BookProcessor {

  private final static Logger log = Logger.create(HtmlBookProcessor.class);
  public static final String OUTPUT_ENCODING = "UTF-8";

  public HtmlBookProcessor() {
  }

  @Override
  public Book processBook(Book book) {
    for (Resource resource : book.getResources().getAll()) {
      try {
        cleanupResource(resource, book);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
    return book;
  }

  private void cleanupResource(Resource resource, Book book)
      throws IOException {
    if (resource.getMediaType() == MediatypeService.XHTML) {
      byte[] cleanedHtml = processHtml(resource, book,
          Constants.CHARACTER_ENCODING);
      resource.setData(cleanedHtml);
      resource.setInputEncoding(Constants.CHARACTER_ENCODING);
    }
  }

  protected abstract byte[] processHtml(Resource resource, Book book,
      String encoding) throws IOException;
}
