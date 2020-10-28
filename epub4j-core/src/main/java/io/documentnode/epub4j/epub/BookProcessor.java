package io.documentnode.epub4j.epub;

import io.documentnode.epub4j.domain.Book;

/**
 * Post-processes a book.
 * 
 * Can be used to clean up a book after reading or before writing.
 * 
 * @author paul
 *
 */
public interface BookProcessor {
	
	/**
	 * A BookProcessor that returns the input book unchanged.
	 */
	public BookProcessor IDENTITY_BOOKPROCESSOR = new BookProcessor() {
		
		@Override
		public Book processBook(Book book) {
			return book;
		}
	};
	
	Book processBook(Book book);
}
