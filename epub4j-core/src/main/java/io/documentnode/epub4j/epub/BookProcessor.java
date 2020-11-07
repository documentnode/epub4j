package io.documentnode.epub4j.epub;

import io.documentnode.epub4j.domain.Book;

/**
 * Post-processes a book.
 *
 * Can be used to clean up a book after reading or before writing.
 *
 * @author paul
 */
public interface BookProcessor {

  /**
   * A BookProcessor that returns the input book unchanged.
   */
  BookProcessor IDENTITY_BOOKPROCESSOR = book -> book;

  Book processBook(Book book);
}
