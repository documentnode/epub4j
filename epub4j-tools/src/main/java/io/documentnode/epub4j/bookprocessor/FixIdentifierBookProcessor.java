package io.documentnode.epub4j.bookprocessor;

import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Identifier;
import io.documentnode.epub4j.epub.BookProcessor;

/**
 * If the book has no identifier it adds a generated UUID as identifier.
 * 
 * @author paul
 *
 */
public class FixIdentifierBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		if(book.getMetadata().getIdentifiers().isEmpty()) {
			book.getMetadata().addIdentifier(new Identifier());
		}
		return book;
	}
}
