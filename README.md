# epub4j

A java library for reading/writing/manipulating EPUB files, with improvements based on [epublib](https://github.com/psiegman/epublib).

Comparing to the original epublib, epub4j contains the following changes:
* Switched to Gradle Kotlin DSL as the build tool
* Removed the dependency of SLF4J libraries
* Fixed issues of creating a native shared library

It consists of 2 parts: a core that reads/writes epub and a collection of tools.

The tools contain an epub cleanup tool, a tool to create epubs from html files, a tool to create an epub from an uncompress html file. It also contains a swing-based epub viewer.

![EPUB4J viewer](doc/Alice%E2%80%99s-Adventures-in-Wonderland_2011-01-30_18-17-30.png)

The core runs both on android and a standard java environment. The tools run only on a standard java environment.

This means that reading/writing epub files works on Android.

Maintained by [Document Node](https://documentnode.io).

## Build status

[![MiniLog Actions Status](https://github.com/documentnode/epub4j/workflows/Java%20CI%20with%20Gradle/badge.svg)](https://github.com/documentnode/epub4j/actions)

[![Travis Build Status](https://travis-ci.org/documentnode/epub4j.svg?branch=main)](https://travis-ci.org/documentnode/epub4j)

## Command line examples

Set the author of an existing epub

```bash
make build
cd epub4j-tools/build/libs
java -jar epub4j-tools-4.2-SNAPSHOT-all.jar --in input.epub --out result.epub --author Tester,Joe
```

Set the cover image of an existing epub

```bash
make build
cd epub4j-tools/build/libs
java -jar epub4j-tools-4.2-SNAPSHOT-all.jar --in input.epub --out result.epub --cover-image my_cover.jpg
```

## Creating an epub programmatically

```java
package io.documentnode.epub4j.examples;

import java.io.InputStream;
import java.io.FileOutputStream;

import io.documentnode.epub4j.domain.Author;
import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Metadata;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.domain.TOCReference;

import io.documentnode.epub4j.epub.EpubWriter;

public class Translator {
  private static InputStream getResource( String path ) {
    return Translator.class.getResourceAsStream( path );
  }

  private static Resource getResource( String path, String href ) {
    return new Resource( getResource( path ), href );
  }

  public static void main(String[] args) {
    try {
      // Create new Book
      Book book = new Book();
      Metadata metadata = book.getMetadata();

      // Set the title
      metadata.addTitle("EPUB4J test book 1");

      // Add an Author
      metadata.addAuthor(new Author("Joe", "Tester"));

      // Set cover image
      book.setCoverImage(
        getResource("/book1/test_cover.png", "cover.png"));

      // Add Chapter 1
      book.addSection("Introduction",
        getResource("/book1/chapter1.html", "chapter1.html"));

      // Add css file
      book.getResources().add(
        getResource("/book1/book1.css", "book1.css"));

      // Add Chapter 2
      TOCReference chapter2 = book.addSection( "Second Chapter",
        getResource("/book1/chapter2.html", "chapter2.html"));

      // Add image used by Chapter 2
      book.getResources().add(
        getResource("/book1/flowers_320x240.jpg", "flowers.jpg"));

      // Add Chapter2, Section 1
      book.addSection(chapter2, "Chapter 2, section 1",
        getResource("/book1/chapter2_1.html", "chapter2_1.html"));

      // Add Chapter 3
      book.addSection("Conclusion",
        getResource("/book1/chapter3.html", "chapter3.html"));

      // Create EpubWriter
      EpubWriter epubWriter = new EpubWriter();

      // Write the Book as Epub
      epubWriter.write(book, new FileOutputStream("test1_book1.epub"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

## Usage in Android

Add the following lines to your `app` module's `build.gradle` file:

```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation('io.documentnode.epub4j:epub4j-core:4.1') {
    exclude group: 'xmlpull'
  }
}
```
