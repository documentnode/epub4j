package io.documentnode.epub4j.bookprocessor;

import io.documentnode.epub4j.epub.BookProcessor;
import io.documentnode.epub4j.epub.BookProcessorPipeline;
import io.documentnode.minilog.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A book processor that combines several other bookprocessors
 *
 * Fixes coverpage/coverimage.
 * Cleans up the XHTML.
 *
 * @author paul.siegmann
 *
 */
public class DefaultBookProcessorPipeline extends BookProcessorPipeline {

  private Logger log = Logger.create(DefaultBookProcessorPipeline.class);

  public DefaultBookProcessorPipeline() {
    super(createDefaultBookProcessors());
  }

  private static List<BookProcessor> createDefaultBookProcessors() {
    List<BookProcessor> result = new ArrayList<BookProcessor>();
    result.addAll(Arrays.asList(new BookProcessor[]{
        new SectionHrefSanityCheckBookProcessor(),
        new HtmlCleanerBookProcessor(),
        new CoverPageBookProcessor(),
        new FixIdentifierBookProcessor()
    }));
    return result;
  }
}
