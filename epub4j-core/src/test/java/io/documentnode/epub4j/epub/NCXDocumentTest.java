package io.documentnode.epub4j.epub;

import static org.junit.Assert.assertEquals;

import io.documentnode.epub4j.domain.MediaTypes;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.util.IOUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NCXDocumentTest {

    byte[] ncxData;

    public NCXDocumentTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        ncxData = IOUtil.toByteArray(new FileInputStream(new File("src/test/resources/toc.xml")));
    }

    @After
    public void tearDown() {
    }

    private void addResource(Book book, String filename) {
        Resource chapterResource = new Resource("id1", "Hello, world !".getBytes(), filename, MediaTypes.XHTML);
        book.addResource(chapterResource);
        book.getSpine().addResource(chapterResource);
    }
    
    /**
     * Test of read method, of class NCXDocument.
     */
    @Test
    public void testReadWithNonRootLevelTOC() {
        
        // If the tox.ncx file is not in the root, the hrefs it refers to need to preserve its path.
        Book book = new Book();
        Resource ncxResource = new Resource(ncxData, "xhtml/toc.ncx");
        addResource(book, "xhtml/chapter1.html");
        addResource(book, "xhtml/chapter2.html");
        addResource(book, "xhtml/chapter2_1.html");
        addResource(book, "xhtml/chapter3.html");

        book.setNcxResource(ncxResource);
        book.getSpine().setTocResource(ncxResource);

        NCXDocument.read(book, new EpubReader());
        assertEquals("xhtml/chapter1.html", book.getTableOfContents().getTocReferences().get(0).getCompleteHref());
    }
}
