package io.documentnode.epub4j.epub;

import io.documentnode.epub4j.domain.Author;
import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.domain.TOCReference;
import java.io.FileOutputStream;

public class Simple1 {

  public static void main(String[] args) {
    try {
      // Create new Book
      Book book = new Book();

      // Set the title
      book.getMetadata().addTitle("EPUB4J test book 1");

      // Add an Author
      book.getMetadata().addAuthor(new Author("Joe", "Tester"));

      // Set cover image
      book.setCoverImage(new Resource(
          Simple1.class.getResourceAsStream("/book1/cover.png"),
          "cover.png"));

      // Set cover page
      book.setCoverPage(new Resource(
          Simple1.class.getResourceAsStream("/book1/cover.html"),
          "cover.html"));

      // Add Chapter 1
      book.addSection("Introduction", new Resource(
          Simple1.class.getResourceAsStream("/book1/chapter1.html"),
          "chapter1.html"));

      // Add css file
      book.getResources().add(
          new Resource(Simple1.class.getResourceAsStream("/book1/book1.css"),
              "book1.css"));

      // Add Chapter 2
      TOCReference chapter2 = book.addSection("Second Chapter", new Resource(
          Simple1.class.getResourceAsStream("/book1/chapter2.html"),
          "chapter2.html"));

      // Add image used by Chapter 2
      book.getResources().add(new Resource(
          Simple1.class.getResourceAsStream("/book1/flowers_320x240.jpg"),
          "flowers.jpg"));

      // Add Chapter2, Section 1
      book.addSection(chapter2, "Chapter 2, section 1", new Resource(
          Simple1.class.getResourceAsStream("/book1/chapter2_1.html"),
          "chapter2_1.html"));

      // Add Chapter 3
      book.addSection("Conclusion", new Resource(
          Simple1.class.getResourceAsStream("/book1/chapter3.html"),
          "chapter3.html"));

      // Create EpubWriter
      EpubWriter epubWriter = new EpubWriter();

      // Write the Book as Epub
      epubWriter.write(book, new FileOutputStream("testbook1.epub"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
