package io.documentnode.epub4j.epub;

import io.documentnode.epub4j.domain.Author;
import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Date;
import io.documentnode.epub4j.domain.Date.Event;
import io.documentnode.epub4j.domain.LazyResource;
import io.documentnode.epub4j.domain.LazyResourceProvider;
import io.documentnode.epub4j.domain.TOCReference;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jake
 */
public class SampleLazy {

  static class ResourceProvider implements LazyResourceProvider {

    @Override
    public InputStream getResourceStream(String href) throws IOException {
      return SampleLazy.class.getResourceAsStream("/book1/" + href);
    }
  }

  public static void main(String[] args) {
    try {
      // Create new Book
      Book book = new Book();

      // Set the title
      book.getMetadata().addTitle("EPUB4J test book 1");

      // Add an Author
      book.getMetadata().addAuthor(new Author("Joe", "Tester"));
      book.getMetadata().addAuthor(new Author("Jill", "Second"));
      book.getMetadata().addContributor(new Author("Mike", "Contrib"));
      book.getMetadata().addPublisher("Document Node 1.4");
      book.getMetadata()
          .addDate(new Date(new java.util.Date(), Event.CREATION));
      book.getMetadata()
          .addDate(new Date(new java.util.Date(), Event.MODIFICATION));
      book.getMetadata()
          .addDate(new Date(new java.util.Date(), Event.PUBLICATION));

      ResourceProvider resourceProvider = new ResourceProvider();
      // Set cover image
      book.setCoverImage(new LazyResource(resourceProvider, "cover.png"));

      // Set cover page
      book.setCoverPage(new LazyResource(resourceProvider, "cover.html"));

      // Add Chapter 1
      TOCReference chapter1 = book.addSection("Introduction",
          new LazyResource(resourceProvider, "chapter1.html"));
      // Add Chapter 1 Section 2 using fragmentId
      book.addSection(chapter1, "Chapter 1, Section 2",
          chapter1.getResource(), "ch-1-2");

      // Add css file
      book.getResources().add(new LazyResource(resourceProvider, "book1.css"));

      // Add Chapter 2
      TOCReference chapter2 = book.addSection("Second Chapter",
          new LazyResource(resourceProvider, "chapter2.html"));

      // Add image used by Chapter 2
      book.getResources().add(
          new LazyResource(resourceProvider, "flowers.jpg"));

      // Add Chapter2, Section 1
      book.addSection(chapter2, "Chapter 2, section 1",
          new LazyResource(resourceProvider, "chapter2_1.html"));

      // Add Chapter 3
      book.addSection("Conclusion",
          new LazyResource(resourceProvider, "chapter3.html"));

      // Create EpubWriter
      EpubWriter epubWriter = new EpubWriter();

      // Write the Book as Epub
      epubWriter.write(book, new FileOutputStream("testbook1_lazy.epub"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
