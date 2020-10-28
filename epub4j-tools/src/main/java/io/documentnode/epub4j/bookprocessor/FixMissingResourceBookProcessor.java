package io.documentnode.epub4j.bookprocessor;

import java.util.Collection;

import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.TOCReference;
import io.documentnode.epub4j.epub.BookProcessor;

public class FixMissingResourceBookProcessor implements BookProcessor {

	@Override
	public Book processBook(Book book) {
		return book;
	}

	private void fixMissingResources(Collection<TOCReference> tocReferences, Book book) {
		for (TOCReference tocReference:  tocReferences) {
			if (tocReference.getResource() == null) {
				
			}
		}
	}
}
