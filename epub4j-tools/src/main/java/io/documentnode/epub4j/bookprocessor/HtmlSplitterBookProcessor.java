package io.documentnode.epub4j.bookprocessor;

import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.epub.BookProcessor;

/**
 * In the future this will split up too large html documents into smaller ones.
 * 
 * @author paul
 *
 */
public class HtmlSplitterBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		return book;
	}

}
